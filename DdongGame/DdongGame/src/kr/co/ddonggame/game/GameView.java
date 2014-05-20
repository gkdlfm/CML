package kr.co.ddonggame.game;

import com.example.ddonggame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.View;

public class GameView extends View {

	public GameView(Context context) {
		super(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		Bitmap card = BitmapFactory.decodeResource(getResources(), R.drawable.card_back);
		canvas.drawBitmap(card, 0, 0, null);
		super.onDraw(canvas);
	}

}
