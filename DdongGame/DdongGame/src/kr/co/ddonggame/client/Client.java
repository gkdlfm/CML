package kr.co.ddonggame.client;

import java.io.ObjectOutputStream;
import java.net.Socket;

import kr.co.ddonggame.GameRoom;
import kr.co.ddonggame.MainActivity;

public class Client implements ChatIF {
	private String host = "203.249.22.116";
	private int port = 8087;
	private Socket clientSocket;
	private String login = "android";
	private ObjectOutputStream output;
	private ChatClient client;
	private String messageFromServer;
	private MainActivity mainActivity;
	private GameRoom gameRoom;
	
	
	public Client() {
		client = new ChatClient(host, port, login, this);
	}

	public void handleMessage(String msg) {
		client.handleMessageFromClientUI(msg);
	}

	@Override
	public void display(String message) {
		messageFromServer = message;
		if(message.equals("#join ok")){
			mainActivity.enterMainMenu();
		}
		else if(message.equals("#room1_open")){
			
		}
		
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
	
	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

}