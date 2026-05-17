package util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import DTO.AddAnalysisDTO;

import com.google.gson.*;

public class GroqService {

    private static final String API_KEY = "";

    private static final String API_URL =
            "https://api.groq.com/openai/v1/chat/completions";

    private static final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(10))
            .build();

    public static AddAnalysisDTO analyzeCode(String sourceCode) {

        try {

            String prompt =
                    "Analyze this code and return ONLY valid JSON with keys: "
                            + "data_structures (string), algorithms (string), "
                            + "ai_probability (number 0-100), feedback (string).\n\nCode:\n"
                            + sourceCode;

            String jsonBody = """
            {
              "model": "llama-3.1-8b-instant",
              "messages": [
                {
                  "role": "user",
                  "content": "%s"
                }
              ],
              "temperature": 0.2
            }
            """.formatted(escapeJson(prompt));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + API_KEY)
                    .timeout(Duration.ofSeconds(60))
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                System.err.println("GROQ HTTP ERROR: " + response.statusCode());
                System.err.println(response.body());
                return null;
            }

            return parseResponse(response.body());

        } catch (Exception e) {
            System.err.println("GROQ request failed: " + e.getMessage());
            return null;
        }
    }

    private static AddAnalysisDTO parseResponse(String responseBody) {

        try {
            System.out.println("RAW GROQ RESPONSE: " + responseBody);

            JsonObject root = JsonParser.parseString(responseBody).getAsJsonObject();

            String content = root
                    .getAsJsonArray("choices")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("message")
                    .get("content")
                    .getAsString();

            content = content.replace("```json", "")
                             .replace("```", "")
                             .trim();

            int start = content.indexOf("{");
            int end = content.lastIndexOf("}");

            if (start == -1 || end == -1) {
                System.err.println("No JSON found in Groq output");
                return null;
            }

            String jsonPart = content.substring(start, end + 1);

            JsonObject data = JsonParser.parseString(jsonPart).getAsJsonObject();

            AddAnalysisDTO dto = new AddAnalysisDTO(getSafe(data, "data_structures"), 
            		getSafe(data, "algorithms"),
            		data.get("ai_probability").getAsFloat(),
            		getSafe(data, "feedback")
            		);

            return dto;

        } catch (Exception e) {
            System.err.println("GROQ parse error: " + e.getMessage());
            return null;
        }
    }

    private static String getSafe(JsonObject obj, String key) {
        return (obj != null && obj.has(key) && !obj.get(key).isJsonNull())
                ? obj.get(key).getAsString()
                : "";
    }

    private static String escapeJson(String text) {
        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");
    }
}
