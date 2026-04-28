package View;

import java.awt.*;
import javax.swing.*;

import BLL.CodeAnalysisBLL;
import BLL.SubmissionBLL;
import DTO.AddAnalysisDTO;
import Entities.CodeAnalysis;
import Entities.Submission;
import util.GroqService;

public class AnalysisPage extends JFrame {

    CodeAnalysisBLL caBll = new CodeAnalysisBLL();
    SubmissionBLL subBll = new SubmissionBLL();

    JTextArea area = new JTextArea();

    public AnalysisPage(int submissionId) {
        this.setTitle("AI Analysis");
        this.setSize(750, 550);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(12, 12));
        this.getContentPane().setBackground(new Color(245, 247, 250));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(245, 247, 250));

        JLabel title = new JLabel("KẾT QUẢ PHÂN TÍCH AI");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(33, 37, 41));
        title.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        JLabel status = new JLabel("Đang xử lý dữ liệu...");
        status.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        status.setForeground(new Color(120, 120, 120));
        status.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

        header.add(title, BorderLayout.NORTH);
        header.add(status, BorderLayout.SOUTH);

        this.add(header, BorderLayout.NORTH);

        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setEditable(false);
        area.setBackground(new Color(30, 30, 30));
        area.setForeground(new Color(220, 220, 220));
        area.setCaretColor(Color.WHITE);
        area.setMargin(new Insets(10, 10, 10, 10));
        area.setText("⏳ Đang kiểm tra dữ liệu...");

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        this.add(scroll, BorderLayout.CENTER);

        this.setVisible(true);

        new Thread(() -> {
            SwingUtilities.invokeLater(() -> status.setText("Đang phân tích AI..."));

            loadData(submissionId);

            SwingUtilities.invokeLater(() -> status.setText("Hoàn tất phân tích ✔"));
        }).start();
    }

    private void loadData(int submissionId) {
        CodeAnalysis ca = caBll.GetAnalysisBySubmission(submissionId);

        if (ca == null) {
            updateText("Chưa có dữ liệu, đang phân tích AI...\n(Vui lòng chờ chút)");

            ca = generateAnalysis(submissionId);
        }

        if (ca != null) {
            String result =
                "Data Structures:\n" + ca.getDataStructures() + "\n\n" +
                "Algorithms:\n" + ca.getAlgorithms() + "\n\n" +
                "AI Probability: " + ca.getAiProbability() + "%\n\n" +
                "Feedback:\n" + ca.getAiFeedback();

            updateText(result);
        } else {
            updateText("Không thể phân tích code này");
        }
    }

    private void updateText(String text) {
        SwingUtilities.invokeLater(() -> area.setText(text));
    }

    private CodeAnalysis generateAnalysis(int submissionId) {
        try {
            Submission s = subBll.GetSubmissionById(submissionId);
            if (s == null || s.getSourceCode() == null) return null;

            AddAnalysisDTO dto = GroqService.analyzeCode(s.getSourceCode());
            if (dto == null) return null;

            dto.setSubmissionId(submissionId);

            caBll.AddAnalysis(dto);

            return caBll.GetAnalysisBySubmission(submissionId);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
