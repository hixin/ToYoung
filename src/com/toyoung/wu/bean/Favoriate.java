package com.toyoung.wu.bean;

public class Favoriate extends Entity{
	String title;
	int lable;
	String question_content;
	int quesID;
	
	public Favoriate(int id, String title, int lable, String question_content,
			int quesID) {
		this.id = id;
		this.title = title;
		this.lable = lable;
		this.question_content = question_content;
		this.quesID = quesID;
	}

	@Override
	public String toString() {
		return "Favoriate [title=" + title + ", lable=" + lable
				+ ", question_content=" + question_content + ", quesID="
				+ quesID + "]";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getLable() {
		return lable;
	}

	public void setLable(int lable) {
		this.lable = lable;
	}

	public String getQuestion_content() {
		return question_content;
	}

	public void setQuestion_content(String question_content) {
		this.question_content = question_content;
	}

	public int getQuesID() {
		return quesID;
	}

	public void setQuesID(int quesID) {
		this.quesID = quesID;
	}

}
