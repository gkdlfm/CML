package kr.co.ddonggame;

import kr.co.ddonggame.client.ClientThread;
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
import android.widget.EditText;

import com.example.ddonggame.R;

public class MainActivity extends ActionBarActivity implements OnClickListener{
	private Button btnJoin;
	private EditText editID;
	private ClientThread clientThread;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		
		
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		btnJoin = (Button)this.findViewById(R.id.btnJoin);
		btnJoin.setOnClickListener(this);
		editID = (EditText)this.findViewById(R.id.editID);
		clientThread = ClientThread.getInstance();
		clientThread.start();
	}
	protected void onDestroy(){
		String msg = "#quit";
		clientThread.getClient().handleMessage(msg);
	}
	public void onClick(View v){
		int btn = v.getId();
		Intent intent;
		switch(btn){
		case R.id.btnJoin:
			intent = new Intent(this, MainMenu.class);
			startActivity(intent);
			break;
		}
	}
	public boolean joinCheck(){
		clientThread.joinUser(editID.getText().toString());
		return true;
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
