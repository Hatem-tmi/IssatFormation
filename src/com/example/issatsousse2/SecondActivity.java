package com.example.issatsousse2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends Activity implements OnClickListener {

	private TextView textView;
	private Button listGroupsButton;
	private Button listStudentsButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_second);

		textView = (TextView) findViewById(R.id.textView);
		textView.setText(this.getIntent().getStringExtra("email"));

		listGroupsButton = (Button) findViewById(R.id.listGroupsButton);
		listGroupsButton.setOnClickListener(this);

		listStudentsButton = (Button) findViewById(R.id.listStudentsButton);
		listStudentsButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.listGroupsButton:
			startActivity(new Intent(this, ListGroupsActivity.class));
		case R.id.listStudentsButton:
			startActivity(new Intent(this, ListStudentsActivity.class));
			break;
		default:
			break;
		}
	}

}
