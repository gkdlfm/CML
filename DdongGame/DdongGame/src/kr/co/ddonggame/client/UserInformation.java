package kr.co.ddonggame.client;

public class UserInformation {
	private static UserInformation userInformation;
	private String nickName;
	private String phoneNumber;
	private UserInformation(){
		
	}
	
	
	public static UserInformation getInstance(){
		if(userInformation == null)
			return userInformation = new UserInformation();
		else
			return userInformation;
	}


	public String getNickName() {
		return nickName;
	}


	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
}
