package com.example.issatsousse2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ListGroupsActivity extends Activity implements OnItemClickListener {

	private ListView listView;
	private Button backButton;

	// Data to populate ListView
	private String[] data = new String[] { "Group1", "Group2", "Group3",
			"Group4" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_groups_layout);

		listView = (ListView) findViewById(R.id.list_view);
		backButton = (Button) findViewById(R.id.backButton);

		listView.setOnItemClickListener(this);
		backButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				(ListGroupsActivity.this).finish();
			}
		});

		// Create Adapter to adapt data on listView
		ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, android.R.id.text1, data);
		listView.setAdapter(myAdapter);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(
				this,
				String.format("Group item at position %s is clicked", position),
				Toast.LENGTH_SHORT).show();
	}
}
