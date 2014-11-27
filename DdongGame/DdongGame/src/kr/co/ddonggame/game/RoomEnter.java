package kr.co.ddonggame.game;

import java.util.StringTokenizer;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.ddonggame.R;

public class RoomEnter extends ActionBarActivity implements OnClickListener {
	private ClientThread clientThread = ClientThread.getInstance();
	private UserInformation userInformation;
	private TextView[] roomEntry = new TextView[12];
	private Button[] btnSwap = new Button[12];
	private Button btnReadyOrStart;
	private boolean gaming = false;
	private boolean dialogCheck = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_roomenter);

		userInformation = UserInformation.getInstance();
		btnReadyOrStart = (Button) findViewById(R.id.btnReadyOrStart);
		btnReadyOrStart.setOnClickListener(this);
		for (int i = 1; i <= 12; i++) {
			int temp = getResources().getIdentifier("roomEntry" + i, "id",
					"com.example.ddonggame");
			roomEntry[i - 1] = (TextView) findViewById(temp);
			int temp2 = getResources().getIdentifier("btnSwap" + i, "id",
					"com.example.ddonggame");
			btnSwap[i - 1] = (Button) findViewById(temp2);
			btnSwap[i - 1].setOnClickListener(this);
		}
		clientThread.getClient().setRoomEnter(this);
		clientThread.getRoomEntry();
		btnReadyOrStart.setVisibility(View.INVISIBLE);
		// 닉네임을 보내면 방정보를 얻어온다. (현재 방에 참여하고 있는 인원)
	}

	public void gameStart() {
		startActivity(new Intent(this, GameActivity.class));
	}

	public void onClick(View v) {
		int getid = v.getId();
		switch (getid) {
		case R.id.btnReadyOrStart:
			userInformation.setGaming(true);
			String temp = btnReadyOrStart.getText().toString();
			clientThread.readyOrStart(temp);
			break;
		default:
			break;
		}
		for (int i = 1; i <= 12; i++) {
			int temp = getResources().getIdentifier("btnSwap" + i, "id",
					"com.example.ddonggame");
			if (getid == temp) {
				clientThread.locationChange(i);
			}
		}

	}

	protected void onResume() {
		super.onResume();
		userInformation.setGaming(false);
		if (dialogCheck == false)
			clientThread.getRoomEntry();
		if (dialogCheck)
			dialogCheck = true;
	}

	public Handler uiChangeHandler = new Handler() {
		public void handleMessage(Message msg) {
			/*
			 * final StringTokenizer st = new StringTokenizer(msg.toString(),
			 * "_"); String tmp = st.nextToken(); for (int i = 1; i <= 12; i++)
			 * { if (!st.hasMoreTokens()) { roomEntry[i -
			 * 1].setText("watting..."); } else { String temp = st.nextToken();
			 * if(i==1 && temp.equals(userInformation.getNickName())){
			 * btnReadyOrStart.setVisibility(View.VISIBLE);
			 * btnReadyOrStart.setText("Start"); } else if(i==1){
			 * btnReadyOrStart.setVisibility(View.INVISIBLE); } roomEntry[i -
			 * 1].setText(temp); } }
			 */

		}
	};

	public void locationChangeDialog(final String nickName) {
		Log.i("RoomEnter : ", nickName);
		new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialogCheck = true;
						AlertDialog dialog = createDialogBox(nickName);
						dialog.show();
					}
				});
			}
		}).start();
	}

	private AlertDialog createDialogBox(final String nickName) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("자리바꾸기");
		builder.setMessage(nickName + "님과 자리를 바꾸시겠습니까?");
		builder.setPositiveButton("네", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				String msg = "OK_" + nickName;
				clientThread.locationChange(msg);
			}
		});
		builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				clientThread.locationChange("NO");
			}
		});
		AlertDialog dialog = builder.create();
		return dialog;
	}

	public void setRoomEntry(String entry) {
		Log.i("roomentry : ", entry);
		final StringTokenizer st = new StringTokenizer(entry, "_");

		new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						String tmp = st.nextToken();
						for (int i = 1; i <= 12; i++) {
							if (!st.hasMoreTokens()) {
								roomEntry[i - 1].setText("watting...");
								btnSwap[i - 1].setVisibility(View.INVISIBLE);
							} else {
								String temp = st.nextToken();
								if (temp.equals(userInformation.getNickName())) {
									btnSwap[i - 1]
											.setVisibility(View.INVISIBLE);
								} else {
									btnSwap[i - 1].setVisibility(View.VISIBLE);
								}
								if (i == 1
										&& temp.equals(userInformation
												.getNickName())) {
									btnReadyOrStart.setVisibility(View.VISIBLE);
									btnReadyOrStart.setText("Start");
								} else if (i == 1) {
									btnReadyOrStart.setVisibility(View.INVISIBLE);
								}
								roomEntry[i - 1].setText(temp);
							}
						}
					}
				});
			}
		}).start();

	}

	public void roomExit() {
		Log.i("RoomEnter roomExit", "exit");
		// super.finish();
	}

	@Override
	public void onStop() {
		super.onStop();
		Log.i("RoomEnter", "onStop");
		if (userInformation.isGaming() == false)
			finish();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("RoomEnter", "onDestroy");
		clientThread.roomExit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		return super.onOptionsItemSelected(item);
	}

}
