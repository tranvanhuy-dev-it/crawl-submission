package View;

import java.awt.*;
import javax.swing.*;

import Entities.Submission;

public class SubmissionDetailPage extends JFrame {

	public SubmissionDetailPage(Submission s) {
		this.setTitle("Chi tiết Submission");
		this.setSize(900, 600);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout(12, 12));
		this.getContentPane().setBackground(new Color(245, 247, 250));

		// ================= HEADER =================
		JLabel title = new JLabel("CHI TIẾT SUBMISSION");
		title.setFont(new Font("Segoe UI", Font.BOLD, 26));
		title.setForeground(new Color(33, 37, 41));
		title.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		this.add(title, BorderLayout.NORTH);

		// ================= INFO PANEL =================
		JPanel info = new JPanel(new GridLayout(2, 2, 10, 10));
		info.setBackground(Color.WHITE);
		info.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

		JLabel lbProblem = new JLabel("Problem: " + s.getProblemTitle());
		JLabel lbLanguage = new JLabel("Language: " + s.getProgrammingLanguage());
		JLabel lbTime = new JLabel("Time: " + s.getSubmissionTime());
		JLabel lbRemoteId = new JLabel("Remote ID: " + s.getRemoteId());

		styleLabel(lbProblem);
		styleLabel(lbLanguage);
		styleLabel(lbTime);
		styleLabel(lbRemoteId);

		info.add(lbProblem);
		info.add(lbLanguage);
		info.add(lbTime);
		info.add(lbRemoteId);

		this.add(info, BorderLayout.BEFORE_FIRST_LINE);

		// ================= CODE AREA =================
		JTextArea codeArea = new JTextArea();
		codeArea.setText(s.getSourceCode());
		codeArea.setFont(new Font("Consolas", Font.PLAIN, 14));
		codeArea.setEditable(false);
		codeArea.setBackground(new Color(30, 30, 30));
		codeArea.setForeground(new Color(220, 220, 220));
		codeArea.setCaretColor(Color.WHITE);
		codeArea.setMargin(new Insets(10, 10, 10, 10));

		JScrollPane scroll = new JScrollPane(codeArea);
		scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

		this.add(scroll, BorderLayout.CENTER);

		this.setVisible(true);
	}

	private void styleLabel(JLabel lb) {
		lb.setFont(new Font("Segoe UI", Font.BOLD, 14));
		lb.setForeground(new Color(50, 50, 50));
	}

}
