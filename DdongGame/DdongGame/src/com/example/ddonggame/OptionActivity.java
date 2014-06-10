package com.example.ddonggame;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;

public class OptionActivity extends Activity implements OnClickListener,
		OnCheckedChangeListener {

	private RadioGroup radioType;
	private RadioButton radioTypeA;
	private RadioButton radioTypeB;
	private RadioButton radioTypeC;

	private ImageView typeImage;
	private BitmapDrawable typeImageDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_option);

		radioType = (RadioGroup) findViewById(R.id.radio_type);
		radioTypeA = (RadioButton) findViewById(R.id.radio_type_a);
		radioTypeB = (RadioButton) findViewById(R.id.radio_type_b);
		radioTypeC = (RadioButton) findViewById(R.id.radio_type_c);

		radioType.setOnCheckedChangeListener(this);

		typeImage = (ImageView) findViewById(R.id.type_image);
	}

	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		switch (checkedId) {
		case R.id.radio_type_a:
			typeImageDrawable = (BitmapDrawable) getResources().getDrawable(
					R.drawable.a1_1);
			
			break;
		case R.id.radio_type_b:
			typeImageDrawable = (BitmapDrawable) getResources().getDrawable(
					R.drawable.b1_4);

			break;
		case R.id.radio_type_c:
			typeImageDrawable = (BitmapDrawable) getResources().getDrawable(
					R.drawable.c1_4);

			break;
		}

		typeImage.setImageDrawable(typeImageDrawable);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}
}
