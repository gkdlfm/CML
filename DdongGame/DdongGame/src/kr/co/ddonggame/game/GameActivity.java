package kr.co.ddonggame.game;

import java.util.Random;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import kr.co.ddonggame.custom.CustomDialog;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ddonggame.R;

public class GameActivity extends ActionBarActivity implements OnClickListener, SensorEventListener {

	private ImageView firstCard, secondCard, thirdCard, fourthCard;
	private Bitmap hwatooDeck[] = new Bitmap[4];
	private Animation cardDownAnimation;
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
	private ProgressBar progressCircle;
	private int time=0;
	private boolean nextTurn=false;
	 private long         m_lLastTime;
	 private float        m_fSpeed;
	 private float        m_fCurX,  m_fCurY,  m_fCurZ;
	 private float        m_fLastX,  m_fLastY,  m_fLastZ;
	  
	  // �Ӱ谪 ����
	 private static final int  SHAKE_THRESHOLD = 800;
	  
	  // �Ŵ��� ��ü
	 private SensorManager    m_senMng;
	 private Sensor           m_senAccelerometer;
	
	private int shakeCount = 0;
	private boolean ddongCheck = false;
	private TextView textView;
	private TextView timeTextView;
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
		//clientThread.getInit();
		progressCircle = (ProgressBar)findViewById(R.id.progresscircle);
		textView = (TextView)findViewById(R.id.textViewWait);
		timeTextView = (TextView)findViewById(R.id.textViewTime	);
		timeTextView.setText("�����ð� : ");
		progressCircle.setVisibility(View.INVISIBLE);
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
		Random rand = new Random();
		int randInt = rand.nextInt(4)+1;
		int soundInt=0;
		switch(randInt){
		case 1:
			soundInt = R.raw.ddong_1;
			break;
		case 2:
			soundInt = R.raw.ddong_2;
			break;
		case 3:
			soundInt = R.raw.ddong_3;
			break;
		case 4:
			soundInt = R.raw.ddong_4;
			break;
		}
		sound = soundPool.load(this, soundInt, 1);
		
