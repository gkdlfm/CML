package kr.co.ddonggame;

import kr.co.ddonggame.game.GameActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.ddonggame.R;

public class GameRoom extends ActionBarActivity implements OnClickListener{
	private Button[] btnRoom;
	boolean []roomOpenAndClose;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_gameroom);
		btnRoom = new Button[6];
		roomOpenAndClose = new boolean[6];
		
		
		for(int i=1; i<=6; i++){
			int temp = getResources().getIdentifier("btnRoom"+i+"Enter", "id", "com.example.ddonggame");
			btnRoom[i-1] = (Button)findViewById(temp);
			btnRoom[i-1].setOnClickListener(this);
		}
		
		//GameRoom Activity요청시 서버에서 방목록의 정보를 가져와서 room1TextView의 text (방 번호)를 바꾸어준다.
	}
	public void onClick(View v){
		int id = v.getId();
		switch (id) {
		case R.id.btnRoom1Enter:
			startActivity(new Intent(this, GameActivity.class));
			break;
		
		default:
			break;
		}
	}
}
