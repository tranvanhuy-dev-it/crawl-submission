package View;

import Entities.Platform;

import javax.swing.*;

import BLL.AccountBLL;
import BLL.PlatformBLL;
import DTO.AddAccountDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.List;

public class AccountForm extends JFrame {

	PlatformBLL plfBll = new PlatformBLL();
	AccountBLL accBll = new AccountBLL();

	private JTextField handle;
	private JComboBox<Platform> cbb;
	private JButton addBtn;

	public AccountForm() {
		setGUI();
		addActionListener();
	}

	private void setGUI() {
		this.setTitle("Thêm tài khoản");
		this.setSize(420, 260);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout(10, 10));
		this.getContentPane().setBackground(new Color(245, 247, 250));

		JLabel title = new JLabel("THÊM TÀI KHOẢN");
		title.setFont(new Font("Segoe UI", Font.BOLD, 20));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		this.add(title, BorderLayout.NORTH);

		JPanel form = new JPanel(new GridBagLayout());
		form.setBackground(new Color(245, 247, 250));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		handle = new JTextField(15);
		handle.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		List<Platform> plfs = plfBll.GetAllPlatform();
		cbb = new JComboBox<>();
		cbb.setFont(new Font("Segoe UI", Font.PLAIN, 14));

		for (Platform p : plfs) {
			cbb.addItem(p);
		}

		addBtn = new JButton("Thêm");
		styleButton(addBtn, new Color(0, 123, 255));

		gbc.gridx = 0;
		gbc.gridy = 0;
		form.add(new JLabel("Handle:"), gbc);

		gbc.gridx = 1;
		form.add(handle, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		form.add(new JLabel("Platform:"), gbc);

		gbc.gridx = 1;
		form.add(cbb, gbc);

		JPanel wrap = new JPanel(new BorderLayout());
		wrap.setBackground(new Color(245, 247, 250));
		wrap.add(form, BorderLayout.CENTER);

		this.add(wrap, BorderLayout.CENTER);

		JPanel bottom = new JPanel();
		bottom.setBackground(new Color(245, 247, 250));
		bottom.add(addBtn);

		this.add(bottom, BorderLayout.SOUTH);

		this.setVisible(true);
	}

	private void addActionListener() {
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Platform plf = (Platform) cbb.getSelectedItem();

				AddAccountDTO acc = new AddAccountDTO();
				acc.setHandle(handle.getText());
				acc.setPlatformId(plf.getPlatformId());
				acc.setAddedDate(new Timestamp(System.currentTimeMillis()));
				acc.setLastCrawlDate(new Timestamp(System.currentTimeMillis()));

				if (accBll.AddAccount(acc)) {
					JOptionPane.showMessageDialog(null, "Thêm tài khoản thành công!");
				} else {
					JOptionPane.showMessageDialog(null, "Thêm tài khoản không thành công!");
				}

				dispose();
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
		btn.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
	}
}
