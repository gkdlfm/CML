package kr.co.ddonggame.client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import org.xml.sax.Parser;

import android.util.Log;
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
	private UserInformation userInformation;
	public Client() {
		client = new ChatClient(host, port, login, this);
		userInformation = UserInformation.getInstance();
	}

	public void handleMessage(String msg) {
		client.handleMessageFromClientUI(msg);
	}

	@Override
	public void display(String message) {
		messageFromServer = message;
		Log.i("Handlemessage From Server : ", message);

		if (message.equals("#join_ok")) {
			mainActivity.enterMainMenu();
		}else if(message.equals("#join_no")){
			mainActivity.nickNameError();
		}
		else if (message.matches(".*#joincheck_ok.*")) {
			StringTokenizer msg = new StringTokenizer(message, "_");
			String temp = msg.nextToken();
			temp = msg.nextToken();
			temp = msg.nextToken();
			userInformation.setNickName(temp);
			mainActivity.enterMainMenu();
		} else if (message.matches(".*#room_information.*")) {
			StringTokenizer msg = new StringTokenizer(message, "_");
			int gameRoomNumber = 0;
			String roomOpenOrClose = null;
			String temp = msg.nextToken();
			temp = msg.nextToken();
			temp = msg.nextToken();
			gameRoomNumber = Integer.parseInt(temp);
			temp = msg.nextToken();
			roomOpenOrClose = temp;
			gameRoom.changeRoomInformation(gameRoomNumber, roomOpenOrClose);
		} else if (message.matches(".*#enter.*")) {
			Log.i("test", "enter");
			if (message.equals("#enter_no")) {

			} else {
				StringTokenizer msg = new StringTokenizer(message, "_");
				while (msg.hasMoreTokens()) {
					String temp = msg.nextToken();
					if (temp.matches("0-9")) {
						int roomEnterNumber = Integer.parseInt(temp);
						gameRoom.roomEnter(roomEnterNumber);
					}
				}
			}
		}
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

}