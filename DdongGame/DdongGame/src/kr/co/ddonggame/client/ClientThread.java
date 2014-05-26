package kr.co.ddonggame.client;

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
	public void joinUser(String userId) {
		String msg = "#join" + userId;
		client.handleMessage(msg);
	}
	
	//종료
	public void quit(){
		String msg = "#quit";
		client.handleMessage(msg);
	}
	
	//게임 메뉴 진입
	public void enterMenu(){
		String msg = "게임메뉴진입";
		client.handleMessage(msg);
	}
	
	//방정보(type은 맞게 변경하면된다)
	public void getRoomList(){
		String msg = "방정보 줘";
		client.handleMessage(msg);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
