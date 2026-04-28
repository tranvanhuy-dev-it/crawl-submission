package DTO;

public class AddAnalysisDTO {
    private int submissionId;
    private String dataStructures;
    private String algorithms;
    private Float aiProbability;
    private String aiFeedback;

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