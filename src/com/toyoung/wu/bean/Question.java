package com.toyoung.wu.bean;

public class Question extends Entity{

	int lable;
	String content;	
		
	public Question(int id, int lable, String content) {	
		this.id = id;
		this.lable = lable;
		this.content = content;	
	}
	
	
	public int getLable() {
		return lable;
	}
	public void setLable(int lable) {
		this.lable = lable;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


	@Override
	public String toString() {
		return "Question [lable=" + lable + ", content=" + content + ", id="
				+ id + "]";
	}
	
}
