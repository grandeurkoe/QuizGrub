package com.quizgrub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StudentDashboard extends JFrame {
	private JButton startQuizBtn, viewResultsBtn, logoutBtn, charBtn, editBtn;
	private String studentName;
	private int studentId;
	private JLabel welcomeLabel;

	public StudentDashboard(String username, int id) {
		this.studentName = username;
		this.studentId = id;

		setTitle("QuizGrub - " + studentName + "'s Dashboard");
		setSize(800, 400);
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

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 0, 0);
		add(logoLabel, gbc);

		startQuizBtn = customButton("Start Quiz", fieldFont, new Color(60, 179, 113), new Color(34, 139, 34));
		charBtn = customButton("View Chart", fieldFont, new Color(255, 165, 0), new Color(255, 140, 0));
		viewResultsBtn = customButton("View Results", fieldFont, new Color(70, 130, 180), new Color(30, 90, 140));
		logoutBtn = customButton("Logout", fieldFont, new Color(220, 20, 60), new Color(139, 0, 0));
		editBtn = customButton("Edit Profile", fieldFont, new Color(52, 152, 219), new Color(41, 128, 185));

		welcomeLabel = new JLabel("Welcome, " + username + "!");
		welcomeLabel.setFont(new Font("Arial", Font.BOLD, 22));
		welcomeLabel.setBorder(BorderFactory.createEmptyBorder());

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 20, 0);
		add(welcomeLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		buttonPanel.add(startQuizBtn);
		buttonPanel.add(viewResultsBtn);
		buttonPanel.add(charBtn);
		buttonPanel.add(editBtn);
		buttonPanel.add(logoutBtn);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, gbc);

		logoutBtn.addActionListener(e -> {
			new Login();
			dispose();
		});

		startQuizBtn.addActionListener(e -> new TakeQuiz(studentId));
		viewResultsBtn.addActionListener(e -> new ViewResults(studentId, studentName));
		charBtn.addActionListener(e -> new PerformanceChart(studentId, studentName).setVisible(true));
		editBtn.addActionListener(e -> {
			new EditStudentDialog(this, studentId, username).setVisible(true);
		});

		getContentPane().setBackground(Color.WHITE);
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

	public void updateStudentUsername(String newUsername) {
		this.studentName = newUsername;
		setTitle("QuizGrub - " + studentName + "'s Dashboard");
		welcomeLabel.setText("Welcome, " + newUsername + "!");
	}
}
