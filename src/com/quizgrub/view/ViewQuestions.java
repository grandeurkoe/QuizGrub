package com.quizgrub.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.ResultSet;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;

import com.quizgrub.dao.DBManager;

public class ViewQuestions extends JFrame {
	private JTable questionTable;
	private JButton refreshBtn, cancelBtn;

	public ViewQuestions() {
		setTitle("QuizGrub - View Questions");
		setSize(1400, 900);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		Font labelFont = new Font("Arial", Font.BOLD, 14);
		Font fieldFont = new Font("Arial", Font.PLAIN, 14);

		refreshBtn = customButton("Refresh", fieldFont, new Color(60, 179, 113), new Color(34, 139, 34));
		cancelBtn = customButton("Cancel", fieldFont, new Color(220, 20, 60), new Color(139, 0, 0));

		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 4;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 10, 0);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		buttonPanel.add(cancelBtn);
		buttonPanel.add(refreshBtn);
		buttonPanel.setBackground(Color.WHITE);
		add(buttonPanel, gbc);

		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new GridBagLayout());

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;

		questionTable = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		JScrollPane questionScroll = new JScrollPane(questionTable);

		questionScroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
				"Questions", TitledBorder.LEFT, TitledBorder.TOP, new Font("Arial", Font.BOLD, 15)));

		styleTable(questionTable);

		centralPanel.add(questionScroll, gbc);

		centralPanel.setBackground(Color.WHITE);

		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 4;
		gbc.insets = new Insets(20, 0, 0, 0);
		add(centralPanel, gbc);

		refreshBtn.addActionListener(e -> loadData());
		cancelBtn.addActionListener(e -> dispose());

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

	private void loadData() {
		try {
			DBManager db = new DBManager();

			ResultSet questionRs = db.getQuestions();
			DefaultTableModel questionModel = new DefaultTableModel(new String[] { "ID", "Question", "Option A",
					"Option B", "Option C", "Option D", "Correct Option", "Category", "Difficulty" }, 0);
			while (questionRs.next()) {
				questionModel.addRow(new Object[] { questionRs.getInt("id"), questionRs.getString("question_text"),
						questionRs.getString("option_a"), questionRs.getString("option_b"),
						questionRs.getString("option_c"), questionRs.getString("option_d"),
						questionRs.getString("correct_option"), questionRs.getString("category"),
						questionRs.getString("difficulty"), });
			}
			questionTable.setModel(questionModel);
			questionTable.getColumnModel().getColumn(1).setPreferredWidth(400);
			questionTable.getColumnModel().getColumn(2).setPreferredWidth(150);
			questionTable.getColumnModel().getColumn(3).setPreferredWidth(150);
			questionTable.getColumnModel().getColumn(4).setPreferredWidth(150);
			questionTable.getColumnModel().getColumn(5).setPreferredWidth(150);
			questionTable.getColumnModel().getColumn(6).setPreferredWidth(150);

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
}
