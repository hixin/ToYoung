package com.toyoung.wu.bean;

public class Answer extends Entity{

	int quesID;
	int lable;
	String ques_content;
	String anws_content;
	
	
	
	public Answer(int id, int quesId, int lable, String ques_content, String anws_content) {
		this.id = id;
		this.quesID = quesId;
		this.lable = lable;
		this.ques_content = ques_content;
		this.anws_content = anws_content;
	}
	
	public int getLable() {
		return lable;
	}
	public void setLable(int lable) {
		this.lable = lable;
	}
	public String getQues_content() {
		return ques_content;
	}
	public void setQues_content(String ques_content) {
		this.ques_content = ques_content;
	}
	public String getAnws_content() {
		return anws_content;
	}
	public void setAnws_content(String anws_content) {
		this.anws_content = anws_content;
	}

	public int getQuesID() {
		return quesID;
	}

	public void setQuesID(int quesID) {
		this.quesID = quesID;
	}

	@Override
	public String toString() {
		return "Answer [lable=" + lable + ", ques_content=" + ques_content
				+ ", anws_content=" + anws_content + "]";
	}

	
}
