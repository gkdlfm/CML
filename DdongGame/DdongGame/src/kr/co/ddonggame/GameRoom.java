package kr.co.ddonggame;

import kr.co.ddonggame.game.GameActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ddonggame.R;

public class GameRoom extends ActionBarActivity implements OnClickListener{
	private Button btnRoom1;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameroom);
		btnRoom1 = (Button)findViewById(R.id.btnRoom1);
		btnRoom1.setOnClickListener(this);
	}
	public void onClick(View v){
		int id = v.getId();
		switch (id) {
		case R.id.btnRoom1:
			startActivity(new Intent(this, GameActivity.class));
			break;

		default:
			break;
		}
	}
}
