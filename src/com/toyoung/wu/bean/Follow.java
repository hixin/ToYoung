package com.toyoung.wu.bean;

public class Follow extends Entity{
	int sex;
	String username;
	String nickname;
	String location;
	String person_signa;

	public Follow(int id, int sex, String username, String nickname, String location,
			String person_signa) {
		this.id = id;
		this.sex = sex;
		this.username = username;
		this.nickname = nickname;
		this.location = location;
		this.person_signa = person_signa;
	}
	
	public int getSex() {
		return sex;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getPerson_signa() {
		return person_signa;
	}
	public void setPerson_signa(String person_signa) {
		this.person_signa = person_signa;
	}

	@Override
	public String toString() {
		return "Follow [sex=" + sex + ", username=" + username + ", nickname="
				+ nickname + ", location=" + location + ", person_signa="
				+ person_signa + "]";
	}	
}
