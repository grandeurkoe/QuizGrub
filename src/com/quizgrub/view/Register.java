package com.quizgrub.view;

import com.quizgrub.dao.DBManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.ResultSet;

public class Register extends JFrame {
	private JTextField userField;
	private JPasswordField passField;
	private JPasswordField confirmPassField;
	private JComboBox<String> roleCombo;
	private JButton backBtn, registerBtn;

	public Register() {
		setTitle("QuizGrub - Register");
		setSize(400, 400);
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

		JLabel userLabel = new JLabel("Username:");
		userLabel.setFont(labelFont);
		JLabel passLabel = new JLabel("Password:");
		passLabel.setFont(labelFont);
		JLabel passConfirmLabel = new JLabel("Confirm Password:");
		passConfirmLabel.setFont(labelFont);
		JLabel rollLabel = new JLabel("Role:");
		rollLabel.setFont(labelFont);

		userField = new JTextField(15);
		userField.setFont(fieldFont);

		passField = new JPasswordField(15);
		passField.setFont(fieldFont);
		
		confirmPassField = new JPasswordField(15);
		confirmPassField.setFont(fieldFont);

		roleCombo = new JComboBox<String>(new String[] { "student", "admin" });
		roleCombo.setFont(fieldFont);

		backBtn = new JButton("Back");
		backBtn.setFont(fieldFont);
		backBtn.setBackground(new Color(60, 179, 113));
		backBtn.setForeground(Color.WHITE);
		backBtn.setBorder(BorderFactory.createLineBorder(new Color(169, 169, 169), 2));
		backBtn.setPreferredSize(new Dimension(100, 40));
		backBtn.setMargin(new Insets(10, 20, 10, 20));

		registerBtn = new JButton("Register");
		registerBtn.setFont(fieldFont);
		registerBtn.setBackground(new Color(70, 130, 180));
		registerBtn.setForeground(Color.WHITE);
		registerBtn.setBorder(BorderFactory.createLineBorder(new Color(21, 87, 36), 2));
		registerBtn.setPreferredSize(new Dimension(100, 40));
		registerBtn.setMargin(new Insets(10, 20, 10, 20));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(0, 0, 20, 0);
		add(logoLabel, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 10);
		add(userLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		add(userField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		add(passLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		add(passField, gbc);
		
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		add(passConfirmLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		add(confirmPassField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		add(rollLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		add(roleCombo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(20, 0, 20, 0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPanel.add(backBtn);
		buttonPanel.add(registerBtn);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, gbc);

		getContentPane().setBackground(Color.WHITE);

		registerBtn.addActionListener(e -> handleRegistration());
		backBtn.addActionListener(e -> {
			dispose();
			new Login();
		});

		setVisible(true);
	}

	private void handleRegistration() {
		String username = userField.getText();
		String password = String.valueOf(passField.getPassword());
		String confirmPassword = new String(confirmPassField.getPassword()).trim();
		String role = (String) roleCombo.getSelectedItem();

		if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
			JOptionPane.showMessageDialog(this, "All fields are required.", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			JOptionPane.showMessageDialog(this, "Passwords do not match.");
			return;
		}

		try {
			DBManager db = new DBManager();
			boolean success = db.insertUser(username, password, role);
			if (success) {
				JOptionPane.showMessageDialog(this, "Registered successfully!");
				dispose();
				new Login();
			} else {
				JOptionPane.showMessageDialog(this, "Registration failed!", "Error", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
		}
	}
}
