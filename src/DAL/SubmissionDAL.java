package DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.AddSubmissionDTO;
import Database.DBHelper;
import Entities.Submission;

public class SubmissionDAL {

    public List<Submission> GetAllSubmissions() {
        List<Submission> list = new ArrayList<Submission>();
        
        String sql = "SELECT * FROM submissions";
        ResultSet rs = DBHelper.getInstance().GetRecords(sql);
        
        try {
            while (rs != null && rs.next()) {
                Submission sub = new Submission();
                
                sub.setSubmissionId(rs.getInt("submissionId"));
                sub.setAccountId(rs.getInt("accountId"));
                sub.setRemoteId(rs.getString("remoteId"));
                sub.setProblemTitle(rs.getString("problemTitle"));
                sub.setProgrammingLanguage(rs.getString("programmingLanguage"));
                sub.setSourceCode(rs.getString("sourceCode"));
                sub.setSubmissionTime(rs.getTimestamp("submissionTime"));
                
                list.add(sub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public Submission GetSubmissionById(int submissionId) {
    	String sql = "SELECT * FROM submissions WHERE submissionId = ?";
	    ResultSet rs = DBHelper.getInstance().GetRecords(sql, submissionId);
	    
	    Submission sub = null; 
	    try {
	        if (rs != null && rs.next()) {
	        	sub = new Submission();
                
                sub.setSubmissionId(rs.getInt("submissionId"));
                sub.setAccountId(rs.getInt("accountId"));
                sub.setRemoteId(rs.getString("remoteId"));
                sub.setProblemTitle(rs.getString("problemTitle"));
                sub.setProgrammingLanguage(rs.getString("programmingLanguage"));
                sub.setSourceCode(rs.getString("sourceCode"));
                sub.setSubmissionTime(rs.getTimestamp("submissionTime"));
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return sub; 
    }

    public void AddSubmission(AddSubmissionDTO sub) {
        String sql = "INSERT INTO submissions (accountId, remoteId, "
        		+ "problemTitle, programmingLanguage, sourceCode, submissionTime) VALUES (?, ?, ?, ?, ?, ?)";
        
        DBHelper.getInstance().ExecuteDB(sql, sub.getAccountId(), sub.getRemoteId(), 
        		sub.getProblemTitle(), sub.getProgrammingLanguage(), sub.getSourceCode(), sub.getSubmissionTime());
    }

    public void DeleteSubmission(int submissionId) {
        String sql1 = "DELETE FROM codeAnalysis WHERE submissionId = ?";
        DBHelper.getInstance().ExecuteDB(sql1, submissionId);

        String sql2 = "DELETE FROM submissions WHERE submissionId = ?";
        DBHelper.getInstance().ExecuteDB(sql2, submissionId);
    }

    public List<Submission> GetSubmissionsByAccount(int accountId) {
        List<Submission> list = new ArrayList<Submission>();
        
        String sql = "SELECT * FROM submissions WHERE accountId = ?";
        ResultSet rs = DBHelper.getInstance().GetRecords(sql, accountId);
        
        try {
            while (rs != null && rs.next()) {
                Submission sub = new Submission();
                
                sub.setSubmissionId(rs.getInt("submissionId"));
                sub.setAccountId(rs.getInt("accountId"));
                sub.setRemoteId(rs.getString("remoteId"));
                sub.setProblemTitle(rs.getString("problemTitle"));
                sub.setProgrammingLanguage(rs.getString("programmingLanguage"));
                sub.setSourceCode(rs.getString("sourceCode"));
                sub.setSubmissionTime(rs.getTimestamp("submissionTime"));
                
                list.add(sub);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}