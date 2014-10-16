package kr.co.ddonggame.game;

import java.util.StringTokenizer;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import android.content.Intent;
import android.os.Bundle;
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

public class RoomEnter extends ActionBarActivity implements OnClickListener{
	private ClientThread clientThread = ClientThread.getInstance();
	private UserInformation userInformation;
	private TextView[] roomEntry = new TextView[12];
	private Button btnReadyOrStart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_roomenter);
		
		userInformation = UserInformation.getInstance();
		btnReadyOrStart = (Button)findViewById(R.id.btnReadyOrStart);
		btnReadyOrStart.setOnClickListener(this);
		for (int i = 1; i <= 12; i++) {
			int temp = getResources().getIdentifier("roomEntry" + i, "id",
					"com.example.ddonggame");
			roomEntry[i - 1] = (TextView) findViewById(temp);
		}
		clientThread.getClient().setRoomEnter(this);
		clientThread.getRoomEntry();
		btnReadyOrStart.setVisibility(View.INVISIBLE);
		// 닉네임을 보내면 방정보를 얻어온다. (현재 방에 참여하고 있는 인원)
	}
	
	public void gameStart(){
		startActivity(new Intent(this, GameActivity.class));
	}
	
	public void onClick(View v){
		int getid = v.getId();
		switch (getid) {
		case R.id.btnReadyOrStart:
			String temp = btnReadyOrStart.getText().toString();
			clientThread.readyOrStart(temp);
			break;
		default:
			break;
		}
		
	}
	
	public void setRoomEntry(String entry) {
		final StringTokenizer st = new StringTokenizer(entry, "_");
		Log.i("roomentry : ", entry);
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
							} else {
								String temp = st.nextToken();
								if(i==1 && temp.equals(userInformation.getNickName())){
									btnReadyOrStart.setVisibility(View.VISIBLE);
									btnReadyOrStart.setText("Start");
								}
								else if(i==1){
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
	
	public void roomExit(){
		Log.i("RoomEnter roomExit", "exit");
		//super.finish();
	}
	@Override
	public void onDestroy() {
		clientThread.roomExit();
		super.onDestroy();
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
