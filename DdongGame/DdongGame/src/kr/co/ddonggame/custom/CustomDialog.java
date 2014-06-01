package kr.co.ddonggame.custom;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;

import com.example.ddonggame.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomDialog extends Dialog implements OnClickListener{
	private Button okButton;
	private Button noButton;
	private int entryCount=12;
	private ClientThread clientThread = ClientThread.getInstance();
	public CustomDialog(Context context, String title) {
		super(context);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);		
		
		
		TextView titleView = (TextView) findViewById(R.id.customDialogTitle);
		titleView.setText(title);
		
		okButton = (Button) findViewById(R.id.customDialogOkBtn);
		noButton = (Button) findViewById(R.id.customDialogNoBtn);
		
		okButton.setOnClickListener(this);
		noButton.setOnClickListener(this);
		
	}
	
	/**
	 * 0 : entryCount
	 * @param context
	 * @param title
	 * @param editText
	 */
	public CustomDialog(Context context, String title, boolean... editText) {
		super(context);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);		
		
		TextView titleView = (TextView) findViewById(R.id.customDialogTitle);
		titleView.setText(title);
		
		LinearLayout editWrap = (LinearLayout) findViewById(R.id.editWrap);
		
		for(int i = 0 ; i < editText.length; i ++){
			if(i == 0 && editText[i]){
				TextView entryCountTitle = new TextView(context);
				entryCountTitle.setText("ÀÎ¿ø ¼ö : ");
				editWrap.addView(entryCountTitle);
				
				
			}
		}
		
		okButton = (Button) findViewById(R.id.customDialogOkBtn);
		noButton = (Button) findViewById(R.id.customDialogNoBtn);
		
		okButton.setOnClickListener(this);
		noButton.setOnClickListener(this);
		
	}
	
	@Override
	public void onClick(View view){
		if(view == okButton){
			clientThread.makeRoom(entryCount);
			//this.onStop();
			dismiss();
			//return;
		} else if (view == noButton){
			dismiss();
			//this.onStop();
			//return;
		}
	}
}
