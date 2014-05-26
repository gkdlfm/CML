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

	//ȸ������
	public void joinUser(String userId) {
		String msg = "#join" + userId;
		client.handleMessage(msg);
	}
	
	//����
	public void quit(){
		String msg = "#quit";
		client.handleMessage(msg);
	}
	
	//���� �޴� ����
	public void enterMenu(){
		String msg = "���Ӹ޴�����";
		client.handleMessage(msg);
	}
	
	//������(type�� �°� �����ϸ�ȴ�)
	public void getRoomList(){
		String msg = "������ ��";
		client.handleMessage(msg);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}
}
