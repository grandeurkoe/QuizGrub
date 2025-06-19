package com.quizgrub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.quizgrub.dao.DBManager;

public class EditStudentDialog extends JDialog {
	private JTextField usernameTxt;
	private JPasswordField passwordTxt, confirmPasswordTxt;
	private JButton saveBtn, cancelBtn;

	public EditStudentDialog(JFrame parent, int studentId, String username) {
		super(parent, "QuizGrub - Edit " + username + "'s Profile.", true);
		setSize(400, 350);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setResizable(false);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		Font labelFont = new Font("Arial", Font.BOLD, 14);
		Font fieldFont = new Font("Arial", Font.PLAIN, 14);

		JLabel userLabel = new JLabel("New Username:");
		userLabel.setFont(labelFont);
		JLabel passLabel = new JLabel("New Password:");
		passLabel.setFont(labelFont);
		JLabel passConfirmLabel = new JLabel("Confirm Password:");
		passConfirmLabel.setFont(labelFont);

		usernameTxt = new JTextField(15);
		usernameTxt.setFont(fieldFont);

		passwordTxt = new JPasswordField(15);
		passwordTxt.setFont(fieldFont);

		confirmPasswordTxt = new JPasswordField(15);
		confirmPasswordTxt.setFont(fieldFont);

		saveBtn = customButton("Save", fieldFont, new Color(60, 179, 113), new Color(34, 139, 34));
		cancelBtn = customButton("Cancel", fieldFont, new Color(220, 20, 60), new Color(139, 0, 0));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 10);
		add(userLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		add(usernameTxt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		add(passLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		add(passwordTxt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		add(passConfirmLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		add(confirmPasswordTxt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(20, 0, 20, 0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPanel.add(cancelBtn);
		buttonPanel.add(saveBtn);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, gbc);

		cancelBtn.addActionListener(e -> dispose());

		saveBtn.addActionListener(e -> {
			String newUsername = usernameTxt.getText().trim();
			String newPassword = new String(passwordTxt.getPassword()).trim();
			String confirmPassword = new String(confirmPasswordTxt.getPassword()).trim();

			if (newUsername.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
				JOptionPane.showMessageDialog(this, "All fields are required.");
				return;
			}

			if (!newPassword.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(this, "Passwords do not match.");
				return;
			}

			try {
				DBManager db = new DBManager();
				boolean updated = db.updateUser(studentId, newUsername, newPassword);
				if (updated) {
					if (parent instanceof StudentDashboard) {
						((StudentDashboard) parent).updateStudentUsername(newUsername);
					}

					JOptionPane.showMessageDialog(this, "Profile updated successfully.");
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Update failed. Try a different username.");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		});

		getContentPane().setBackground(Color.WHITE);

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

}
