package kr.co.ddonggame.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {
	private SurfaceHolder mSurfaceHolder;
	private MainView mMainView;
	private boolean mRunning = false;
	
	public MainThread(SurfaceHolder surfaceHolder, MainView mMainView){
		mSurfaceHolder = surfaceHolder;
		this.mMainView = mMainView;
	}
	
	public SurfaceHolder getSurfaceHolder(){
		return mSurfaceHolder;
	}
	
	public void setRunning(boolean run){
		mRunning = run;
	}
	
	@Override
	public void run() {
		Canvas canvas;
		while(mRunning){
			canvas = null;
			try{
				canvas = mSurfaceHolder.lockCanvas(null);
				synchronized (mSurfaceHolder) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
					}
				}
			} finally {
				if( canvas != null){
					mSurfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
}
