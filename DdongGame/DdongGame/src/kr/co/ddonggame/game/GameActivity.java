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
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
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
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.ddonggame.R;

public class GameActivity extends ActionBarActivity implements OnClickListener {

	private ImageView firstCard, secondCard, thirdCard, fourthCard;
	private Bitmap hwatooDeck[] = new Bitmap[4];
	private Animation cardDownAnimation;
	private int deck[][] = new int[12][4];
	private int doubleClick[] = new int[4];
	private int hwatooDeckInt[] = new int[4];
	private ClientThread clientThread = ClientThread.getInstance();
	private UserInformation userInformation = UserInformation.getInstance();
	private int originCardTop;
	private int[] checkInit = new int[4];
	private CustomDialog confirm;
	private SoundPool soundPool;
	private int sound = 0;
	private int abnormalCheck = 0;
	private int doubleClickCheck = 0;

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
		clientThread.getClient().setGameActivity(this);
		clientThread.getInit();

		firstCard = (ImageView) findViewById(R.id.firstCard);
		secondCard = (ImageView) findViewById(R.id.secondCard);
		thirdCard = (ImageView) findViewById(R.id.thirdCard);
		fourthCard = (ImageView) findViewById(R.id.fourthCard);

		originCardTop = (int) firstCard.getY();

		cardDownAnimation = AnimationUtils
				.loadAnimation(this, R.anim.card_down);

		setAnimation(cardDownAnimation);

		firstCard.setOnClickListener(this);
		secondCard.setOnClickListener(this);
		thirdCard.setOnClickListener(this);
		fourthCard.setOnClickListener(this);

		doubleClickInit();
		confirm = new CustomDialog(this, "�� �и� �����Ͻðڽ��ϱ�?");
		confirm.setGameActivity(this);
		soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
		sound = soundPool.load(this, R.raw.piano4, 1);
	}

	public void init(String msg) {
		Log.i("test", "init");
		StringTokenizer st = new StringTokenizer(msg, "_");
		String temp = st.nextToken();
		int i = 0;
		while (st.hasMoreTokens()) {
			int deck = Integer.parseInt(st.nextToken());
			int wol = deck / 100;
			int il = deck - (wol * 100);
			int tmp = getResources().getIdentifier("a" + wol + "_" + il,
					"drawable", "com.example.ddonggame");
			hwatooDeckInt[i] = deck;
			hwatooDeck[i++] = BitmapFactory.decodeResource(getResources(), tmp);
			/**/
		}
	}

	public void chageCard(String msg) {
		final String stringTmp = msg;
		doubleClickCheck=0;
		new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						StringTokenizer st = new StringTokenizer(stringTmp, "_");
						String temp = st.nextToken();
						int getCard = Integer.parseInt(st.nextToken());
						for (int i = 0; i < 4; i++) {
							if (doubleClick[i] == 3) {
								int wol = getCard / 100;
								int il = getCard - (wol * 100);
								int tmp = getResources().getIdentifier(
										"a" + wol + "_" + il, "drawable",
										"com.example.ddonggame");
								hwatooDeck[i] = BitmapFactory.decodeResource(
										getResources(), tmp);
								hwatooDeckInt[i] = getCard;
								switch (i) {
								case 0:
									firstCard.setImageBitmap(hwatooDeck[0]);
									break;
								case 1:
									secondCard.setImageBitmap(hwatooDeck[1]);
								case 2:
									thirdCard.setImageBitmap(hwatooDeck[2]);
								case 3:
									fourthCard.setImageBitmap(hwatooDeck[3]);
								}
								selectCard(10);
								checkDdong();
							}
						}
					}
				});
			}
		}).start();
	}

	protected void onDestroy() {
		super.onDestroy();
		if (abnormalCheck == 1) {

		} else if (abnormalCheck == 2) {

		} else {// �⺻�� 0�̸� �������� �޽����� ������ ��� ����ڵ��� ������ ����
			clientThread.gameAbnormalEnd();
		}
	}

	public void checkDdong() {
		for (int i = 1; i < 4; i++) {
			if (hwatooDeckInt[0] / 100 == (int) (hwatooDeckInt[i] / 100)) {
				continue;
			} else {
				return;
			}
		}
		abnormalCheck = 1;
		// 1�̸� ���̶� ����
		clientThread.gameEnd();
		// return true;
	}

	public void gameEnd(String msg) {
		String temp = msg.split("_")[1];
		// �Ҹ��߰�;
		if (temp.equals("#gameabnormalend")) {
			abnormalCheck = 2;
			// ������������
		}
		if (temp.equals(userInformation.getNickName())) {
			// ���̶� ����.
			soundPool.play(sound, 1, 1, 0, 0, 1);
		}
		finish();
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
			checkInit[0] = 1;
			doubleClick(1);
			break;
		case R.id.secondCard:
			secondCard.setImageBitmap(hwatooDeck[1]);
			checkInit[1] = 1;
			doubleClick(2);
			break;
		case R.id.thirdCard:
			thirdCard.setImageBitmap(hwatooDeck[2]);
			checkInit[2] = 1;
			doubleClick(3);
			break;
		case R.id.fourthCard:
			fourthCard.setImageBitmap(hwatooDeck[3]);
			checkInit[3] = 1;
			doubleClick(4);
			break;
		default:
			break;
		}
		for (int i = 0; i < 4; i++) {
			if (checkInit[i] != 1) {
				break;
			}
			if (i == 3)
				checkDdong();
		}
	}

	@SuppressLint("NewApi")
	void doubleClick(int cardNum) {
		doubleClick[cardNum - 1]++;
		if (doubleClickCheck == 0) {
			if (doubleClick[cardNum - 1] == 1) {
				return;
			} else if (doubleClick[cardNum - 1] == 2) {
				selectCard(cardNum);
			} else if (doubleClick[cardNum - 1] == 3) {
				// CustomDialog cs = new CustomDialog(this, "dd");
				// confirm.setTurnOverCard(hwatooDeckInt[cardNum - 1]);
				// confirm.show();
				String msg = "#nextturn_" + userInformation.getRoomNumber()
						+ "_" + userInformation.getNickName() + "_"
						+ hwatooDeckInt[cardNum - 1];
				clientThread.nextTurn(msg);
				doubleClickCheck = 1;
			}
		}
		else{
			
		}
	}

	// �ִϸ��̼� ���� �޼ҵ�
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
		} else {
			fourthCard.setY(originCardTop);
			firstCard.setY(originCardTop);
			secondCard.setY(originCardTop);
			thirdCard.setY(originCardTop);
		}

		doubleClickInit(1, cardNum);
	}

	private void doubleClickInit() {
		doubleClickInit(0, 0);
	}

	private void doubleClickInit(int clickCount, int cardNum) {
		for (int i = 0; i < 4; i++) {
			if (cardNum - 1 != i)
				doubleClick[i] = clickCount;
		}
	}

	// ������ ó�� ī�带 �й��ϴ� ��� - 12��� ���ӿ� �����ϴ� user�� ���ڸ� ������ �����ϸ��
	/*private void ran(int numberOfUser) {

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
	}*/

}
