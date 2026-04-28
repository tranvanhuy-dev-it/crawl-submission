package BLL;

import java.util.List;

import DAL.CodeAnalysisDAL;
import DTO.AddAnalysisDTO;
import Entities.CodeAnalysis;

public class CodeAnalysisBLL {

    CodeAnalysisDAL dal = new CodeAnalysisDAL();

    public List<CodeAnalysis> GetAllAnalysis() {
        return dal.GetAllAnalysis();
    }

    public CodeAnalysis GetAnalysisBySubmission(int submissionId) {
        return dal.GetAnalysisBySubmission(submissionId);
    }

    public void AddAnalysis(AddAnalysisDTO dto) {
        dal.AddAnalysis(dto);
    }

    public void DeleteAnalysis(int analysisId) {
        dal.DeleteAnalysis(analysisId);
    }
}
