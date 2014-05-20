package kr.co.ddonggame.game;

import kr.co.ddonggame.util.ConvertUtil;

import com.example.ddonggame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;

public class GameView extends View {
	private Bitmap firstCard;
	private Bitmap secondCard;
	private Bitmap thirdCard;
	private Bitmap fourthCard;
	
	private float cardWidth;
	private float cardHeight;

	public GameView(Context context) {
		super(context);
		
		firstCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		secondCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		thirdCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		fourthCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		
		cardWidth = ConvertUtil.convertPixelToDp(255, context);
		cardHeight = ConvertUtil.convertPixelToDp(400, context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		int x, y;
		x = canvas.getWidth();
		y = canvas.getHeight();
		x /= 4;
		
		Rect rect1 = new Rect(0, 0, x, y);
		
		canvas.drawBitmap(firstCard, null, new Rect(0, 0, (int)cardWidth, (int)cardHeight), null);
		canvas.drawBitmap(secondCard, null, new Rect(x,0,2*x,y), null);
		canvas.drawBitmap(thirdCard, null, new Rect(2*x,0,3*x,y), null);
		canvas.drawBitmap(fourthCard, null, new Rect(3*x,0,4*x,y), null);
		
		super.onDraw(canvas);
	}
}
