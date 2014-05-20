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

	public GameView(Context context) {
		super(context);
		
		firstCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		secondCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		thirdCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		fourthCard = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {		
		int cardWidth = firstCard.getWidth();
		int cardHeight = firstCard.getHeight();
		
		int realCardWidth = canvas.getWidth() / 4;
		int realCardHeight = cardHeight * realCardWidth / cardWidth;
		
		canvas.drawBitmap(firstCard, null, new Rect(0, 0, realCardWidth, realCardHeight), null);
		canvas.drawBitmap(secondCard, null, new Rect(realCardWidth, 0, 2 * realCardWidth, realCardHeight), null);
		canvas.drawBitmap(thirdCard, null, new Rect(2 * realCardWidth, 0, 3 * realCardWidth, realCardHeight), null);
		canvas.drawBitmap(fourthCard, null, new Rect(3 * realCardWidth, 0,4 * realCardWidth, realCardHeight), null);
		
		super.onDraw(canvas);
	}
}
