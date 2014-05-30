package kr.co.ddonggame;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.game.RoomEnter;
import android.R.integer;
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

import com.example.ddonggame.R;

public class GameRoom extends ActionBarActivity implements OnClickListener {
	private Button[] btnRoom;
	private TextView[] roomTextView;
	boolean[] roomOpenAndClose;
	private ClientThread clientThread;
	private Button btnLeft;
	private Button btnRight;
	private Button btnRoomCreate;
	private Button btnRoomRefresh;
	private int roomList = 1;
	private int roomEnterNumber=0;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_gameroom);
		btnRoom = new Button[6];
		roomTextView = new TextView[6];
		roomOpenAndClose = new boolean[6];
		btnLeft = (Button)findViewById(R.id.btnRoomLeft);
		btnLeft.setOnClickListener(this);
		btnRight = (Button)findViewById(R.id.btnRoomRight);
		btnRight.setOnClickListener(this);
		btnRoomCreate = (Button)findViewById(R.id.btnRoomCreate);
		btnRoomCreate.setOnClickListener(this);
		btnRoomRefresh = (Button)findViewById(R.id.btnRoomRefresh);
		btnRoomRefresh.setOnClickListener(this);
		for (int i = 1; i <= 6; i++) {
			int temp = getResources().getIdentifier("btnRoom" + i + "Enter",
					"id", "com.example.ddonggame");
			btnRoom[i - 1] = (Button) findViewById(temp);
			btnRoom[i - 1].setOnClickListener(this);
			int temp2 = getResources().getIdentifier("room"+i+"TextView", "id", "com.example.ddonggame");
			roomTextView[i-1] = (TextView)findViewById(temp2);
		}

		clientThread = ClientThread.getInstance();
		clientThread.getRoomList(roomList);
		clientThread.getClient().setGameRoom(this);
		//GameRoom Activity요청시 서버에서 방목록의 정보를 가져와서 room1TextView의 text (방 번호)를 바꾸어준다.
	}

	public void changeRoomInformation(int gameRoomNumber, String roomOpenOrClose){
		Log.i("gameRoomNumber", Integer.toString(gameRoomNumber));
		Log.i("roomopenorclose",roomOpenOrClose);
		for(int i=1; i<=6; i++){
			int temp = (gameRoomNumber-1)*6+i;
			//Log.i("gameRoom num : ", Integer.toString(temp));
			roomTextView[i-1].setText(Integer.toString(temp));
			Log.i("changeRoom num : ", Integer.toString(temp));
			char a = roomOpenOrClose.charAt(i-1);
			if(a=='0'){
				btnRoom[i-1].setEnabled(false);
			}
			else{
				btnRoom[i-1].setEnabled(true);
			}
		}
		
	}
	
	public void onClick(View v) {
		int id = v.getId();
		int roomNumber=0;
		switch (id) {
		case R.id.btnRoom1Enter:
			roomNumber = Integer.parseInt(roomTextView[0].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			break;
		case R.id.btnRoom2Enter:
			roomNumber = Integer.parseInt(roomTextView[1].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			break;
		case R.id.btnRoom3Enter:
			roomNumber = Integer.parseInt(roomTextView[2].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			break;
		case R.id.btnRoom4Enter:
			roomNumber = Integer.parseInt(roomTextView[3].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			break;
		case R.id.btnRoom5Enter:
			roomNumber = Integer.parseInt(roomTextView[4].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			break;
		case R.id.btnRoom6Enter:
			roomNumber = Integer.parseInt(roomTextView[5].getText().toString());
			clientThread.getRoomEnter(roomNumber);
			break;
		case R.id.btnRoomLeft:
			if(roomList>1){
				roomList--;
				clientThread.getRoomList(roomList);
			}
			break;
		case R.id.btnRoomRight:
			roomList++;
			clientThread.getRoomList(roomList);
			break;
		case R.id.btnRoomRefresh:
			break;
		default:
			break;
		}
	}
	public void roomEnter(int roomEnterNumber){
		this.roomEnterNumber = roomEnterNumber;
		startActivity(new Intent(this, RoomEnter.class));
	}
	
}
