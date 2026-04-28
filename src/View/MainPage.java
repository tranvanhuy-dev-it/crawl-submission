package View;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import BLL.AccountBLL;
import BLL.PlatformBLL;
import Entities.Account;
import Entities.Platform;

public class MainPage extends JFrame {
	PlatformBLL plfBll = new PlatformBLL();
	AccountBLL accBll = new AccountBLL();
	JComboBox<Platform> plfs;
	JButton btn;
	DefaultTableModel model = new DefaultTableModel();
	JTable table;
	JButton delbtn = new JButton("Del");
	JButton newbtn = new JButton("New");
	JButton viewbtn = new JButton("View");
	JButton crawlBtn = new JButton("Crawl Code");
	
	public MainPage() {
		SetGUI();
		AddActionListoner();
	}
	
private void SetGUI() {
	this.setSize(900, 600);
	this.setTitle("BTL Java");
	this.setLocationRelativeTo(null);
	this.setLayout(new BorderLayout(12, 12));
	this.getContentPane().setBackground(new Color(245, 247, 250));

	JPanel header = new JPanel(new BorderLayout());
	header.setBackground(new Color(245, 247, 250));

	JLabel lb = new JLabel("DANH SÁCH TÀI KHOẢN");
	lb.setFont(new Font("Segoe UI", Font.BOLD, 26));
	lb.setForeground(new Color(33, 37, 41));
	lb.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

	header.add(lb, BorderLayout.WEST);

	btn = new JButton("Lọc");
	styleButton(btn, new Color(0, 123, 255));

	plfs = new JComboBox<>();
	plfs.setFont(new Font("Segoe UI", Font.PLAIN, 14));
	plfs.addItem(new Platform(0, "Tất cả", ""));

	List<Platform> platforms = plfBll.GetAllPlatform();
	for (Platform p : platforms) {
		plfs.addItem(p);
	}

	JPanel filterPanel = new JPanel(new GridLayout(1, 2, 10, 10));
	filterPanel.setBackground(new Color(245, 247, 250));
	filterPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 15, 10, 15));

	filterPanel.add(plfs);
	filterPanel.add(btn);

	header.add(filterPanel, BorderLayout.SOUTH);

	this.add(header, BorderLayout.NORTH);

	String[] col = {"ID", "Tên", "Plf Name", "Added Date", "LastCraw Date"};
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
	table.getTableHeader().setForeground(new Color(50, 50, 50));

	JScrollPane scrollPane = new JScrollPane(table);
	scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 15));

	this.add(scrollPane, BorderLayout.CENTER);

	LoadData();

	styleButton(delbtn, new Color(220, 53, 69));
	styleButton(newbtn, new Color(0, 123, 255));
	styleButton(viewbtn, new Color(255, 193, 7));
	styleButton(crawlBtn, new Color(108, 117, 125));

	JPanel botbar = new JPanel(new GridLayout(1, 4, 12, 12));
	botbar.setBackground(new Color(245, 247, 250));
	botbar.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 15, 15));

	botbar.add(crawlBtn);
	botbar.add(newbtn);
	botbar.add(viewbtn);
	botbar.add(delbtn);

	this.add(botbar, BorderLayout.SOUTH);

	this.setVisible(true);
}


	
	private void LoadData() {
		model.setRowCount(0);
		Platform selected = (Platform) plfs.getSelectedItem();
		if (selected.getPlatformId() == 0) {
			List<Account> acc = accBll.GetAllAccounts();
			for (Account a : acc) {
				model.addRow(new Object[] {
					a.getAccountId(), 
					a.getHandle(),
					a.getPlatformName(),
					a.getAddedDate(),
					a.getLastCrawlDate()
				});
			}
		} else {
			List<Account> acc = accBll.GetAccountByPlatform(selected.getPlatformId());
			for (Account a : acc) {
				model.addRow(new Object[] {
					a.getAccountId(), 
					a.getHandle(),
					a.getPlatformName(),
					a.getAddedDate(),
					a.getLastCrawlDate()
				});
			}
		}
	}
	
	private void AddActionListoner() {
//		editbtn.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
		
		delbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if (row == -1) {
					return;
				}
				int confirm = JOptionPane.showConfirmDialog(
			            null,
			            "Bạn chắc chắn muốn xóa tài khoản này không? ",
			            "Xác nhận xóa",
			            JOptionPane.YES_NO_OPTION );
			        if (confirm != JOptionPane.YES_OPTION) {
			            return;
			        }
			        int id = (int) table.getValueAt(row, 0);
			        accBll.DeleteAccount(id);
			        ((DefaultTableModel) table.getModel()).removeRow(row); 
			        JOptionPane.showMessageDialog(null, "Đã xóa thành công");
			}
		});
		
		newbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountForm form = new AccountForm();
		        form.addWindowListener(new java.awt.event.WindowAdapter() {
		            @Override
		            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
		                LoadData();
		            }
		        });
			}
		});
		
		viewbtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				int id = (int) table.getValueAt(row, 0);
				new SubmissionPage(id);
			}
		});
		
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				LoadData();
			}
		});
		
		crawlBtn.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        int confirm = JOptionPane.showConfirmDialog(
		            null,
		            "Bạn có chắc muốn crawl tất cả bài nộp mới nhất của tất cả tài khoản không?",
		            "Xác nhận crawl",
		            JOptionPane.YES_NO_OPTION
		        );
		        if (confirm != JOptionPane.YES_OPTION) return;

		        new Thread(() -> {
		            SwingUtilities.invokeLater(() -> {
		                crawlBtn.setEnabled(false);
		                crawlBtn.setText("Đang crawl...");
		            });

		            BLL.SubmissionBLL subBll = new BLL.SubmissionBLL();
		            String result = subBll.CrawlLatestSubmissionsForAllAccounts();

		            SwingUtilities.invokeLater(() -> {
		                crawlBtn.setEnabled(true);
		                crawlBtn.setText("Crawl Code");
		                LoadData(); 
		                JOptionPane.showMessageDialog(null, result);
		            });
		        }).start();
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

		btn.setBorder(javax.swing.BorderFactory.createEmptyBorder(8, 12, 8, 12));
	}


}
