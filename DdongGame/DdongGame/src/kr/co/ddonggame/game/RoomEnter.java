package kr.co.ddonggame.game;

import java.util.StringTokenizer;

import kr.co.ddonggame.GameRoom;
import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.ddonggame.R;

public class RoomEnter extends ActionBarActivity {
	private ClientThread clientThread;
	private UserInformation userInformation;
	private TextView[] roomEntry = new TextView[12];
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_roomenter);
		clientThread = ClientThread.getInstance();
		userInformation = UserInformation.getInstance();
		for (int i = 1; i <= 12; i++) {
			int temp = getResources().getIdentifier("roomEntry" + i, "id",
					"com.example.ddonggame");
			roomEntry[i - 1] = (TextView) findViewById(temp);
		}
		clientThread.getClient().setRoomEnter(this);
		clientThread.getRoomEntry();
		
		// 닉네임을 보내면 방정보를 얻어온다. (현재 방에 참여하고 있는 인원)
	}

	public void roomEntrySetting(String entry) {
		final StringTokenizer st = new StringTokenizer(entry, "_");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						String tmp = st.nextToken();
						for (int i = 1; i <= 12; i++) {
							if(!st.hasMoreTokens()){
								roomEntry[i-1].setText("watting...");
							}
							else{
								String temp = st.nextToken();
								roomEntry[i - 1].setText(temp);
							}
						}
					}
				});
			}
		}).start();

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
