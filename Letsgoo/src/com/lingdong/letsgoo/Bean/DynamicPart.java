package com.lingdong.letsgoo.Bean;

public class DynamicPart {
	private Integer type;
	private String nickname;
	private String pictureurl;
	private String school;
	private String gender;
	private String phone;
	public DynamicPart(Integer type, String nickname, String pictureurl,
			String school, String gender, String phone) {
		super();
		this.type = type;
		this.nickname = nickname;
		this.pictureurl = pictureurl;
		this.school = school;
		this.gender = gender;
		this.phone = phone;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getPictureurl() {
		return pictureurl;
	}
	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}
	public String getSchool() {
		return school;
	}
	public void setSchool(String school) {
		this.school = school;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}

}
