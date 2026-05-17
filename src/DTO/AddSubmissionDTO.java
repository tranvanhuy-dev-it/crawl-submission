package DTO;

import java.sql.Timestamp;

public class AddSubmissionDTO {
    private int accountId;
    private String remoteId;
    private String problemTitle;
    private String programmingLanguage;
    private String sourceCode;
    private Timestamp submissionTime;

    public int getAccountId() { return accountId; }
    public void setAccountId(int accountId) { this.accountId = accountId; }
    
    public String getRemoteId() { return remoteId; }
    public void setRemoteId(String remoteId) { this.remoteId = remoteId; }
    
    public String getProblemTitle() { return problemTitle; }
    public void setProblemTitle(String problemTitle) { this.problemTitle = problemTitle; }
    
    public String getProgrammingLanguage() { return programmingLanguage; }
    public void setProgrammingLanguage(String programmingLanguage) { this.programmingLanguage = programmingLanguage; }
    
    public String getSourceCode() { return sourceCode; }
    public void setSourceCode(String sourceCode) { this.sourceCode = sourceCode; }
    
    public Timestamp getSubmissionTime() { return submissionTime; }
    public void setSubmissionTime(Timestamp submissionTime) { this.submissionTime = submissionTime; }
}