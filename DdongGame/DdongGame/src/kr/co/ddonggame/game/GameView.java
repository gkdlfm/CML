package kr.co.ddonggame.game;

import kr.co.ddonggame.util.ConvertUtil;

import com.example.ddonggame.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class GameView extends View {
	private Bitmap firstCard;
	private Bitmap secondCard;
	private Bitmap thirdCard;
	private Bitmap fourthCard;

	public GameView(Context context) {
		super(context);
		
		firstCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		secondCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		thirdCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		fourthCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);		
	}
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {		
		int cardWidth = firstCard.getWidth();
		int cardHeight = firstCard.getHeight();
		
		int realCardWidth = canvas.getWidth() / 4;
		int realCardHeight = cardHeight * realCardWidth / cardWidth;
		
		int marginHeight = (canvas.getHeight() - realCardHeight) / 2;
		
		canvas.drawBitmap(firstCard, null, new Rect(0, marginHeight, realCardWidth, realCardHeight + marginHeight), null);
		canvas.drawBitmap(secondCard, null, new Rect(realCardWidth, marginHeight, 2 * realCardWidth, realCardHeight + marginHeight), null);
		canvas.drawBitmap(thirdCard, null, new Rect(2 * realCardWidth, marginHeight, 3 * realCardWidth, realCardHeight + marginHeight), null);
		canvas.drawBitmap(fourthCard, null, new Rect(3 * realCardWidth, marginHeight, 4 * realCardWidth, realCardHeight + marginHeight), null);
		
		super.onDraw(canvas);
	}
}
