package com.example.issatsousse2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.issatsousse2.adapter.StudentsListAdapter;
import com.example.issatsousse2.model.Student;

public class ListStudentsActivity extends Activity implements
		OnItemClickListener, OnClickListener, OnItemLongClickListener {

	private ListView listView;
	private Button addStudentButton;
	private Button backButton;

	private StudentsListAdapter adapter;
	private List<Student> data = new ArrayList<Student>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_groups_layout);

		listView = (ListView) findViewById(R.id.list_view);
		addStudentButton = (Button) findViewById(R.id.addStudentButton);
		backButton = (Button) findViewById(R.id.backButton);

		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		addStudentButton.setOnClickListener(this);
		backButton.setOnClickListener(this);

		// Create Adapter to adapt data on listView
		adapter = new StudentsListAdapter(getApplicationContext(), data);
		listView.setAdapter(adapter);

		// populate data and refresh listview
		data.addAll(populateData());
		adapter.notifyDataSetChanged();
	}

	/**
	 * Populate list of students
	 * 
	 * @return
	 */
	private List<Student> populateData() {
		List<Student> result = new ArrayList<Student>();

		for (int i = 0; i < 3; i++) {
			Student student = new Student();
			student.setId(i);
			student.setName("Name-" + i);
			student.setSurname("Surname-" + i);
			student.setAge(20);

			result.add(student);
		}

		return result;
	}

	/**
	 * Add Student to data and refresh listview
	 */
	private void addStudent() {
		Student student = new Student();
		student.setId(data.size());
		student.setName("Name-" + data.size());
		student.setSurname("Surname-" + data.size());
		student.setAge(20);

		// add student and refresh listview
		data.add(student);
		adapter.notifyDataSetChanged();
	}

	/**
	 * Remove Student from data and refresh listview
	 * 
	 * @param student
	 */
	private void removeStudent(Student student) {
		data.remove(student);
		adapter.notifyDataSetChanged();
	}

	/**
	 * Show Remove Dialog
	 * 
	 * @param student
	 */
	private void showRemoveConfirmationDialog(final Student student) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setTitle("Suppression d'un étudiant")
				.setMessage("Voulez-vous vraiment supprimer?")
				.setCancelable(false);

		alertDialogBuilder.setPositiveButton("Oui",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						removeStudent(student);
					}
				});

		alertDialogBuilder.setNegativeButton("Non", null);

		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Toast.makeText(
				this,
				String.format("Student item at position %s is clicked",
						position), Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		showRemoveConfirmationDialog(data.get(position));
		return true;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.addStudentButton:
			addStudent();
			break;
		case R.id.backButton:
			finish();
		default:
			break;
		}
	}
}
