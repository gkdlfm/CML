package kr.co.ddonggame.custom;

import com.example.ddonggame.R;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CustomDialog extends Dialog implements OnClickListener{
	private Button okButton;
	private Button noButton;

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
	
	public void onClick(View view){
		if(view == okButton){
			
		} else if (view == noButton){
			dismiss();
		}
	}
}
