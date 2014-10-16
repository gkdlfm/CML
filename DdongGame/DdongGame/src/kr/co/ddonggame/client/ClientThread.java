package kr.co.ddonggame.client;

import android.util.Log;

public class ClientThread extends Thread {
	private static ClientThread clientThread;
	private Client client;
	private UserInformation userInformation = UserInformation.getInstance();;
	private ClientThread() {
		
	}

	public static ClientThread getInstance() {
		if (clientThread == null){
			return clientThread = new ClientThread();
		}
		else
			return clientThread;
	}

	public void run() {
		client = new Client();
	}

	//회원가입
	public boolean joinUser(String userId, String macAddress) {
		String msg = "!joinUser" + "_" + userId + "_" + macAddress;
		Log.i("ClientThread joinUser : ", msg);
		client.handleMessage(msg);
		//일단 false
		return false;
	}
	
	//Join확인
	public void joinCheck(String macAddress){
		String msg = "!joinCheck_" + macAddress;
		Log.i("ClientThread joinCheck : ", msg);
		client.handleMessage(msg);
	}
	
	//종료
	public void quit(){
		String msg = "#quit_" + userInformation.getNickName();
		client.handleMessage(msg);
	}
	
	
	public int makeRoom(int entryCount){
		String msg = "@makeRoom_" + entryCount + "_" + userInformation.getNickName();
		Log.i("ClientThread makeRoom : ", msg);
		client.handleMessage(msg);
		
		return 0; // 만들어진 방번호 return
	}
	
	//방정보(type은 맞게 변경하면된다)
	public void getRoomList(int roomList){
		String msg = "@getRoomList_"+roomList;
		Log.i("ClientThread getRoomList : ", msg);
		client.handleMessage(msg);
	}
	
	//방입장
	public void getRoomEnter(int roomNumber){
		String msg = "@getRoomEnter_"+roomNumber+"_"+userInformation.getNickName();
		Log.i("ClientThread getRoomEnter : ", msg);
		client.handleMessage(msg);
	}
	
	
	public void getRoomEntry(){
		String msg = "#getRoomEntry_"+userInformation.getRoomNumber() +"_"+userInformation.getNickName();
		Log.i("ClientThread getRoomEntry : ", msg);
		client.handleMessage(msg);
	}
	
	public void roomExit(){
		String msg = "#roomExit_"+userInformation.getRoomNumber() +"_"+userInformation.getNickName();
		Log.i("ClientThread roomExit : ", msg);
		client.handleMessage(msg);
	}
	
	public void readyOrStart(String command){
		String msg = "#game"+command+"_"+userInformation.getRoomNumber()+"_"+userInformation.getNickName();
		Log.i("ClientThread readyOrStart : ", msg);
		client.handleMessage(msg);
	}
	
	public void getInit(){
		String msg = "$getInit_"+userInformation.getRoomNumber();
		Log.i("ClientThread getInit : ", msg);
		client.handleMessage(msg);
	}
	
	public void nextTurn(String msg){
		Log.i("ClientThread nextTurn : ", msg);
		client.handleMessage(msg);
	}
	
	//public void game
	
	public void gameEnd(){
		String msg = "$gameEnd_"+userInformation.getRoomNumber()+"_"+userInformation.getNickName();
		Log.i("ClientThread gameEnd : ", msg);
		client.handleMessage(msg);
	}
	
	public void gameAbnormalEnd(){
		String msg = "$gameAbnormalEnd_"+userInformation.getRoomNumber() + "_" + userInformation.getNickName();
		Log.i("ClientThread gameAbnormalEnd : ", msg);
		client.handleMessage(msg);
	}
	
	public void setType(String type){
		String msg = "%setType_"+userInformation.getNickName()+"_"+type;
		Log.i("ClientThread setType : ", msg);
		client.handleMessage(msg);
	}
	
	public void getType(){
		String msg = "%getType_"+userInformation.getNickName();
		Log.i("ClientThread getType : ", msg);
		client.handleMessage(msg);
	}
	
	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
