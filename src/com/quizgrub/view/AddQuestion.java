package com.quizgrub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.quizgrub.dao.DBManager;

public class AddQuestion extends JFrame {
	private JTextField questionField, optionAField, optionBField, optionCField, optionDField;
	private JComboBox<String> correctAnswerBox, categoryBox, difficultyBox;
	private JButton submitBtn, cancelBtn, importCSVBtn;

	public AddQuestion() {
		setTitle("QuizGrub - Add New Question");
		setSize(600, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		Font labelFont = new Font("Arial", Font.BOLD, 14);
		Font fieldFont = new Font("Arial", Font.PLAIN, 14);

		JLabel questionLabel = new JLabel("Question:");
		questionLabel.setFont(labelFont);
		JLabel optionALabel = new JLabel("Option A:");
		optionALabel.setFont(labelFont);
		JLabel optionBLabel = new JLabel("Option B:");
		optionBLabel.setFont(labelFont);
		JLabel optionCLabel = new JLabel("Option C:");
		optionCLabel.setFont(labelFont);
		JLabel optionDLabel = new JLabel("Option D:");
		optionDLabel.setFont(labelFont);
		JLabel correctLabel = new JLabel("Correct Option:");
		correctLabel.setFont(labelFont);
		JLabel categoryLabel = new JLabel("Category:");
		categoryLabel.setFont(labelFont);
		JLabel difficultyLabel = new JLabel("Difficulty:");
		difficultyLabel.setFont(labelFont);

		questionField = new JTextField(30);
		questionField.setFont(fieldFont);
		optionAField = new JTextField(30);
		optionAField.setFont(fieldFont);
		optionBField = new JTextField(30);
		optionBField.setFont(fieldFont);
		optionCField = new JTextField(30);
		optionCField.setFont(fieldFont);
		optionDField = new JTextField(30);
		optionDField.setFont(fieldFont);

		correctAnswerBox = new JComboBox<String>(new String[] { "A", "B", "C", "D" });
		correctAnswerBox.setFont(fieldFont);
		categoryBox = new JComboBox<String>(new String[] { "General", "Math", "Science", "History", "Technology" });
		categoryBox.setFont(fieldFont);
		difficultyBox = new JComboBox<String>(new String[] { "Easy", "Medium", "Hard" });
		difficultyBox.setFont(fieldFont);

		submitBtn = customButton("Submit", fieldFont, new Color(60, 179, 113), new Color(34, 139, 34));
		cancelBtn = customButton("Cancel", fieldFont, new Color(220, 20, 60), new Color(139, 0, 0));
		importCSVBtn = customButton("Import CSV", fieldFont, new Color(0, 102, 204), new Color(21, 87, 36));

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 20, 10);
		add(questionLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 20, 0);
		add(questionField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 20, 10);
		add(optionALabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 20, 0);
		add(optionAField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 20, 10);
		add(optionBLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 20, 0);
		add(optionBField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 20, 10);
		add(optionCLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 20, 0);
		add(optionCField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 20, 10);
		add(optionDLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 20, 0);
		add(optionDField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 20, 10);
		add(correctLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 20, 0);
		add(correctAnswerBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 20, 10);
		add(categoryLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 20, 0);
		add(categoryBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.EAST;
		gbc.insets = new Insets(0, 0, 20, 10);
		add(difficultyLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 10, 20, 0);
		add(difficultyBox, gbc);

		gbc.gridx = 0;
		gbc.gridy = 8;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 0, 0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
		buttonPanel.add(cancelBtn);
		buttonPanel.add(importCSVBtn);
		buttonPanel.add(submitBtn);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, gbc);

		submitBtn.addActionListener(new SubmitListener());
		cancelBtn.addActionListener(e -> dispose());
		importCSVBtn.addActionListener(e -> importCSV());

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

	private class SubmitListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String question = questionField.getText().trim();
			String a = optionAField.getText().trim();
			String b = optionBField.getText().trim();
			String c = optionCField.getText().trim();
			String d = optionDField.getText().trim();
			String correct = (String) correctAnswerBox.getSelectedItem();
			String category = (String) categoryBox.getSelectedItem();
			String difficulty = (String) difficultyBox.getSelectedItem();

			if (question.isEmpty() || a.isEmpty() || b.isEmpty() || c.isEmpty() || d.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Please fill all the fields.");
				return;
			}

			try {
				DBManager db = new DBManager();
				boolean success = db.addQuestion(question, a, b, c, d, correct, category, difficulty);
				if (success) {
					JOptionPane.showMessageDialog(null, "Question added successfully!");
					dispose();
				} else {
					JOptionPane.showMessageDialog(null, "Failed to add question.");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage());
			}
		}
	}

	private void importCSV() {
		JFileChooser fileChooser = new JFileChooser();
		int result = fileChooser.showOpenDialog(this);

		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			int count = 0;

			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				DBManager db = new DBManager();
				String line;
				boolean firstLine = true;

				while ((line = br.readLine()) != null) {
					if (firstLine) {
						firstLine = false;
						continue;
					}

					String[] parts = line.split(",", -1);
					if (parts.length == 8) {
						boolean success = db.addQuestion(parts[0], parts[1], parts[2], parts[3], parts[4],
								parts[5].trim(), parts[6], parts[7]);
						if (success)
							count++;
					}
				}
				JOptionPane.showMessageDialog(this, count + " questions imported successfully");
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Error importing questions:\n" + e.getMessage());
			}
		}
	}
}
