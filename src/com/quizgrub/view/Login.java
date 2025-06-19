package com.quizgrub.view;

import com.quizgrub.dao.DBManager;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;

public class Login extends JFrame {
	private JTextField userField;
	private JPasswordField passField;
	private JButton loginBtn, registerBtn;

	public Login() {
		setTitle("QuizGrub - Login");
		setSize(400, 350);
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

		userField = new JTextField(15);
		userField.setFont(fieldFont);

		passField = new JPasswordField(15);
		passField.setFont(fieldFont);

		loginBtn = new JButton("Login");
		loginBtn.setFont(fieldFont);
		loginBtn.setBackground(new Color(60, 179, 113));
		loginBtn.setForeground(Color.WHITE);
		loginBtn.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
		loginBtn.setPreferredSize(new Dimension(100, 40));
		loginBtn.setMargin(new Insets(10, 20, 10, 20));

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
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(20, 0, 20, 0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPanel.add(loginBtn);
		buttonPanel.add(registerBtn);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, gbc);

		getContentPane().setBackground(Color.WHITE);

		loginBtn.addActionListener(e -> attemptLogin());
		registerBtn.addActionListener(e -> {
			dispose();
			new Register();
		});

		setVisible(true);
	}

	private void attemptLogin() {
		String username = userField.getText();
		String password = String.valueOf(passField.getPassword());

		if (username.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Both fields must be filled!", "Validation Error",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		try {
			DBManager db = new DBManager();
			ResultSet rs = db.checkUser(username, password);
			if (rs.next()) {
				String role = rs.getString("role");
				int id = rs.getInt("id");
				JOptionPane.showMessageDialog(this, "Login successfully as " + role);

				dispose();
				if (role.equalsIgnoreCase("admin")) {
					new AdminDashboard(username, id);
				} else {
					new StudentDashboard(username, id);
				}
			} else {
				JOptionPane.showMessageDialog(this, "Invalid credentials", "Login Failed", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
		}
	}
}
