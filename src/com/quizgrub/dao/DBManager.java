package com.quizgrub.dao;

import java.sql.*;

public class DBManager {
	private Connection con;

	public DBManager() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quizgrub", "root", "172958");
	}

	// Register new user (student or admin)
	public boolean insertUser(String username, String password, String role) throws Exception {
		PreparedStatement pst = con.prepareStatement("INSERT INTO users(username, password, role) VALUES(?, ?, ?)");
		pst.setString(1, username);
		pst.setString(2, password);
		pst.setString(3, role);
		return pst.executeUpdate() > 0;
	}

	// Check login credentials
	public ResultSet checkUser(String username, String password) throws Exception {
		PreparedStatement pst = con.prepareStatement("SELECT * FROM users where username=? AND password=?");
		pst.setString(1, username);
		pst.setString(2, password);
		return pst.executeQuery();
	}

	// Fetch all quiz questions
	public ResultSet getQuestions() throws Exception {
		Statement st = con.createStatement();
		return st.executeQuery("SELECT * FROM questions");
	}

	// Fetch 10 random questions.
	public ResultSet getRandomQuestions() throws Exception {
		Statement st = con.createStatement();
		return st.executeQuery("SELECT * FROM questions ORDER BY RAND() LIMIT 10");
	}

	// Save user score
	public void saveScore(int studentId, int score, int totalQuestions, int timeTaken) throws Exception {
		PreparedStatement pst = con.prepareStatement(
				"INSERT INTO scores(student_id, score, total_questions, time_taken) VALUES(?, ?, ?, ?)");
		pst.setInt(1, studentId);
		pst.setInt(2, score);
		pst.setInt(3, totalQuestions);
		pst.setInt(4, timeTaken);
		pst.executeUpdate();
	}

	// Fetch a specific user's scores.
	public ResultSet getUserScores(int student_id) throws Exception {
		PreparedStatement pst = con.prepareStatement(
				"SELECT score, total_questions, time_taken, timestamp FROM scores WHERE student_id=?");
		pst.setInt(1, student_id);
		return pst.executeQuery();
	}

	// Fetch all scores (for admin charts)
	public ResultSet getAllScores() throws Exception {
		Statement st = con.createStatement();
		return st.executeQuery("SELECT student_id, score, total_questions, time_taken FROM scores");
	}

	// Fetch all users (for admin)
	public ResultSet getAllUsers() throws Exception {
		Statement st = con.createStatement();
		return st.executeQuery("SELECT * FROM users");
	}

	// Add new question.
	public boolean addQuestion(String question, String a, String b, String c, String d, String correct, String category,
			String difficulty) throws Exception {
		PreparedStatement pst = con.prepareStatement(
				"INSERT INTO questions(question_text, option_a, option_b, option_c, option_d, correct_option, category, difficulty)"
						+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
		pst.setString(1, question);
		pst.setString(2, a);
		pst.setString(3, b);
		pst.setString(4, c);
		pst.setString(5, d);
		pst.setString(6, correct);
		pst.setString(7, category);
		pst.setString(8, difficulty);
		return pst.executeUpdate() > 0;
	}

	// Update user profile.
	public boolean updateUser(int userId, String newUsername, String newPassword) throws Exception {
		PreparedStatement pst = con.prepareStatement("UPDATE users SET username = ?, password = ? WHERE id = ?");
		pst.setString(1, newUsername);
		pst.setString(2, newPassword);
		pst.setInt(3, userId);
		return pst.executeUpdate() > 0;
	}

	// Update user profile with admin privilege.
	public boolean updateUserAsAdmin(int id, String username, String password, String role) throws Exception {
		String sql;
		PreparedStatement pst;
		if (password != null && !password.isEmpty()) {
			sql = "UPDATE users SET username=?, password=?, role=? WHERE id=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, role);
			pst.setInt(4, id);
		} else {
			sql = "UPDATE users SET username=?, role=? WHERE id=?";
			pst = con.prepareStatement(sql);
			pst.setString(1, username);
			pst.setString(2, role);
			pst.setInt(3, id);
		}
		return pst.executeUpdate() > 0;
	}

	// Delete user
	public boolean deleteUser(int id) throws Exception {
		PreparedStatement pst = con.prepareStatement("DELETE FROM users WHERE id=?");
		pst.setInt(1, id);
		return pst.executeUpdate() > 0;
	}

	// Get user score with total.
	public ResultSet getUserScoresWithTotal(int studentId) throws Exception {
		PreparedStatement pst = con
				.prepareStatement("SELECT score, total_questions FROM scores WHERE student_id = ? ORDER BY timestamp");
		pst.setInt(1, studentId);
		return pst.executeQuery();
	}

	// Get username by id.
	public String getUsernameById(int userId) throws Exception {
		PreparedStatement ps = con.prepareStatement("SELECT username FROM users WHERE id = ?");
		ps.setInt(1, userId);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getString("username");
		}
		return null;
	}
}
