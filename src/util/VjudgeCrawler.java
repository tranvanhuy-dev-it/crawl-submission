package util;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DTO.AddSubmissionDTO;

public class VjudgeCrawler {

    private static final String COOKIE =
        "JSESSIONID=E21A4C762355C53A87CA8F8750082EF3; cf_clearance=xxx";

    public static List<AddSubmissionDTO> crawl(String handle, Timestamp lastCrawlDate) {

        List<AddSubmissionDTO> list = new ArrayList<>();

        try {
            String url = "https://vjudge.net/status/data?un=" + handle + "&res=1&start=0&length=50";

            HttpClient client = HttpClient.newBuilder().build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("User-Agent", "Mozilla/5.0")
                    .header("Accept", "application/json")
                    .header("Referer", "https://vjudge.net/")
                    .header("Cookie", COOKIE)
                    .GET()
                    .build();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();

            if (!json.has("data")) return list;

            long lastTime = (lastCrawlDate != null) ? lastCrawlDate.getTime() : 0;

            JsonArray data = json.getAsJsonArray("data");

            for (int i = 0; i < data.size(); i++) {
                JsonObject obj = data.get(i).getAsJsonObject();

                long submissionTime = obj.get("time").getAsLong();

                // 🔥 gặp bài cũ → dừng luôn
                if (submissionTime <= lastTime) break;

                AddSubmissionDTO dto = new AddSubmissionDTO();

                dto.setRemoteId(obj.get("runId").getAsString());

                // problem
                String problem = "Unknown";
                if (obj.has("problemId") && !obj.get("problemId").isJsonNull()) {
                    problem = obj.get("problemId").getAsString();
                }

                if (obj.has("oj") && !obj.get("oj").isJsonNull()) {
                    problem += " (" + obj.get("oj").getAsString() + ")";
                }

                dto.setProblemTitle(problem);

                dto.setProgrammingLanguage(
                        obj.has("language") ? obj.get("language").getAsString() : "Unknown"
                );

                dto.setSubmissionTime(new Timestamp(submissionTime));

                dto.setSourceCode("// Không lấy được từ Vjudge");

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
