package com.example.ddonggame;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class OptionActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private RadioGroup radioType;
	private RadioButton radioTypeA;
	private RadioButton radioTypeB;
	private RadioButton radioTypeC;
	private Button btnOK;
	private Button btnNO;
	private UserInformation userInformation = UserInformation.getInstance();
	private ImageView typeImage;
	private BitmapDrawable typeImageDrawable;
	private ClientThread clientThread = ClientThread.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_option);

		radioType = (RadioGroup) findViewById(R.id.radio_type);
		radioTypeA = (RadioButton) findViewById(R.id.radio_type_a);
		radioTypeA.setId(1);
		radioTypeB = (RadioButton) findViewById(R.id.radio_type_b);
		radioTypeB.setId(2);
		radioTypeC = (RadioButton) findViewById(R.id.radio_type_c);
		radioTypeC.setId(3);
		btnOK = (Button)findViewById(R.id.option_yes_btn);
		btnNO = (Button)findViewById(R.id.option_no_btn);
		btnOK.setOnClickListener(this);
		btnNO.setOnClickListener(this);
		radioType.setOnCheckedChangeListener(this);
		typeImage = (ImageView) findViewById(R.id.type_image);
		String type = userInformation.getType();
		if(type.equals("a")){
			radioType.check(1);
			//onCheckedChanged(radioType, 1);
		}else if(type.equals("b")){
			radioType.check(2);
			//onCheckedChanged(radioType, 2);
		}
		else if(type.equals("c")){
			radioType.check(3);
			//onCheckedChanged(radioType, 3);
		}
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		switch (checkedId) {
		case 1:
			typeImageDrawable = (BitmapDrawable) getResources().getDrawable(
					R.drawable.a1_1);
			clientThread.setType("a");
			userInformation.setType("a");
			break;
		case 2:
			typeImageDrawable = (BitmapDrawable) getResources().getDrawable(
					R.drawable.b1_4);
			clientThread.setType("b");
			userInformation.setType("b");
			break;
		case 3:
			typeImageDrawable = (BitmapDrawable) getResources().getDrawable(
					R.drawable.c1_4);
			clientThread.setType("c");
			userInformation.setType("c");
			break;
		}

		typeImage.setImageDrawable(typeImageDrawable);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.radio_type_a:
			clientThread.setType("a");
			userInformation.setType("a");
			break;
		case R.id.radio_type_b:
			Log.i("test", "bbb");
			clientThread.setType("b");
			userInformation.setType("b");
			break;
		case R.id.radio_type_c:
			clientThread.setType("c");
			userInformation.setType("c");
			break;
		case R.id.option_yes_btn:
			finish();
			break;
		case R.id.option_no_btn:
			finish();
			break;
		}
	}
}
