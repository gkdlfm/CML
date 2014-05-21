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
	//������ ó�� ī�带 �й��ϴ� ��� - 12��� ���ӿ� �����ϴ� user�� ���ڸ� ������ �����ϸ��
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

		//������ ȭ��� �׿��� ����� ȭ���� �ٸ��� ����. - WaitGameRoom.java �� activity_waitgameroom.xml ����
		//WaitGameRoom���� ��� �غ� �ǰ� ������ ���ӽ����� ������ GameActivity�� ���۵ǰ�.
		
		
		//GameActivity ��û�ÿ� �������� �ش��ϴ� �ο���, ������ ���� ȭ������ Ÿ�Կ� ���� image�� Road�ϰ�, �ʱ� ȭ���� �й�.
		//���� imageView�� �ι� Ŭ���ÿ� ī�带 �ѱ� �غ� �Ϸ�� ���·� ��ΰ� �غ� �Ǹ� ī�尡 �Ű� ���Ե�.
		//���� ���� ī�尡 4���� �Ǹ� ���̶�� �� Ȯ���ϱ� ���� Method�߰�.[ boolean DDongCheck() ] imageView�� id������ �����ؼ� ���� ���� 4������ �Ǵ����ִ� ��.
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
