package kr.co.ddonggame.game;

import com.example.ddonggame.R;
import com.example.ddonggame.R.id;
import com.example.ddonggame.R.layout;
import com.example.ddonggame.R.menu;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.os.Build;

public class GameActivity extends ActionBarActivity implements OnClickListener {
	MainView mainView;
	private WakeLock wakeLock;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_game);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		PowerManager pm = (PowerManager)this.getApplicationContext().getSystemService(Context.POWER_SERVICE);
		wakeLock = pm.newWakeLock(PowerManager.FULL_WAKE_LOCK, "gamelock");
		wakeLock.acquire();
		int ScreenWidth = getWindowManager().getDefaultDisplay().getWidth();
		int ScreenHeight = getWindowManager().getDefaultDisplay().getHeight();
		
		mainView = (MainView)findViewById(R.id.game_view);
		mainView.init(ScreenWidth, ScreenHeight, this);
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
			View rootView = inflater.inflate(R.layout.fragment_game, container,
					false);
			return rootView;
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

}
