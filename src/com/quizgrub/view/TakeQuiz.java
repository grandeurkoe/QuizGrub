package com.quizgrub.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.Timer;

import com.quizgrub.dao.DBManager;
import com.quizgrub.model.Question;

public class TakeQuiz extends JFrame {
	private int studentId;
	private ArrayList<Question> questions;
	private int currentQuestionIndex = 0;
	private int score = 0;

	private Timer countdownTimer;
	private int totalTime = 300;
	private int timeLeft = totalTime;

	private JLabel questionLabel, timerLabel;
	private JRadioButton[] options;
	private ButtonGroup optionGroup;
	private JButton nextBtn;

	public TakeQuiz(int studentId) {
		this.studentId = studentId;

		setTitle("QuizGrub - Take Quiz");
		setSize(800, 500);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		loadQuestions();
		loadUI();
		if (!questions.isEmpty()) {
			displayQuestion(0);
			startQuizTimer();
		} else {
			return;
		}

		getContentPane().setBackground(Color.WHITE);
		setVisible(true);
	}

	private void loadQuestions() {
		questions = new ArrayList<>();
		try {
			DBManager db = new DBManager();
			ResultSet rs = db.getRandomQuestions();

			while (rs.next()) {
				questions.add(new Question(rs.getInt("id"), rs.getString("question_text"), rs.getString("option_a"),
						rs.getString("option_b"), rs.getString("option_c"), rs.getString("option_d"),
						rs.getString("correct_option")));
			}

			if (questions.isEmpty()) {
				JOptionPane.showMessageDialog(this, "No questions found.");
				dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error loading questions.");
		}
	}

	private void loadUI() {
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		timerLabel = new JLabel("Timer: 05:00");
		timerLabel.setFont(new Font("Arial", Font.BOLD, 18));
		timerLabel.setBorder(BorderFactory.createEmptyBorder());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 20, 0);
		add(timerLabel, gbc);

		questionLabel = new JLabel("Question text");
		questionLabel.setFont(new Font("Arial", Font.BOLD, 16));
		questionLabel.setBorder(BorderFactory.createEmptyBorder());

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.insets = new Insets(20, 0, 20, 0);
		add(questionLabel, gbc);

		JPanel optionsPanel = new JPanel();
		optionsPanel.setLayout(new GridBagLayout());
		optionsPanel.setBorder(BorderFactory.createEmptyBorder());
		optionsPanel.setBackground(Color.WHITE);

		gbc.gridwidth = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(10, 0, 10, 0);

		options = new JRadioButton[4];
		optionGroup = new ButtonGroup();

		for (int i = 0; i < 4; i++) {
			options[i] = new JRadioButton("Option " + (char) ('A' + i));
			options[i].setFont(new Font("Arial", Font.PLAIN, 14));
			options[i].setBackground(Color.WHITE);
			optionGroup.add(options[i]);
			optionsPanel.add(options[i], gbc);
			gbc.gridy++;
		}

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		add(optionsPanel, gbc);

		nextBtn = new JButton("Next");
		nextBtn.setFont(new Font("Arial", Font.PLAIN, 14));
		nextBtn.setBackground(new Color(60, 179, 113));
		nextBtn.setForeground(Color.WHITE);
		nextBtn.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34), 2));
		nextBtn.setPreferredSize(new Dimension(100, 40));
		nextBtn.setMargin(new Insets(10, 20, 10, 20));

		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		add(nextBtn, gbc);

		nextBtn.addActionListener(e -> handleNext());
	}

	private void handleNext() {
		if (optionGroup.getSelection() == null) {
			JOptionPane.showMessageDialog(this, "Please select an answer.");
			return;
		}

		Question current = questions.get(currentQuestionIndex);
		String selected = "";

		for (int i = 0; i < 4; i++) {
			if (options[i].isSelected()) {
				selected = String.valueOf((char) ('A' + i));
				break;
			}
		}

		if (selected.equalsIgnoreCase(current.getCorrectOption())) {
			score++;
		}

		currentQuestionIndex++;

		if (currentQuestionIndex < questions.size()) {
			displayQuestion(currentQuestionIndex);
		} else {
			saveResult();
			JOptionPane.showMessageDialog(this, "Quiz finished! Your score: " + score + "/" + questions.size());
			dispose();
		}
	}

	private void displayQuestion(int index) {
		Question q = questions.get(index);
		questionLabel.setText("Q" + (index + 1) + ": " + q.getQuestionText());

		options[0].setText("A. " + q.getOptionA());
		options[1].setText("B. " + q.getOptionB());
		options[2].setText("C. " + q.getOptionC());
		options[3].setText("D. " + q.getOptionD());

		optionGroup.clearSelection();
	}

	private void saveResult() {
		if (countdownTimer != null && countdownTimer.isRunning()) {
			countdownTimer.stop();
		}
		try {
			DBManager db = new DBManager();
			db.saveScore(studentId, score, questions.size(), totalTime - timeLeft);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Failed to save score.");
		}
	}

	private void startQuizTimer() {
		countdownTimer = new Timer(1000, e -> {
			timeLeft--;
			int minutes = timeLeft / 60;
			int seconds = timeLeft % 60;
			timerLabel.setText(String.format("Timer: %02d:%02d", minutes, seconds));

			if (timeLeft <= 0) {
				countdownTimer.stop();
				JOptionPane.showMessageDialog(null, "Time's up! Submitting your quiz.");
				saveResult();
				dispose();
			}
		});
		countdownTimer.start();
	}
}
