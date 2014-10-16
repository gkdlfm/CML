package kr.co.ddonggame;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.ddonggame.GameMethodActivity;
import com.example.ddonggame.OptionActivity;
import com.example.ddonggame.R;

public class MainMenu extends ActionBarActivity implements OnClickListener {
	private Button btnGameStart;
	private Button btnGameMethod;
	private Button btnGameOption;
	private Button btnGameExit;
	private ClientThread clientThread = ClientThread.getInstance();
	private UserInformation userInformation = UserInformation.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main_menu);
		btnGameStart = (Button) findViewById(R.id.btnGameStart);
		btnGameMethod = (Button) findViewById(R.id.btnGameMethod);
		btnGameOption = (Button) findViewById(R.id.btnGameOption);
		btnGameExit = (Button) findViewById(R.id.btnGameExit);
		btnGameStart.setOnClickListener(this);
		btnGameMethod.setOnClickListener(this);
		btnGameOption.setOnClickListener(this);
		btnGameExit.setOnClickListener(this);
		clientThread.getType();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		clientThread = ClientThread.getInstance();
	}

	@Override
	public void onClick(View v) {
		int btn = v.getId();
		switch (btn) {
		case R.id.btnGameStart:
			startActivity(new Intent(this, GameRoom.class));
			break;
		case R.id.btnGameMethod:
			startActivity(new Intent(this, GameMethodActivity.class));
			break;
		case R.id.btnGameOption:
			Intent optionDialog = new Intent(this, OptionActivity.class);
			startActivity(optionDialog);
			break;
		case R.id.btnGameExit:
			this.finish();
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			clientThread.quit();
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_menu, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_main_menu,
					container, false);
			return rootView;
		}
	}
}
