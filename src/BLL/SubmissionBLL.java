package BLL;

import java.util.List;
import DAL.SubmissionDAL;
import DAL.AccountDAL;
import DTO.AccountResponse;
import DTO.AddSubmissionDTO;
import Entities.Submission;
import Entities.Account;
import util.CrawlerService; 

public class SubmissionBLL {
    private SubmissionDAL subDal = new SubmissionDAL();
    private AccountDAL accDal = new AccountDAL();

    public List<Submission> GetAllSubmissions() {
        return subDal.GetAllSubmissions();
    }

    public List<Submission> GetSubmissionsByAccount(int accountId) {
        return subDal.GetSubmissionsByAccount(accountId);
    }
    
    public Submission GetSubmissionById(int SubmissionId) {
    	return subDal.GetSubmissionById(SubmissionId);
    }
    
    public void DeleteSubmission(int submissionId) {
        subDal.DeleteSubmission(submissionId);
    }

    public String CrawlLatestSubmissionsForAllAccounts() {
        List<AccountResponse> allAccounts = accDal.GetAllAccounts();
        
        if (allAccounts == null || allAccounts.isEmpty()) return "Không có tài khoản nào!";

        int totalNew = 0;

        for (AccountResponse acc : allAccounts) {
            List<AddSubmissionDTO> crawledList = CrawlerService.crawlData(acc.getPlatformName(), acc.getHandle(), acc.getLastCrawlDate());
            if (crawledList == null || crawledList.isEmpty()) continue;

            for (AddSubmissionDTO dto : crawledList) {
                if (!CheckSubmissionExists(dto.getRemoteId())) {
                    dto.setAccountId(acc.getAccountId());
                    subDal.AddSubmission(dto);
                    totalNew++;
                    accDal.UpdateLastCrawlDate(acc.getAccountId());
                }
            }
        }

        return "Đã cào thành công " + totalNew + " bài nộp mới từ tất cả tài khoản!";
    }


    private boolean CheckSubmissionExists(String remoteId) {
        List<Submission> all = subDal.GetAllSubmissions();
        for (Submission s : all) {
            if (s.getRemoteId().equals(remoteId)) {
                return true; 
            }
        }
        return false;
    }
}