		 m_senMng = (SensorManager)getSystemService(SENSOR_SERVICE);
		 m_senAccelerometer = m_senMng.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	}

	public void init(){
		hwatooDeckInt = userInformation.getHwatooDeckInt();
		for(int i=0; i<4; i++){
			Log.i("gameActivity init : ",Integer.toString(hwatooDeckInt[i]));
			int wol = hwatooDeckInt[i]/100;
			int il = hwatooDeckInt[i] - (wol * 100);
			int tmp = getResources().getIdentifier(userInformation.getType() + wol + "_" + il,
					"drawable", "com.example.ddonggame");
			Log.i("type : ", userInformation.getType());
			hwatooDeck[i] = BitmapFactory.decodeResource(getResources(), tmp);
		}
	}
	Handler handler = new Handler(){
		public void handleMessage(Message msg){
			time++;
			int waitTime = 4;
			waitTime = waitTime - time;
			Log.i("time : ", Integer.toString(waitTime));
			timeTextView.setText("���� �ð� : " + waitTime + "��");
			if(waitTime==0){
				Random rd = new Random();
				int temp = rd.nextInt(4);
				String msgTemp = "#nextTurn_" + userInformation.getRoomNumber()
						+ "_" + userInformation.getNickName() + "_"
						+ hwatooDeckInt[temp];
				userInformation.setHwatooDeckInt(temp);
				clientThread.nextTurn(msgTemp);
			}
			if(ddongCheck==false)
				handler.sendEmptyMessageDelayed(0, 1000);
		}
	};
	/*public void init(String msg) {
		Log.i("GameActivity", "init : "+msg);
		StringTokenizer st = new StringTokenizer(msg, "_");
		String temp = st.nextToken();
		int i = 0;
		while (st.hasMoreTokens()) {
			int deck = Integer.parseInt(st.nextToken());
			int wol = deck / 100;
			int il = deck - (wol * 100);
			int tmp = getResources().getIdentifier(userInformation.getType() + wol + "_" + il,
					"drawable", "com.example.ddonggame");
			hwatooDeckInt[i] = deck;
			hwatooDeck[i++] = BitmapFactory.decodeResource(getResources(), tmp);
		}
	}
	*/
	
	public void cardChange(){
		time=0;
		handler.removeMessages(0);
		handler.sendEmptyMessage(0);
		doubleClickCheck=0;
		new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						hwatooDeckInt = userInformation.getHwatooDeckInt();
						for(int i=0; i<4; i++){
							int wol = hwatooDeckInt[i]/100;
							int il = hwatooDeckInt[i] - (wol * 100);
							int tmp = getResources().getIdentifier(userInformation.getType() + wol + "_" + il,
									"drawable", "com.example.ddonggame");
							hwatooDeck[i] = BitmapFactory.decodeResource(getResources(), tmp);
						}
						firstCard.setImageBitmap(hwatooDeck[0]);
						secondCard.setImageBitmap(hwatooDeck[1]);
						thirdCard.setImageBitmap(hwatooDeck[2]);
						fourthCard.setImageBitmap(hwatooDeck[3]);
						selectCard(10);
						checkDdong();
						doubleClickInit(1, 5);
					}
				});
			}
		}).start();
		progressFinish();
	}
	/*
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
										userInformation.getType() + wol + "_" + il, "drawable",
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
								doubleClickInit(1, 5);
							}
						}
					}
				});
			}
		}).start();
	}
	*/
	
	public void onStart()
	  {
	    super.onStart();
	    if(m_senAccelerometer != null)
	      m_senMng.registerListener(this, m_senAccelerometer, SensorManager.SENSOR_DELAY_GAME);
	  }
	  
	  // ���Ⱑ ������ ȣ��Ǵ� �Լ�
	  public void onStop()
	  {
	    super.onStop();
	    if(m_senMng != null)
	      m_senMng.unregisterListener(this);
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
		ddongCheck = true;
		Toast.makeText(this, "���� �Ǿ����ϴ�. ���� �����ּ���", Toast.LENGTH_SHORT).show();
		// 1�̸� ���̶� ����
		//clientThread.gameEnd();
		// return true;
	}

	public void gameEnd(String msg) {
		Log.i("GameActivity gameEnd", msg);
		String temp = msg.split("_")[1];
		// �Ҹ��߰�;
		Log.i("test", temp);
		if (temp.equals("#gameabnormalend")) {
			abnormalCheck = 2;
			// ������������
		}
		if (temp.equals(userInformation.getNickName())) {
			soundPool.play(sound, 1, 1, 1, 0, 1);
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
		if(doubleClickCheck==0)
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
			if (i == 3){
				//handler.sendEmptyMessage(0);
				checkDdong();
			}
		}
	}

	@SuppressLint("NewApi")
	void doubleClick(int cardNum) {
		doubleClick[cardNum - 1]++;
		if (doubleClickCheck == 0) {
			if (doubleClick[cardNum - 1] == 1) {
				
			} else if (doubleClick[cardNum - 1] == 2) {
				selectCard(cardNum);
				
			} else if (doubleClick[cardNum - 1] == 3) {
				// CustomDialog cs = new CustomDialog(this, "dd");
				// confirm.setTurnOverCard(hwatooDeckInt[cardNum - 1]);
				// confirm.show();
				Log.i("nextTurn      : ", Integer.toString(cardNum) + Integer.toString(hwatooDeckInt[cardNum-1]));
				String msg = "#nextTurn_" + userInformation.getRoomNumber()
						+ "_" + userInformation.getNickName() + "_"
						+ hwatooDeckInt[cardNum - 1];
				userInformation.setHwatooDeckInt(cardNum-1);
				clientThread.nextTurn(msg);
				doubleClickCheck = 1;
				progressCircle.setVisibility(View.VISIBLE);
				textView.setText("��븦 ��ٸ��� ���Դϴ�.");
				
			}
			
		}
		else{
			
		}
	}
	
	public void progressFinish(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						progressCircle.setVisibility(View.INVISIBLE);
						textView.setText("");
					}
				});
			}
		}).start();
		//handler.sendEmptyMessage(0);
		/*new Thread(new Runnable() {
			@Override
			public void run() {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog.
					}
				});
			}
		}).start();*/
	}
	
	/*private Handler handler = new Handler() {
	    public void handleMessage(Message msg) {
	    	if(msg.what == 0) {
	    		if(dialog != null && dialog.isShowing()){
	                dialog.dismiss();
	                Log.i("dialog", "dialog aaa");
	                dialog = null;
	            }
	        }
	    }
	};*/
	
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

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
	    {
	      long lCurTime  = System.currentTimeMillis();
	      long lGabOfTime  = lCurTime - m_lLastTime;
	      
	      // 0.1�ʺ��� �����Ǹ� ������ ���� (100ms)
	      if(lGabOfTime > 100)
	      {
	        m_lLastTime = lCurTime;
	        
	        m_fCurX = event.values[SensorManager.DATA_X];
	        m_fCurY = event.values[SensorManager.DATA_Y];
	        m_fCurZ = event.values[SensorManager.DATA_Z];
	        
	        //  ������ ���밪��  / lGabOfTime * 10000 �Ͽ� ���ǵ� ��� 
	        m_fSpeed = Math.abs(m_fCurX + m_fCurY + m_fCurZ - m_fLastX - m_fLastY - m_fLastZ) / lGabOfTime * 10000;
	        
	        // �Ӱ谪���� ũ�� �������� ��� ������ ����
	        if(m_fSpeed > SHAKE_THRESHOLD && ddongCheck)
	        {
	        	shakeCount++;
	        	if(shakeCount>20)
	        		clientThread.gameEnd();
	        }
	        
	        // ������ ��ġ ����
	        // m_fLastX = event.values[0]; �׳� �迭�� 0�� �ε����� X��
	        m_fLastX = event.values[SensorManager.DATA_X];
	        m_fLastY = event.values[SensorManager.DATA_Y];
	        m_fLastZ = event.values[SensorManager.DATA_Z];
	      }
	    }
	}


}
