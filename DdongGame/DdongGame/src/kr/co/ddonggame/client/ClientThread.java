package kr.co.ddonggame.client;

public class ClientThread extends Thread{
	private static ClientThread clientThread;
	public Client client;
	private ClientThread(){
		
	}
	public static ClientThread getInstance(){
		if(clientThread == null)
			return clientThread = new ClientThread();
		else
			return clientThread;
	}
	public void run(){
		client = new Client();
	}
}
