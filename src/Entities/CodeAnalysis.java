package Entities;

public class CodeAnalysis {
    private int analysisId;
    private int submissionId;
    private String dataStructures;
    private String algorithms;
    private Float aiProbability;
    private String aiFeedback;

    public CodeAnalysis() {
    }

    public CodeAnalysis(int analysisId, int submissionId, String dataStructures, String algorithms, Float aiProbability, String aiFeedback) {
        this.analysisId = analysisId;
        this.submissionId = submissionId;
        this.dataStructures = dataStructures;
        this.algorithms = algorithms;
        this.aiProbability = aiProbability;
        this.aiFeedback = aiFeedback;
    }

    
    public int getAnalysisId() { return analysisId; }
    public void setAnalysisId(int analysisId) { this.analysisId = analysisId; }
    public int getSubmissionId() { return submissionId; }
    public void setSubmissionId(int submissionId) { this.submissionId = submissionId; }
    public String getDataStructures() { return dataStructures; }
    public void setDataStructures(String dataStructures) { this.dataStructures = dataStructures; }
    public String getAlgorithms() { return algorithms; }
    public void setAlgorithms(String algorithms) { this.algorithms = algorithms; }
    public Float getAiProbability() { return aiProbability; }
    public void setAiProbability(Float aiProbability) { this.aiProbability = aiProbability; }
    public String getAiFeedback() { return aiFeedback; }
    public void setAiFeedback(String aiFeedback) { this.aiFeedback = aiFeedback; }
}