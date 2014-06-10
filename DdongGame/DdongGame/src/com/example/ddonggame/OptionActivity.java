package com.example.ddonggame;

import android.os.Bundle;
import android.view.Window;
import android.app.Activity;

public class OptionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_option);
	}
}
