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

	//ȸ������
	public boolean joinUser(String userId, String phoneNumber) {
		String msg = "#join" + "_" + userId + "_" + phoneNumber;
		Log.i("test", "2");
		client.handleMessage(msg);
		//�ϴ� false
		return false;
	}
	
	//JoinȮ��
	public void joinCheck(String phoneNumber){
		String msg = "#joincheck_" + phoneNumber;
		Log.i("test", "3");
		client.handleMessage(msg);
	}
	
	//����
	public void quit(){
		String msg = "#quit";
		client.handleMessage(msg);
	}
	
	
	public int createRoom(int entryCount){
		String msg = "#makeroom_" + entryCount;
		
		
		return 0; // ������� ���ȣ return
	}
	
	//������(type�� �°� �����ϸ�ȴ�)
	public void getRoomList(int roomList){
		String msg = "#room_information_"+roomList;
		client.handleMessage(msg);
	}

	//������
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
