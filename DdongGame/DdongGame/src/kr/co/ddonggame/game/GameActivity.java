package kr.co.ddonggame.game;

import java.util.Random;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ddonggame.R;

public class GameActivity extends ActionBarActivity implements OnClickListener {

	private ImageView firstCard, secondCard, thirdCard, fourthCard;
	private Bitmap hwatooDeck[][] = new Bitmap[12][4];
	private Animation cardDownAnimation;
	private int deck[][] = new int[12][4];
	private int doubleClick[] = new int[4];
	
	private int originCardTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game);
		
		// 방장의 화면과 그외의 사람의 화면을 다르게 구성. - WaitGameRoom.java 및
		// activity_waitgameroom.xml 구현
		// WaitGameRoom에서 모두 준비가 되고 방장이 게임시작을 누르면 GameActivity가 시작되게.

		// GameActivity 요청시에 방정보에 해당하는 인원수, 유저가 정한 화투패의 타입에 따라 image를 Road하고, 초기
		// 화투패 분배.
		// 같은 imageView를 두번 클릭시에 카드를 넘길 준비가 완료된 상태로 모두가 준비가 되면 카드가 옮겨 가게됨.
		// 이후 같은 카드가 4개가 되면 똥이라는 걸 확인하기 위해 Method추가.[ boolean DDongCheck() ]
		// imageView의 id값들을 조사해서 같은 월이 4장인지 판단해주는 것.
		// int temp = R.drawable.a1_1;
		ran(12);
		for (int j = 0; j < 4; j++) {
			int wol = deck[0][j] / 100;
			int il = deck[0][j] - (wol * 100);
			int temp = getResources().getIdentifier("a" + wol + "_" + il,
					"drawable", "com.example.ddonggame");
			hwatooDeck[0][j] = BitmapFactory.decodeResource(getResources(),
					temp);
		}
		firstCard = (ImageView) findViewById(R.id.firstCard);
		secondCard = (ImageView) findViewById(R.id.secondCard);
		thirdCard = (ImageView) findViewById(R.id.thirdCard);
		fourthCard = (ImageView) findViewById(R.id.fourthCard);
		
		originCardTop = (int) firstCard.getY();
		
		cardDownAnimation = AnimationUtils.loadAnimation(this, R.anim.card_down);
		
		setAnimation(cardDownAnimation);
		
		firstCard.setOnClickListener(this);
		secondCard.setOnClickListener(this);
		thirdCard.setOnClickListener(this);
		fourthCard.setOnClickListener(this);
		
		doubleClickInit();
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
			doubleClick(1);
			break;
		case R.id.secondCard:
			secondCard.setImageBitmap(hwatooDeck[0][1]);
			doubleClick(2);
			break;
		case R.id.thirdCard:
			thirdCard.setImageBitmap(hwatooDeck[0][2]);
			doubleClick(3);
			break;
		case R.id.fourthCard:
			fourthCard.setImageBitmap(hwatooDeck[0][3]);
			doubleClick(4);
			break;
		default:
			break;
		}
	}

	@SuppressLint("NewApi")
	void doubleClick(int cardNum) {
		if (doubleClick[cardNum - 1] == 0) {
			doubleClick[cardNum - 1]++;
			return;
		}

		for (int i = 0; i < 4; i++) {
			if (i != cardNum - 1)
				doubleClick[i] = 1;
			doubleClick[i]++;
		}
		
		if (doubleClick[cardNum - 1] == 2) {
			
			if (cardNum == 1 && firstCard.getY() == originCardTop) {
				firstCard.setY(firstCard.getY() - 50);
				secondCard.setY(originCardTop);
				thirdCard.setY(originCardTop);
				fourthCard.setY(originCardTop);
			}
			if (cardNum == 2 && secondCard.getY() == originCardTop) {
				secondCard.setY(secondCard.getY() - 50);
				firstCard.setY(originCardTop);
				thirdCard.setY(originCardTop);
				fourthCard.setY(originCardTop);
			}
			if (cardNum == 3 && thirdCard.getY() == originCardTop) {
				thirdCard.setY(thirdCard.getY() - 50);
				firstCard.setY(originCardTop);
				secondCard.setY(originCardTop);
				fourthCard.setY(originCardTop);
			}
			if (cardNum == 4 && fourthCard.getY() == originCardTop) {
				fourthCard.setY(fourthCard.getY() - 50);
				firstCard.setY(originCardTop);
				secondCard.setY(originCardTop);
				thirdCard.setY(originCardTop);
			}
			
			doubleClickInit(1);
		}
	}

	//애니매이션 동작 메소드
	private void setAnimation(Animation animation) {
		animation.setFillAfter(true);
				
		firstCard.startAnimation(animation);
		secondCard.startAnimation(animation);
		thirdCard.startAnimation(animation);
		fourthCard.startAnimation(animation);		
	}
	
	private void doubleClickInit(){
		doubleClickInit(0);
	}

	private void doubleClickInit(int clickCount) {		
		for (int i = 0; i < 4; i++){
			doubleClick[i] = clickCount;
		}				
	}

	// 서버가 처음 카드를 분배하는 방법 - 12대신 게임에 참여하는 user의 숫자를 변수로 대입하면됨
	private void ran(int numberOfUser) {

		Random rand = new Random();
		int i = 0, j = 0;
		int[] count = new int[numberOfUser];
		for (i = 0; i < numberOfUser; i++)
			count[i] = 0;
		i = 0;
		j = 0;
		while (true) {
			int user = rand.nextInt(numberOfUser);
			int card = rand.nextInt(4);
			
			if (i == numberOfUser)
				break;
			
			if (count[user] == 4) {
				continue;
			}
			
			deck[user][count[user]] = (i + 1) * 100 + (j + 1);
			count[user]++;
			j++;
			
			if (j == 4) {
				j = 0;
				i++;
			}
			
			//Log.i("test i : ", Integer.toString(i));
			//Log.i("test j : ", Integer.toString(j));
		}
	}

}
