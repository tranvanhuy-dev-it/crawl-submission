package DAL;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import DTO.AddAnalysisDTO;
import Database.DBHelper;
import Entities.CodeAnalysis;

public class CodeAnalysisDAL {

    public List<CodeAnalysis> GetAllAnalysis() {
        List<CodeAnalysis> list = new ArrayList<CodeAnalysis>();
        
        String sql = "SELECT * FROM codeAnalysis";
        ResultSet rs = DBHelper.getInstance().GetRecords(sql);
        
        try {
            while (rs != null && rs.next()) {
                CodeAnalysis ca = new CodeAnalysis();
                ca.setAnalysisId(rs.getInt("analysisId"));
                ca.setSubmissionId(rs.getInt("submissionId"));
                ca.setDataStructures(rs.getString("dataStructures"));
                ca.setAlgorithms(rs.getString("algorithms"));
                ca.setAiProbability(rs.getFloat("aiProbability"));
                ca.setAiFeedback(rs.getString("aiFeedback"));
                
                list.add(ca);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public CodeAnalysis GetAnalysisBySubmission(int submissionId) {
        String sql = "SELECT * FROM codeAnalysis WHERE submissionId = ?";
        ResultSet rs = DBHelper.getInstance().GetRecords(sql, submissionId);
        
        try {
            if (rs != null && rs.next()) {
                return new CodeAnalysis(rs.getInt("analysisId"), rs.getInt("submissionId"), rs.getString("dataStructures"), 
                		rs.getString("algorithms"), rs.getFloat("aiProbability"), rs.getString("aiFeedback"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; 
    }

    public void AddAnalysis(AddAnalysisDTO ca) {
        String sql = "INSERT INTO codeAnalysis (submissionId, dataStructures, algorithms, aiProbability, aiFeedback) VALUES (?, ?, ?, ?, ?)";
        
        DBHelper.getInstance().ExecuteDB(sql, 
            ca.getSubmissionId(), 
            ca.getDataStructures(), 
            ca.getAlgorithms(), 
            ca.getAiProbability(), 
            ca.getAiFeedback()
        );
    }

    public void DeleteAnalysis(int analysisId) {
        String sql = "DELETE FROM codeAnalysis WHERE analysisId = ?";
        DBHelper.getInstance().ExecuteDB(sql, analysisId);
    }
}