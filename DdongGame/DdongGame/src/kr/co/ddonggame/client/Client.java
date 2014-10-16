package kr.co.ddonggame.client;

import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

import kr.co.ddonggame.GameRoom;
import kr.co.ddonggame.MainActivity;
import kr.co.ddonggame.game.GameActivity;
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
	private GameActivity gameActivity;
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
		Log.i("Client display : ", messageFromServer);
		

		if (message.equals("!join_ok")) {
			mainActivity.enterMainMenu();
		}else if(message.equals("!join_no")){
			mainActivity.nickNameError();
		}
		else if (message.matches(".*!joinCheck_ok.*")) {
			StringTokenizer msg = new StringTokenizer(message, "_");
			String temp = msg.nextToken();
			temp = msg.nextToken();
			temp = msg.nextToken();
			userInformation.setNickName(temp);
			mainActivity.enterMainMenu();
		} else if (message.matches(".*@setRoomList.*")) {
			StringTokenizer msg = new StringTokenizer(message, "_");
			int gameRoomNumber = 0;
			String roomOpenOrClose = null;
			String temp = msg.nextToken();
			temp = msg.nextToken();
			gameRoomNumber = Integer.parseInt(temp);
			temp = msg.nextToken();
			roomOpenOrClose = temp;
			gameRoom.setRoomList(gameRoomNumber, roomOpenOrClose);
		}else if(message.matches(".*@makeRoom.*")){
			StringTokenizer st = new StringTokenizer(message, "_");
			String temp = st.nextToken();
			temp = st.nextToken();
			Log.i("make roomNumber", temp);
			userInformation.setRoomNumber(Integer.parseInt(temp));
			gameRoom.makeRoom();
		}
		else if(message.equals("@enter_ok")){
			gameRoom.roomEnter();
		}else if(message.equals("@enter_no")){
			gameRoom.roomEnterError();
		}else if(message.matches(".*#setRoomEntry.*")){
			roomEnter.setRoomEntry(message);
		}else if(message.equals("#gameStart")){
			roomEnter.gameStart();
		}else if(message.equals("#roomExit")){
			//roomEnter.onDestroy();
		}else if(message.matches(".*$init.*")){
			gameActivity.init(message);
		}else if(message.matches(".*$nextTurn.*")){
			gameActivity.chageCard(message);
		}else if(message.matches(".*$gameAbnormalEnd.*")){
			String temp = message.split("_")[1];
			if(temp.equals(userInformation.getNickName())){
				
			}
			else
				gameActivity.gameEnd(message);
		}else if(message.matches(".*$gameEnd.*")){
			gameActivity.gameEnd(message);
		}else if(message.matches(".*%setType.*")){
			userInformation.setType(message.split("_")[1]);
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

	public GameActivity getGameActivity() {
		return gameActivity;
	}

	public void setGameActivity(GameActivity gameActivity) {
		this.gameActivity = gameActivity;
	}
}