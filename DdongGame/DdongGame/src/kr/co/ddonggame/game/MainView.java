package kr.co.ddonggame.game;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainView extends SurfaceView implements SurfaceHolder.Callback {

	private MainThread mMainThread;
	private boolean mDrawCls = false;
	private ScreenConfig mScreenConfig;

	public MainView(Context context, AttributeSet attribute) {
		super(context, attribute);
		getHolder().addCallback(this);
		mMainThread = new MainThread(getHolder(), this);
		setFocusable(true);
	}

	public void init(int width, int height, GameActivity mGameActivity) {
		mScreenConfig = new ScreenConfig(width, height);
		mScreenConfig.setSize(2000, 1000);
		mDrawCls = true;
	}

	@Override
	public void onDraw(Canvas canvas) {
		if (mDrawCls == false)
			return;
		canvas.drawColor(Color.WHITE);
		Paint backPaint = new Paint();
		backPaint.setColor(Color.rgb(255, 0, 0));
		canvas.drawRect(mScreenConfig.getX(0), mScreenConfig.getY(0),
				mScreenConfig.getX(200), mScreenConfig.getY(200), backPaint);
		backPaint.setColor(Color.rgb(0, 255, 0));
		canvas.drawRect(mScreenConfig.getX(900), mScreenConfig.getY(400),
				mScreenConfig.getX(1100), mScreenConfig.getY(600), backPaint);
		backPaint.setColor(Color.rgb(0, 0, 255));
		canvas.drawRect(mScreenConfig.getX(1800), mScreenConfig.getY(800),
				mScreenConfig.getX(2000), mScreenConfig.getY(1000), backPaint);

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		mMainThread.setRunning(true);
		
		try{
			if(mMainThread.getState() == Thread.State.TERMINATED){
				mMainThread = new MainThread(getHolder(), this);
				mMainThread.setRunning(true);
				setFocusable(true);
				mMainThread.start();
			} else {
				mMainThread.start();
			}
		} catch (Exception ex){
			Log.i("MainView", "ex : " + ex.toString());
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		mMainThread.setRunning(false);
		while(retry){
			try {
				mMainThread.join();
				retry = false;
			} catch (Exception ex) {
				Log.i("MainView", "surfaceDestroyed ex : " + ex.toString());
			}
		}
	}

}
