package kr.co.ddonggame.game;

import java.util.Random;
import java.util.StringTokenizer;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import kr.co.ddonggame.custom.CustomDialog;
import android.annotation.SuppressLint;
import android.app.Dialog;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ddonggame.R;

public class GameActivity extends ActionBarActivity implements OnClickListener {

	private ImageView firstCard, secondCard, thirdCard, fourthCard;
	private Bitmap hwatooDeck[] = new Bitmap[4];
	private Animation cardDownAnimation;
	private int deck[][] = new int[12][4];
	private int doubleClick[] = new int[4];
	private ClientThread clientThread = ClientThread.getInstance();
	private UserInformation userInformation = UserInformation.getInstance();
	private int originCardTop;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game);
		
		// ������ ȭ��� �׿��� ����� ȭ���� �ٸ��� ����. - WaitGameRoom.java ��
		// activity_waitgameroom.xml ����
		// WaitGameRoom���� ��� �غ� �ǰ� ������ ���ӽ����� ������ GameActivity�� ���۵ǰ�.

		// GameActivity ��û�ÿ� �������� �ش��ϴ� �ο���, ������ ���� ȭ������ Ÿ�Կ� ���� image�� Road�ϰ�, �ʱ�
		// ȭ���� �й�.
		// ���� imageView�� �ι� Ŭ���ÿ� ī�带 �ѱ� �غ� �Ϸ�� ���·� ��ΰ� �غ� �Ǹ� ī�尡 �Ű� ���Ե�.
		// ���� ���� ī�尡 4���� �Ǹ� ���̶�� �� Ȯ���ϱ� ���� Method�߰�.[ boolean DDongCheck() ]
		// imageView�� id������ �����ؼ� ���� ���� 4������ �Ǵ����ִ� ��.
		// int temp = R.drawable.a1_1;
		ran(12);
		clientThread.getClient().setGameActivity(this);
		clientThread.getInit();
		
		/*for (int j = 0; j < 4; j++) {
			int wol = deck[0][j] / 100;
			int il = deck[0][j] - (wol * 100);
			int temp = getResources().getIdentifier("a" + wol + "_" + il,
					"drawable", "com.example.ddonggame");
			hwatooDeck[0][j] = BitmapFactory.decodeResource(getResources(),
					temp);
		}*/
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
	
	public void init(String msg){
		StringTokenizer st = new StringTokenizer(msg, "_");
		String temp = st.nextToken();
		int i=0;
		while(st.hasMoreTokens()){
			int deck = Integer.parseInt(st.nextToken());
			int wol = deck/100;
			int il = deck-(wol*100);
			int tmp = getResources().getIdentifier("a" + wol + "_" + il,
					"drawable", "com.example.ddonggame");
			hwatooDeck[i++] = BitmapFactory.decodeResource(getResources(),
					tmp);
			/*new Thread(new Runnable() {
				@Override
				public void run() {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							
						}
					});
				}
			}).start();*/
		}
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
			firstCard.setImageBitmap(hwatooDeck[0]);
			doubleClick(1);
			break;
		case R.id.secondCard:
			secondCard.setImageBitmap(hwatooDeck[1]);
			doubleClick(2);
			break;
		case R.id.thirdCard:
			thirdCard.setImageBitmap(hwatooDeck[2]);
			doubleClick(3);
			break;
		case R.id.fourthCard:
			fourthCard.setImageBitmap(hwatooDeck[3]);
			doubleClick(4);
			break;
		default:
			break;
		}
	}

	@SuppressLint("NewApi")
	void doubleClick(int cardNum) {
		doubleClick[cardNum - 1]++;
		
		if (doubleClick[cardNum - 1] == 1) {
			return;
		} else if( doubleClick[cardNum - 1] == 2){		
			selectCard(cardNum);			
		} else if (doubleClick[cardNum - 1] == 3) {
			Dialog confirm = new CustomDialog(this, "�� �и� �����Ͻðڽ��ϱ�?");
			confirm.show();
		}
	}

	//�ִϸ��̼� ���� �޼ҵ�
	private void setAnimation(Animation animation) {
		animation.setFillAfter(true);
				
		firstCard.startAnimation(animation);
		secondCard.startAnimation(animation);
		thirdCard.startAnimation(animation);
		fourthCard.startAnimation(animation);		
	}

	private void selectCard(int cardNum) {		
		if (cardNum == 1 && firstCard.getY() == originCardTop) {
			firstCard.setY(firstCard.getY() - 50);
			secondCard.setY(originCardTop);
			thirdCard.setY(originCardTop);
			fourthCard.setY(originCardTop);
		} else if (cardNum == 2 && secondCard.getY() == originCardTop) {
			secondCard.setY(secondCard.getY() - 50);
			firstCard.setY(originCardTop);
			thirdCard.setY(originCardTop);
			fourthCard.setY(originCardTop);
		} else if (cardNum == 3 && thirdCard.getY() == originCardTop) {
			thirdCard.setY(thirdCard.getY() - 50);
			firstCard.setY(originCardTop);
			secondCard.setY(originCardTop);
			fourthCard.setY(originCardTop);
		} else if (cardNum == 4 && fourthCard.getY() == originCardTop) {
			fourthCard.setY(fourthCard.getY() - 50);
			firstCard.setY(originCardTop);
			secondCard.setY(originCardTop);
			thirdCard.setY(originCardTop);
		}			
		
		doubleClickInit(1, cardNum);		
	}
	
	private void doubleClickInit(){
		doubleClickInit(0, 0);
	}

	private void doubleClickInit(int clickCount, int cardNum) {		
		for (int i = 0; i < 4; i++){
			if(cardNum - 1 != i)
				doubleClick[i] = clickCount;
		}				
	}

	// ������ ó�� ī�带 �й��ϴ� ��� - 12��� ���ӿ� �����ϴ� user�� ���ڸ� ������ �����ϸ��
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
		}
	}

}
