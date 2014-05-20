package kr.co.ddonggame.game;

import com.example.ddonggame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class GameView extends View {

	public GameView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int x,y;
		x=canvas.getWidth();
		y=canvas.getHeight();
		x/=4;
		Bitmap card = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		Bitmap card2 = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		Bitmap card3 = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		Bitmap card4 = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		/*canvas.drawBitmap(card, 0, 0, null);
		canvas.drawBitmap(card2, x, 0, null);
		canvas.drawBitmap(card3, 2*x, 0, null);
		canvas.drawBitmap(card4, 3*x, 0, null);*/
		canvas.drawBitmap(card,null,new Rect(0,0,x,y),null);
		canvas.drawBitmap(card2,null,new Rect(x,0,2*x,y),null);
		canvas.drawBitmap(card3,null,new Rect(2*x,0,3*x,y),null);
		canvas.drawBitmap(card4,null,new Rect(3*x,0,4*x,y),null);
		super.onDraw(canvas);
	}

}
