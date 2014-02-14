package com.example.issatsousse2;

import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	EditText email, password;
	Button ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ok = (Button) findViewById(R.id.button1);
		ok.setOnClickListener(this);
		email = (EditText) findViewById(R.id.editText1);
		password = (EditText) findViewById(R.id.editText2);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.button1:
			String chEmail = email.getText().toString();
			String chPassword = password.getText().toString();

			if (isValidEmail(chEmail) && chPassword.equals("admin")) {
				Intent intent = new Intent(this, SecondActivity.class);
				intent.putExtra("email", email.getText().toString());
				startActivity(intent);

				finish();
			} else {
				Toast.makeText(this, "Tapper votre email correctement",
						Toast.LENGTH_LONG).show();
			}
			break;
		default:
			break;
		}
	}

	/**
	 * Check if email address is valid
	 * 
	 * @param email
	 * @return
	 */
	private boolean isValidEmail(String email) {
		Pattern EMAIL_ADDRESS_PATTERN = Pattern
				.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\."
						+ "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

		return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
	}
}
