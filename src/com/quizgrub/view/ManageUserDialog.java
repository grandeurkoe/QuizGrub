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
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.quizgrub.dao.DBManager;

public class ManageUserDialog extends JDialog {
	private JTextField userField;
	private JPasswordField passField;
	private JPasswordField confirmPassField;
	private JComboBox<String> roleCombo;
	private JButton saveBtn, deleteBtn, cancelBtn;

	private int userId;
	private String userName;
	private String role;
	private boolean isCurrentAdmin;
	private Runnable onAdminUsernameUpdated;

	public ManageUserDialog(JFrame parent, int userId, String userName, String role, boolean isCurrentAdmin,
			Runnable onAdminUsernameUpdated) {
		super(parent, "QuizGrub - Manage " + userName + "'s Profile.", true);
		this.userId = userId;
		this.userName = userName;
		this.role = role;
		this.isCurrentAdmin = isCurrentAdmin;
		this.onAdminUsernameUpdated = onAdminUsernameUpdated;

		setSize(400, 350);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(parent);
		setResizable(false);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		Font labelFont = new Font("Arial", Font.BOLD, 14);
		Font fieldFont = new Font("Arial", Font.PLAIN, 14);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setFont(labelFont);
		JLabel passLabel = new JLabel("New Password:");
		passLabel.setFont(labelFont);
		JLabel passConfirmLabel = new JLabel("Confirm Password:");
		passConfirmLabel.setFont(labelFont);

		userField = new JTextField(15);
		userField.setFont(fieldFont);

		passField = new JPasswordField(15);
		passField.setFont(fieldFont);

		confirmPassField = new JPasswordField(15);
		confirmPassField.setFont(fieldFont);

		JLabel rollLabel = new JLabel("Role:");
		rollLabel.setFont(labelFont);

		roleCombo = new JComboBox<String>(new String[] { "student", "admin" });
		roleCombo.setFont(fieldFont);
		if (isCurrentAdmin) {
			roleCombo.setEnabled(false);
			roleCombo.setToolTipText("You cannot change the role of the currently logged-in admin.");
		}

		saveBtn = customButton("Save", fieldFont, new Color(60, 179, 113), new Color(34, 139, 34));
		cancelBtn = customButton("Cancel", fieldFont, new Color(220, 20, 60), new Color(139, 0, 0));
		deleteBtn = customButton("Delete", fieldFont, new Color(220, 20, 60), new Color(139, 0, 0));

		if (isCurrentAdmin) {
			deleteBtn.setEnabled(false);
			deleteBtn.setToolTipText("You cannot delete the currently logged-in admin.");
		}

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 0, 10);
		add(userLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 0, 0);
		add(userField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		add(passLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		add(passField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		add(passConfirmLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		add(confirmPassField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(10, 0, 0, 10);
		add(rollLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(10, 10, 0, 0);
		add(roleCombo, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(20, 0, 20, 0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		buttonPanel.add(cancelBtn);
		buttonPanel.add(deleteBtn);
		buttonPanel.add(saveBtn);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, gbc);

		cancelBtn.addActionListener(e -> dispose());
		saveBtn.addActionListener(e -> handleSave());
		deleteBtn.addActionListener(e -> handleDelete());

		getContentPane().setBackground(Color.WHITE);
	}

	private void handleSave() {
		String newUsername = userField.getText().trim();
		String newPassword = new String(passField.getPassword()).trim();
		String confirmPassword = new String(confirmPassField.getPassword()).trim();
		String newRole = isCurrentAdmin ? this.role : (String) roleCombo.getSelectedItem();
		if (newUsername.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Username cannot be empty.");
			return;
		}

		if (!newPassword.isEmpty()) {
			if (!newPassword.equals(confirmPassword)) {
				JOptionPane.showMessageDialog(this, "Password do not match.");
				return;
			}
		}

		try {
			DBManager db = new DBManager();
			boolean updated = db.updateUserAsAdmin(userId, newUsername, newPassword, newRole);
			if (updated) {
				JOptionPane.showMessageDialog(this, "User updated successfully.");

				if (isCurrentAdmin && onAdminUsernameUpdated != null) {
					onAdminUsernameUpdated.run(); // Trigger title update in AdminDashboard
				}

				dispose();
			} else {
				JOptionPane.showMessageDialog(this, "Failed to update user.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void handleDelete() {
		int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this user?",
				"Confirm Deletion", JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			try {
				DBManager db = new DBManager();
				boolean deleted = db.deleteUser(userId);
				if (deleted) {
					JOptionPane.showMessageDialog(this, "User deleted successfully.");
					dispose();
				} else {
					JOptionPane.showMessageDialog(this, "Failed to delete user.");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
			}
		}
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
