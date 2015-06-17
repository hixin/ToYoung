package com.tuyoung.vo;

public class UserVo {

	private String userID;
	private String userName;
	private String password;
	private String sex;//0 for man,1 for women
	private String address;
	private String email;
	private String nickName;
	private String schoolName;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "userID:"+this.getUserID()+" userName:"+this.getUserName()+" sex:"+this.getSex()+" address:"+this.getAddress()+" nickName:"+this.getNickName()+" schoolName:"+this.getSchoolName();
	}
}
