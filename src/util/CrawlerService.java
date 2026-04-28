package util;

import java.util.List;
import DTO.AddSubmissionDTO;

import java.sql.Timestamp;
import java.util.ArrayList;

public class CrawlerService {
    public static List<AddSubmissionDTO> crawlData(String platformName, String handle, Timestamp lastCrawlDate) {
        if (platformName.equalsIgnoreCase("Codeforces")) {
            return CodeforcesCrawler.crawl(handle, lastCrawlDate);
        } 
        else if (platformName.equalsIgnoreCase("Vjudge")) {
            return VjudgeCrawler.crawl(handle, lastCrawlDate); 
        }
        return new ArrayList<>();
    }
}