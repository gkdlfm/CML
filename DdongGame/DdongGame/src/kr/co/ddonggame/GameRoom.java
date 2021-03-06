package kr.co.ddonggame;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import kr.co.ddonggame.custom.CustomDialog;
import kr.co.ddonggame.game.RoomEnter;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddonggame.R;

public class GameRoom extends ActionBarActivity implements OnClickListener {
	private Button[] btnRoom;
	private TextView[] roomTextView;
	boolean[] roomOpenAndClose;

	private ClientThread clientThread;

	private Button btnRoomCreate;
	private Button btnRoomRefresh;
	private Button btnLeft;
	private Button btnRight;
	private TextView nickNameTextView;
	private int roomList = 1;
	private int roomNumber = 0;

	private UserInformation userInformation;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_gameroom);

		btnRoomCreate = (Button) findViewById(R.id.btnRoomCreate);
		btnRoomCreate.setOnClickListener(this);
		btnRoomRefresh = (Button) findViewById(R.id.btnRoomRefresh);
		btnRoomRefresh.setOnClickListener(this);

		btnRoom = new Button[6];
		roomTextView = new TextView[6];
		roomOpenAndClose = new boolean[6];
		btnLeft = (Button) findViewById(R.id.btnRoomLeft);
		btnLeft.setOnClickListener(this);
		btnRight = (Button) findViewById(R.id.btnRoomRight);
		btnRight.setOnClickListener(this);

		btnRoomCreate = (Button) findViewById(R.id.btnRoomCreate);
		btnRoomCreate.setOnClickListener(this);
		btnRoomRefresh = (Button) findViewById(R.id.btnRoomRefresh);
		btnRoomRefresh.setOnClickListener(this);
		userInformation = UserInformation.getInstance();

		nickNameTextView = (TextView) findViewById(R.id.nickNameTextView);
		nickNameTextView.setText(userInformation.getNickName());

		for (int i = 1; i <= 6; i++) {
			int temp = getResources().getIdentifier("btnRoom" + i + "Enter",
					"id", "com.example.ddonggame");
			btnRoom[i - 1] = (Button) findViewById(temp);
			btnRoom[i - 1].setOnClickListener(this);
			int temp2 = getResources().getIdentifier("room" + i + "TextView",
					"id", "com.example.ddonggame");
			roomTextView[i - 1] = (TextView) findViewById(temp2);
		}

		clientThread = ClientThread.getInstance();
		clientThread.getRoomList(roomList);
		clientThread.getClient().setGameRoom(this);
		// GameRoom Activity요청시 서버에서 방목록의 정보를 가져와서 room1TextView의 text (방 번호)를
		// 바꾸어준다.
	}

	public void setRoomList(int gameRoomNumber, String roomOpenOrClose) {
		final int gameRoomNumbertemp = gameRoomNumber;
		final String roomOpenClosetemp = roomOpenOrClose;
		//Log.i("GameRoom : setRoomList", gameRoomNumber + " ^^ " +  roomOpenOrClose);
		new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						for (int i = 1; i <= 6; i++) {
							int temp = (gameRoomNumbertemp - 1) * 6 + i;
							roomTextView[i - 1].setText(Integer.toString(temp));
							char a = roomOpenClosetemp.charAt(i - 1);
							if (a == '0') {
								btnRoom[i - 1].setEnabled(false);
							} else {
								btnRoom[i - 1].setEnabled(true);
							}
						}
					}
				});
			}
		}).start();
		for (int i = 1; i <= 6; i++) {

		}

	}

	@Override
	public void onRestart(){
		Log.i("GameRoom onRestart", "roomList : " + Integer.toString(roomList));
		clientThread.getRoomList(roomList);
		super.onRestart();
	}
	
	public void onClick(View v) {
		int id = v.getId();
		switch (id) {
		case R.id.btnRoomCreate:
			clientThread.makeRoom(12);
			break;
		case R.id.btnRoomRefresh:
			clientThread.getRoomList(roomList);
			break;
		case R.id.btnRoom1Enter:
			roomNumber = Integer.parseInt(roomTextView[0].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			userInformation.setRoomNumber(roomNumber);
			break;
		case R.id.btnRoom2Enter:
			roomNumber = Integer.parseInt(roomTextView[1].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			userInformation.setRoomNumber(roomNumber);
			break;
		case R.id.btnRoom3Enter:
			roomNumber = Integer.parseInt(roomTextView[2].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			userInformation.setRoomNumber(roomNumber);
			break;
		case R.id.btnRoom4Enter:
			roomNumber = Integer.parseInt(roomTextView[3].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			userInformation.setRoomNumber(roomNumber);
			break;
		case R.id.btnRoom5Enter:
			roomNumber = Integer.parseInt(roomTextView[4].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			userInformation.setRoomNumber(roomNumber);
			break;
		case R.id.btnRoom6Enter:
			roomNumber = Integer.parseInt(roomTextView[5].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			userInformation.setRoomNumber(roomNumber);
			break;
		case R.id.btnRoomLeft:
			if (roomList > 1) {
				roomList--;
				clientThread.getRoomList(roomList);
			}
			break;
		case R.id.btnRoomRight:
			roomList++;
			clientThread.getRoomList(roomList);
			break;
		default:
			break;
		}
	}

	public void makeRoom() {
		try {
			startActivity(new Intent(this, RoomEnter.class));
		} catch (Exception e) {
			Log.i("GameRoom_makeRoom", e.toString());
		}
	}
	
	public void roomEnter(){
		try {
			startActivity(new Intent(this, RoomEnter.class));
		} catch (Exception e) {
			Log.i("GameRoom_roomEnter", e.toString());
		}
	}
	
	public void roomEnterError() {
		Toast toast = Toast.makeText(this, "방인원을 초과하였습니다.", Toast.LENGTH_SHORT);
		toast.show();
	}
}
