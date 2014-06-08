package kr.co.ddonggame.custom;

import kr.co.ddonggame.client.ClientThread;
import kr.co.ddonggame.client.UserInformation;
import kr.co.ddonggame.game.GameActivity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ddonggame.R;

public class CustomDialog extends Dialog implements OnClickListener {
	private Button okButton;
	private Button noButton;
	private int state = 0;
	private EditText entryCountEdit;
	private ClientThread clientThread = ClientThread.getInstance();
	private GameActivity gameActivity;
	private int turnOverCard;
	private UserInformation userInformation = UserInformation.getInstance();
	public CustomDialog(Context context, String title) {
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);
		TextView titleView = (TextView) findViewById(R.id.customDialogTitle);
		titleView.setText(title);
		state = 1;
		okButton = (Button) findViewById(R.id.customDialogOkBtn);
		noButton = (Button) findViewById(R.id.customDialogNoBtn);

		okButton.setOnClickListener(this);
		noButton.setOnClickListener(this);

	}

	/**
	 * 0 : entryCount
	 * 
	 * @param context
	 * @param title
	 * @param editText
	 */
	public CustomDialog(Context context, String title, boolean... editText) {
		super(context);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_dialog);
		state = 2;
		TextView titleView = (TextView) findViewById(R.id.customDialogTitle);
		titleView.setText(title);

		LinearLayout editWrap = (LinearLayout) findViewById(R.id.editWrap);

		for (int i = 0; i < editText.length; i++) {
			if (i == 0 && editText[i]) {
				TextView entryCountTitle = new TextView(context);
				entryCountTitle.setText("ÀÎ¿ø ¼ö : ");
				entryCountTitle.setTextSize(24);
				editWrap.addView(entryCountTitle);

				entryCountEdit = new EditText(context);
				entryCountEdit.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
				editWrap.addView(entryCountEdit);
			}
		}

		okButton = (Button) findViewById(R.id.customDialogOkBtn);
		noButton = (Button) findViewById(R.id.customDialogNoBtn);

		okButton.setOnClickListener(this);
		noButton.setOnClickListener(this);

	}

	public void onClick(View view) {
		if (view == okButton) {
			if (state == 1) {
				
			}
			else if (state == 2) {
				int entryCount = (entryCountEdit.getText().toString()
						.equals("") ? 0 : Integer.parseInt(entryCountEdit
						.getText().toString()));
				if (entryCount < 13 && entryCount > 3) {
					clientThread.makeRoom(entryCount);
				} else {

				}
			}
			dismiss();
		} else if (view == noButton) {
			dismiss();
		}
	}

	public GameActivity getGameActivity() {
		return gameActivity;
	}

	public void setGameActivity(GameActivity gameActivity) {
		this.gameActivity = gameActivity;
	}

	public int getTurnOverCard() {
		return turnOverCard;
	}

	public void setTurnOverCard(int turnOverCard) {
		this.turnOverCard = turnOverCard;
	}
}
