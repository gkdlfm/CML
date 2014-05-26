package kr.co.ddonggame.client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import org.xml.sax.Parser;

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
		else if(message.matches("#room_information")){
			StringTokenizer msg = new StringTokenizer(message, "_");
			int gameRoomNumber=0;
			String roomOpenOrClose = null;
			while(msg.hasMoreTokens()){
				String temp = msg.nextToken();
				if(temp.matches("0-9")){
					gameRoomNumber = Integer.parseInt(temp);
					temp = msg.nextToken();
					roomOpenOrClose = temp;
				}
			}
			gameRoom.changeRoomInformation(gameRoomNumber, roomOpenOrClose);
		}
		
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}
	
	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

}