package kr.co.ddonggame.client;

import java.util.StringTokenizer;

import kr.co.ddonggame.game.GameActivity;

import android.graphics.BitmapFactory;
import android.util.Log;

public class UserInformation {
	private static UserInformation userInformation;
	private String nickName;
	private int roomNumber;
	private String type = "a";
	private String phoneNumber;
	private String macAddress;
	private int hwatooDeckInt[] = new int[4];
	private boolean gaming = false;
	
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


	public int getRoomNumber() {
		return roomNumber;
	}


	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getMacAddress() {
		return macAddress;
	}


	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}


	public int[] getHwatooDeckInt() {
		return hwatooDeckInt;
	}


	public void setHwatooDeckInt(String msg) {
		Log.i("userInformation setHwatooDecint : ", msg);
		StringTokenizer st = new StringTokenizer(msg, "_");
		st.nextToken();
		for(int i=0; i<4; i++){
			int deck = Integer.parseInt(st.nextToken());
			hwatooDeckInt[i] = deck;
		}
	}
	
	public void setHwatooDeckInt(int index) {
		hwatooDeckInt[index]=0;
	}
		
	public void changeHwatooDeckInt(String msg){
		Log.i("userInformation changeCard : ", msg);
		StringTokenizer st = new StringTokenizer(msg, "_");
		st.nextToken();
		for(int i=0; i<4; i++){
			if(hwatooDeckInt[i] == 0){
				Log.i("abc", "abc");
				hwatooDeckInt[i] = Integer.parseInt(st.nextToken());
			}
		}
	}


	public boolean isGaming() {
		return gaming;
	}


	public void setGaming(boolean gaming) {
		this.gaming = gaming;
	}

}
