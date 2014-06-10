package kr.co.ddonggame.custom;

import kr.co.ddonggame.client.ClientThread;

import com.example.ddonggame.R;
import com.example.ddonggame.R.layout;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CustomDialogActivity extends Activity implements OnClickListener {

	private Button customDialogYesBtn, customDialogNoBtn;
	private int type;
	private EditText entryCountEdit;
	private ClientThread clientThread = ClientThread.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_custom_dialog);

		Intent paramIntent = getIntent(); // type과 title을 받아오기 위한 intent
		type = paramIntent.getIntExtra("type", 0);

		makeDialog(paramIntent.getStringExtra("title"));
	}

	
	private void makeDialog(String title) {
		customDialogYesBtn = (Button) findViewById(R.id.custom_dialog_yes_btn);
		customDialogNoBtn = (Button) findViewById(R.id.custom_dialog_no_btn);
		
		if (type == 1) {
			TextView dialogTitle = (TextView) findViewById(R.id.custom_dialog_title);
			dialogTitle.setText(title);

			LinearLayout editWrap = (LinearLayout) findViewById(R.id.custom_dialog_edit_wrap);
			TextView entryCountTitle = new TextView(this);
			entryCountTitle.setText("인원 수 : ");
			entryCountTitle.setTextSize(15);
			editWrap.addView(entryCountTitle);

			entryCountEdit = new EditText(this);
			
			entryCountEdit.setLayoutParams(new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
			editWrap.addView(entryCountEdit);
			
			customDialogYesBtn.setText("생성하기");
			customDialogNoBtn.setVisibility(View.GONE);
		}
		
		customDialogYesBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.custom_dialog_yes_btn:
			if (type == 1) {
				int entryCount = (entryCountEdit.getText().toString()
						.equals("") ? 0 : Integer.parseInt(entryCountEdit
						.getText().toString()));
				if (entryCount < 13 && entryCount > 3) {
					clientThread.makeRoom(entryCount);
				} else {

				}
			}
			break;

		case R.id.custom_dialog_no_btn:
			break;
		}
	}
}
