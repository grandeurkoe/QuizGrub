package com.quizgrub.model;

public class Question {
	private int id;
	private String questionText;
	private String optionA, optionB, optionC, optionD;
	private String correctOption;

	public Question(int id, String questionText, String optionA, String optionB, String optionC, String optionD,
			String correctOption) {
		super();
		this.id = id;
		this.questionText = questionText;
		this.optionA = optionA;
		this.optionB = optionB;
		this.optionC = optionC;
		this.optionD = optionD;
		this.correctOption = correctOption;
	}

	public int getId() {
		return id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public String getOptionA() {
		return optionA;
	}

	public String getOptionB() {
		return optionB;
	}

	public String getOptionC() {
		return optionC;
	}

	public String getOptionD() {
		return optionD;
	}

	public String getCorrectOption() {
		return correctOption;
	}

}
