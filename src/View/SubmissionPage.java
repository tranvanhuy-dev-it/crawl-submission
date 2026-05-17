package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import BLL.AccountBLL;
import DTO.AccountResponse;
import java.text.SimpleDateFormat;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BLL.SubmissionBLL;
import Entities.Submission;

public class SubmissionPage extends JFrame {

    SubmissionBLL subBll = new SubmissionBLL();
    AccountBLL accBll = new AccountBLL();
    DefaultTableModel model = new DefaultTableModel();
    JTable table;

    JButton delbtn = new JButton("Del");
    JButton viewbtn = new JButton("View");
    JButton analyzeBtn = new JButton("Analyze AI");

    public SubmissionPage(int accountId) {
        SetGUI(accountId);
        AddActionListener();
    }

    private void SetGUI(int accountId) {
        this.setSize(900, 600);
        this.setTitle("Danh sách Submission");
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(12, 12));
        this.getContentPane().setBackground(new Color(245, 247, 250));

        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(245, 247, 250));

        JLabel lb = new JLabel("DANH SÁCH SUBMISSION");
        lb.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lb.setForeground(new Color(33, 37, 41));
        lb.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        AccountResponse acc = accBll.GetAccountById(accountId);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String lastCrawl = (acc != null && acc.getLastCrawlDate() != null) ? sdf.format(acc.getLastCrawlDate()) : "N/A";
        JLabel accInfo;
        if (acc != null) {
            accInfo = new JLabel("Handle: " + acc.getHandle() + " | Platform: " + acc.getPlatformName() + " | Last Crawl: " + lastCrawl);
        } else {
            accInfo = new JLabel("Không tìm thấy thông tin account");
        }

        accInfo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        accInfo.setForeground(new Color(100, 100, 100));
        accInfo.setBorder(BorderFactory.createEmptyBorder(0, 15, 10, 15));

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(245, 247, 250));

        lb.setAlignmentX(LEFT_ALIGNMENT);
        accInfo.setAlignmentX(LEFT_ALIGNMENT);

        textPanel.add(lb);
        textPanel.add(accInfo);

        header.add(textPanel, BorderLayout.WEST);

        this.add(header, BorderLayout.NORTH);

        String[] col = {"ID", "RemoteId", "Problem", "Language", "Time"};

        model.setColumnIdentifiers(col);

        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.setGridColor(new Color(220, 220, 220));
        table.setShowHorizontalLines(true);
        table.setShowVerticalLines(false);
        table.setSelectionBackground(new Color(220, 240, 255));

        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        this.add(scroll, BorderLayout.CENTER);

        LoadData(accountId);

        styleButton(delbtn, new Color(220, 53, 69));
        styleButton(viewbtn, new Color(255, 193, 7));
        styleButton(analyzeBtn, new Color(108, 117, 125));

        JPanel bot = new JPanel(new GridLayout(1, 3, 12, 12));
        bot.setBackground(new Color(245, 247, 250));
        bot.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));

        bot.add(delbtn);
        bot.add(viewbtn);
        bot.add(analyzeBtn);

        this.add(bot, BorderLayout.SOUTH);

        this.setVisible(true);
    }

    private void LoadData(int accountId) {
        model.setRowCount(0);
        List<Submission> list = subBll.GetSubmissionsByAccount(accountId);
        for (Submission s : list) {
            model.addRow(new Object[]{
                    s.getSubmissionId(),
                    s.getRemoteId(),
                    s.getProblemTitle(),
                    s.getProgrammingLanguage(),
                    s.getSubmissionTime()
            });
        }
    }

    private void AddActionListener() {

        delbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int[] rows = table.getSelectedRows();
                if (rows.length == 0) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Vui lòng chọn một tài khoản để xem!",
                            "Thông báo",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                        SubmissionPage.this,
                        "Bạn chắc chắn muốn xóa " + rows.length + " submission không?",
                        "Xác nhận xóa",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm != JOptionPane.YES_OPTION) return;

                for (int i = rows.length - 1; i >= 0; i--) {
                    int row = rows[i];

                    int id = Integer.parseInt(table.getValueAt(row, 0).toString());

                    subBll.DeleteSubmission(id);
                    model.removeRow(row);
                }

                JOptionPane.showMessageDialog(SubmissionPage.this, "Đã xóa " + rows.length + " submission");
            }
        });

        viewbtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = (int) table.getValueAt(row, 0);
                Submission s = subBll.GetSubmissionById(id);
                new SubmissionDetailPage(s);
            }
        });

        analyzeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if (row == -1) {
                    return;
                }
                int id = (int) table.getValueAt(row, 0);
                new AnalysisPage(id);
            }
        });
    }

    private void styleButton(JButton btn, Color color) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
    }
}