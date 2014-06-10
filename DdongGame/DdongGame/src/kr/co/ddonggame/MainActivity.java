package kr.co.ddonggame;

import java.util.Timer;
import java.util.TimerTask;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ddonggame.R;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private Button btnJoin;
	private EditText editID;
	private ClientThread clientThread;
	private String phoneNumber;
	private UserInformation userInformation;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		userInformation = UserInformation.getInstance();
		TimerTask mTask = new TimerTask(){
			public void run() {
				clientThread.getClient().setMainActivity(MainActivity.this);
				clientThread.joinCheck(phoneNumber);
				Log.i("test : phonenumber", phoneNumber);
			}
		};
		clientThread = ClientThread.getInstance();
		clientThread.start();
		
		TelephonyManager mTelephonyMgr = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
		phoneNumber = mTelephonyMgr.getLine1Number();
		userInformation.setPhoneNumber(phoneNumber);
		Timer timer = new Timer();
		timer.schedule(mTask, 2000);
		//타이머를 쓴이유는 getClient가 받아지지 않음. 클라이언트는 스레드로 돌려야하는데 스레드가 돌기전에 참조해서 nullpoint에러남.
		
		btnJoin = (Button) this.findViewById(R.id.btnJoin);
		btnJoin.setOnClickListener(this);
		editID = (EditText) this.findViewById(R.id.editID);
		
	}

	protected void onDestroy() {
		clientThread.quit();
	}
	
	public void onClick(View v) {
		int btn = v.getId();
		switch (btn) {
		case R.id.btnJoin:
			userInformation.setNickName(editID.getText().toString());
			clientThread.getClient().setMainActivity(MainActivity.this);
			clientThread.joinUser(editID.getText().toString(), phoneNumber);
			break;
		}
	}
	
	public void nickNameError(){
		Toast toast = Toast.makeText(this, "닉네임이 중복되었습니다. 다시 입력해주세요.", 
				Toast.LENGTH_SHORT); 
		toast.show(); 
	}
	
	public void enterMainMenu(){
		startActivity(new Intent(this, MainMenu.class));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
