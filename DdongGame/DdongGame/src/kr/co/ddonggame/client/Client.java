package kr.co.ddonggame.client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import kr.co.ddonggame.GameRoom;
import kr.co.ddonggame.MainActivity;
import kr.co.ddonggame.game.RoomEnter;
import android.util.Log;

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
	private RoomEnter roomEnter;
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
		Log.i("Handlemessage From Server : ", messageFromServer);
		

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
		}else if(message.matches(".*#makeroom.*")){
			StringTokenizer st = new StringTokenizer(message, "_");
			String temp = st.nextToken();
			temp = st.nextToken();
			userInformation.setRoomNumber(Integer.parseInt(temp));
			gameRoom.roomEnter();
		}
		else if(message.equals("#enter_ok")){
			gameRoom.roomEnter();
		}else if(message.equals("#enter_no")){
			gameRoom.roomEnterError();
		}else if(message.matches(".*#entryroom")){
			roomEnter.roomEntrySetting(message);
		}
		else if (message.matches(".*#entryroom.*")) {
			Log.i("test", "enter");
			if (message.equals("#enter_no")) {

			} else {
				roomEnter.roomEntrySetting(message);
			}
		}
	}

	public void setGameRoom(GameRoom gameRoom) {
		this.gameRoom = gameRoom;
	}

	public void setMainActivity(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}
	public void setRoomEnter(RoomEnter roomEnter){
		this.roomEnter = roomEnter;
	}
}