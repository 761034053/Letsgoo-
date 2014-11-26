package com.lingdong.letsgoo.Bean;


public class DynamicMessage{
	private Integer activity_id;
	private Integer dynamic_id;
	private String pictureurl;
	private String nickname;
	private String actheme;
	private String updated_at;
	private Integer operate_type;
	

	public String getActheme() {
		return actheme;
	}
	public void setActheme(String actheme) {
		this.actheme = actheme;
	}
	
	public String getPictureurl() {
		return pictureurl;
	}
	public void setPictureurl(String pictureurl) {
		this.pictureurl = pictureurl;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public Integer getOperate_type() {
		return operate_type;
	}
	public void setOperate_type(Integer operate_type) {
		this.operate_type = operate_type;
	}
	public Integer getActivity_id() {
		return activity_id;
	}
	public void setActivity_id(Integer activity_id) {
		this.activity_id = activity_id;
	}
	public Integer getDynamic_id() {
		return dynamic_id;
	}
	public void setDynamic_id(Integer dynamic_id) {
		this.dynamic_id = dynamic_id;
	}
	@Override
	public String toString() {
		return "DynamicMessage [activity_id=" + activity_id + ", dynamic_id="
				+ dynamic_id + ", pictureurl=" + pictureurl + ", nickname="
				+ nickname + ", actheme=" + actheme + ", updated_at="
				+ updated_at + ", operate_type=" + operate_type + "]";
	}
	

	
	
	
}
