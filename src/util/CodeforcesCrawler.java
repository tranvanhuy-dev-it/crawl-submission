package util;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import DTO.AddSubmissionDTO;

public class CodeforcesCrawler {

    public static List<AddSubmissionDTO> crawl(String handle, Timestamp lastCrawlDate) {
        List<AddSubmissionDTO> list = new ArrayList<>();

        try {
            String apiUrl = "https://codeforces.com/api/user.status?handle=" + handle + "&from=1&count=50";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            JsonObject response = JsonParser.parseReader(reader).getAsJsonObject();
            reader.close();
            conn.disconnect();

            if (!response.get("status").getAsString().equals("OK")) return list;

            long lastTime = (lastCrawlDate != null) ? lastCrawlDate.getTime() : 0;

            JsonArray result = response.getAsJsonArray("result");

            for (int i = 0; i < result.size(); i++) {
                JsonObject sub = result.get(i).getAsJsonObject();

                long submissionTime = sub.get("creationTimeSeconds").getAsLong() * 1000;

                if (submissionTime <= lastTime) break;

                String verdict = sub.has("verdict") ? sub.get("verdict").getAsString() : "";
                if (!verdict.equals("OK")) continue;

                AddSubmissionDTO dto = new AddSubmissionDTO();
                dto.setRemoteId(sub.get("id").getAsString());
                dto.setProblemTitle(sub.getAsJsonObject("problem").get("name").getAsString());
                dto.setProgrammingLanguage(
                        sub.has("programmingLanguage")
                                ? sub.get("programmingLanguage").getAsString()
                                : "Unknown"
                );
                dto.setSubmissionTime(new Timestamp(submissionTime));

                if (sub.has("program")) {
                    dto.setSourceCode(sub.get("program").getAsString());
                } else {
                    dto.setSourceCode("// Không có source code công khai");
                }

                list.add(dto);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
