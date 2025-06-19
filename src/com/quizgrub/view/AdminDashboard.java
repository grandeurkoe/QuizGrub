package com.quizgrub.view;

import com.quizgrub.dao.DBManager;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AdminDashboard extends JFrame {
	private JTable userTable;
	private JTable scoreTable;
	private JButton refreshBtn, logoutBtn, addQuestionBtn, viewQuestionBtn, manageUserBtn;
	private String adminUsername;
	private int adminId;

	public AdminDashboard(String username, int id) {
		this.adminId = id;
		this.adminUsername = username;
		setTitle("QuizGrub - " + username + "'s Dashboard");
		setSize(900, 900);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		Font labelFont = new Font("Arial", Font.BOLD, 14);
		Font fieldFont = new Font("Arial", Font.PLAIN, 14);

		ImageIcon originalLogoIcon = new ImageIcon(getClass().getResource("/images/quizgrub_logo.png"));
		Image scaledImage = originalLogoIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
		ImageIcon logoIcon = new ImageIcon(scaledImage);

		JLabel logoLabel = new JLabel(logoIcon);

		refreshBtn = customButton("Refresh", fieldFont, new Color(60, 179, 113), new Color(34, 139, 34));
		manageUserBtn = customButton("Manage User", new Font("Arial", Font.PLAIN, 14), new Color(255, 165, 0),
				new Color(255, 140, 0));
		addQuestionBtn = customButton("Add Question", fieldFont, new Color(0, 102, 204), new Color(21, 87, 36));
		viewQuestionBtn = customButton("View Questions", fieldFont, new Color(70, 130, 180), new Color(30, 90, 140));
		viewQuestionBtn.setPreferredSize(new Dimension(120, 40));
		logoutBtn = customButton("Logout", fieldFont, new Color(220, 20, 60), new Color(139, 0, 0));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 0, 0);
		add(logoLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		buttonPanel.add(refreshBtn);
		buttonPanel.add(manageUserBtn);
		buttonPanel.add(addQuestionBtn);
		buttonPanel.add(viewQuestionBtn);
		buttonPanel.add(logoutBtn);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, gbc);

		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		userTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		scoreTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane userScroll = new JScrollPane(userTable);
		JScrollPane scoreScroll = new JScrollPane(scoreTable);

		userScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"Registered Users", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 15)));
		scoreScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK), "Scores",
				TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 15)));
		styleTable(userTable);
		styleTable(scoreTable);

		centralPanel.add(userScroll, gbc);

		gbc.gridy = 1;
		centralPanel.add(scoreScroll, gbc);

		centralPanel.setBackground(Color.WHITE);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(20, 0, 0, 0);
		add(centralPanel, gbc);

		getContentPane().setBackground(Color.WHITE);

		refreshBtn.addActionListener(e -> loadData());
		logoutBtn.addActionListener(e -> {
			dispose();
			new Login();
		});
		addQuestionBtn.addActionListener(e -> new AddQuestion());
		viewQuestionBtn.addActionListener(e -> new ViewQuestions());
		manageUserBtn.addActionListener(e -> {
			int selectedRow = userTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(this, "Please select a user to manage.");
				return;
			}

			int userId = (int) userTable.getValueAt(selectedRow, 0);
			String userName = (String) userTable.getValueAt(selectedRow, 1);
			String role = (String) userTable.getValueAt(selectedRow, 2);
			boolean isCurrentAdmin = userId == adminId;
			new ManageUserDialog(this, userId, userName, role, isCurrentAdmin, () -> {
				try {
					DBManager db = new DBManager();
					this.adminUsername = db.getUsernameById(adminId);
					setTitle("QuizGrub - " + this.adminUsername + "'s Dashboard");
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(this, "Error loading data: " + ex.getMessage());

				}

			}).setVisible(true);
		});

		setVisible(true);
	}

	private JButton customButton(String btnText, Font btnFont, Color bgColor, Color borderColor) {
		JButton newBtn = new JButton(btnText);
		newBtn.setFont(btnFont);
		newBtn.setBackground(bgColor);
		newBtn.setForeground(Color.WHITE);
		newBtn.setBorder(BorderFactory.createLineBorder(borderColor, 2));
		newBtn.setPreferredSize(new Dimension(100, 40));
		newBtn.setMargin(new Insets(10, 20, 10, 20));
		return newBtn;
	}

	private void loadData() {
		try {
			DBManager db = new DBManager();

			ResultSet userRs = db.getAllUsers();
			DefaultTableModel userModel = new DefaultTableModel(new String[] { "ID", "Username", "Role" }, 0);
			while (userRs.next()) {
				userModel.addRow(
						new Object[] { userRs.getInt("id"), userRs.getString("username"), userRs.getString("role"), });
			}

			userTable.setModel(userModel);

			ResultSet scoreRs = db.getAllScores();
			DefaultTableModel scoreModel = new DefaultTableModel(
					new String[] { "Student ID", "Score", "Total Questions", "Time Taken" }, 0);
			while (scoreRs.next()) {
				scoreModel.addRow(new Object[] { scoreRs.getString("student_id"), scoreRs.getInt("score"),
						scoreRs.getInt("total_questions"), scoreRs.getInt("time_taken") });
			}
			scoreTable.setModel(scoreModel);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
		}
	}

	private void styleTable(JTable table) {
		table.setFont(new Font("Arial", Font.PLAIN, 14));

		table.setRowHeight(20);

		table.setSelectionBackground(new Color(173, 216, 230)); // Light blue
		table.setSelectionForeground(Color.BLACK);

		table.setDefaultRenderer(Object.class, new TableCellRenderer() {
			private final TableCellRenderer defaultRenderer = table.getDefaultRenderer(Object.class);

			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				Component c = defaultRenderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				if (isSelected) {
					c.setBackground(table.getSelectionBackground());
					c.setForeground(table.getSelectionForeground());
				} else {
					c.setForeground(Color.BLACK);
					if (row % 2 == 0) {
						c.setBackground(new Color(240, 240, 240));
					} else {
						c.setBackground(Color.WHITE);
					}
				}

				if (c instanceof JLabel) {
					((JLabel) c).setHorizontalAlignment(SwingConstants.CENTER);
				}

				return c;
			}
		});

		JTableHeader header = table.getTableHeader();
		header.setFont(new Font("Arial", Font.BOLD, 14));
		header.setForeground(Color.BLACK);
		header.setPreferredSize(new Dimension(0, 40));
	}

	public int getAdminId() {
		return adminId;
	}

	public String getAdminUsername() {
		return adminUsername;
	}
}
