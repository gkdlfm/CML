package kr.co.ddonggame.game;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.widget.ImageView;

import com.example.ddonggame.R;

public class GameActivity extends ActionBarActivity implements OnClickListener {

	private ImageView firstCard;
	private ImageView secondCard;
	private ImageView thirdCard;
	private ImageView fourthCard;
	Bitmap hwatooDeck[][] = new Bitmap[12][4];
	
	
	/*
	//서버가 처음 카드를 분배하는 방법 - 12대신 게임에 참여하는 user의 숫자를 변수로 대입하면됨
	void ran(){
		int deck[][] = new int[12][4];
		Random rand = new Random();
		int i=0, j=0;
		int [][]count = new int[12][4];
		for(i=0; i<12; i++)
			for(j=0; j<4; j++)
				count[i][j] = 0;
		i=0;j=0;
		while(true){
			int user = rand.nextInt(12);
			int card = rand.nextInt(4);
			if(i==12 && j==4)
				break;
			if(count[user][card]==4){
				continue;
			}
			deck[user][card]=i*100 + j;
			count[user][card]++;
			j++;
			if(j==4){
				j=0;
				i++;
			}
		}
	}*/
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game);

		//방장의 화면과 그외의 사람의 화면을 다르게 구성. - WaitGameRoom.java 및 activity_waitgameroom.xml 구현
		//WaitGameRoom에서 모두 준비가 되고 방장이 게임시작을 누르면 GameActivity가 시작되게.
		
		
		//GameActivity 요청시에 방정보에 해당하는 인원수, 유저가 정한 화투패의 타입에 따라 image를 Road하고, 초기 화투패 분배.
		//같은 imageView를 두번 클릭시에 카드를 넘길 준비가 완료된 상태로 모두가 준비가 되면 카드가 옮겨 가게됨.
		//이후 같은 카드가 4개가 되면 똥이라는 걸 확인하기 위해 Method추가.[ boolean DDongCheck() ] imageView의 id값들을 조사해서 같은 월이 4장인지 판단해주는 것.
		int temp = R.drawable.a1_1;
		for (int j = 0; j < 4; j++) {
			hwatooDeck[0][j] = BitmapFactory.decodeResource(getResources(),
					temp++);
		}
		firstCard = (ImageView)findViewById(R.id.firstCard);
		secondCard = (ImageView)findViewById(R.id.secondCard);
		thirdCard = (ImageView)findViewById(R.id.thirdCard);
		fourthCard = (ImageView)findViewById(R.id.fourthCard);
		firstCard.setOnClickListener(this);
		secondCard.setOnClickListener(this);
		thirdCard.setOnClickListener(this);
		fourthCard.setOnClickListener(this);
		/*
		 * setContentView(new GameView(this));
		 * 
		 * if (savedInstanceState == null) {
		 * getSupportFragmentManager().beginTransaction() .add(R.id.container,
		 * new PlaceholderFragment()).commit(); }
		 */
		// firstCard = (ImageView) findViewById(R.drawable.card_back);
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
		int id = v.getId();
		switch (id) {
		case R.id.firstCard:
			firstCard.setImageBitmap(hwatooDeck[0][0]);
			break;
		case R.id.secondCard:
			secondCard.setImageBitmap(hwatooDeck[0][1]);
			break;
		case R.id.thirdCard:
			thirdCard.setImageBitmap(hwatooDeck[0][2]);
			break;
		case R.id.fourthCard:
			fourthCard.setImageBitmap(hwatooDeck[0][3]);
			break;
		default:
			break;
		}
	}

}
