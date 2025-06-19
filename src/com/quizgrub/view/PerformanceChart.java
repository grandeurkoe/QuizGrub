package com.quizgrub.view;

import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.quizgrub.dao.DBManager;

public class PerformanceChart extends JFrame {
	private int studentId;
	private String studentName;

	public PerformanceChart(int studentId, String studentName) {
		this.studentId = studentId;
		this.studentName = studentName;

		setTitle("QuizGrub - " + studentName + "'s Performance Chart");
		setSize(800, 600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		DefaultCategoryDataset dataset = createDataset();
		JFreeChart linechart = ChartFactory.createLineChart("Your Performance Over Time", "Attempt", "Score (%)",
				dataset);

		ChartPanel charPanel = new ChartPanel(linechart);
		setContentPane(charPanel);
	}

	private DefaultCategoryDataset createDataset() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		try {
			DBManager db = new DBManager();
			ResultSet rs = db.getUserScoresWithTotal(studentId);
			int attempt = 1;

			while (rs.next()) {
				int score = rs.getInt("score");
				int total = rs.getInt("total_questions");
				double percent = (score * 100.0) / total;
				dataset.addValue(percent, "Score", "Attempt " + attempt++);
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Failed to load chart data: " + e.getMessage());
		}

		return dataset;
	}
}
