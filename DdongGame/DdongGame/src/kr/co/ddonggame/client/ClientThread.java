package kr.co.ddonggame.client;

import android.util.Log;

public class ClientThread extends Thread {
	private static ClientThread clientThread;
	private Client client;

	private ClientThread() {
		
	}

	public static ClientThread getInstance() {
		if (clientThread == null)
			return clientThread = new ClientThread();
		else
			return clientThread;
	}

	public void run() {
		client = new Client();
	}

	//회원가입
	public boolean joinUser(String userId, String phoneNumber) {
		String msg = "#join" + "_" + userId + "_" + phoneNumber;
		Log.i("test", "2");
		client.handleMessage(msg);
		//일단 false
		return false;
	}
	
	//Join확인
	public void joinCheck(String phoneNumber){
		String msg = "#joincheck_" + phoneNumber;
		Log.i("test", "3");
		client.handleMessage(msg);
	}
	
	//종료
	public void quit(){
		String msg = "#quit";
		client.handleMessage(msg);
	}
	
	
	//방정보(type은 맞게 변경하면된다)
	public void getRoomList(int roomList){
		String msg = "#room_information_"+roomList;
		client.handleMessage(msg);
	}

	//방입장
	public void getRoomEnter(int roomNumber){
		String msg = "#room_enter_"+roomNumber;
		client.handleMessage(msg);
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
