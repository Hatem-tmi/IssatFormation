package com.example.issatsousse2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.issatsousse2.adapter.StudentsListAdapter;
import com.example.issatsousse2.model.Student;

public class ListStudentsActivity extends Activity implements
		OnItemClickListener, OnClickListener, OnItemLongClickListener {
	private static final String TAG = ListStudentsActivity.class
			.getSimpleName();
	private static final String WS_URL = "http://issat-formation.site50.net/students";

	private ListView listView;
	private ProgressBar progressBar;
	private Button addStudentButton;
	private Button backButton;
	private Button refreshButton;

	private StudentsListAdapter adapter;
	private List<Student> data = new ArrayList<Student>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_students_layout);

		listView = (ListView) findViewById(R.id.list_view);
		progressBar = (ProgressBar) findViewById(R.id.progressBar);
		addStudentButton = (Button) findViewById(R.id.addStudentButton);
		backButton = (Button) findViewById(R.id.backButton);
		refreshButton = (Button) findViewById(R.id.refreshButton);

		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
		addStudentButton.setOnClickListener(this);
		backButton.setOnClickListener(this);
		refreshButton.setOnClickListener(this);

		// Create Adapter to adapt data on listView
		adapter = new StudentsListAdapter(getApplicationContext(), data);
		listView.setAdapter(adapter);

		// fetch data
		new FetchDataAsynck().execute(WS_URL);
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
		data.add(0, student);
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
		case R.id.refreshButton:
			// fetch data
			new FetchDataAsynck().execute(WS_URL);
			break;
		default:
			break;
		}
	}

	/**
	 * Fetch data AsynckTask
	 * 
	 * @author "Hatem Toumi"
	 * 
	 */
	private class FetchDataAsynck extends
			AsyncTask<String, Void, List<Student>> {

		@Override
		protected void onPreExecute() {
			progressBar.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
		}

		@Override
		protected List<Student> doInBackground(String... params) {
			List<Student> result = new ArrayList<Student>();
			BufferedReader bufferedReader = null;

			try {
				URL urlWS = new URL(params[0]);

				URLConnection urlConnection = urlWS.openConnection();
				bufferedReader = new BufferedReader(new InputStreamReader(
						urlConnection.getInputStream()));

				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = bufferedReader.readLine()) != null)
					sb.append(line);

				JSONArray studentsArray = new JSONArray(sb.toString());
				for (int i = 0; i < studentsArray.length(); i++) {
					JSONObject studentObject = (JSONObject) studentsArray
							.get(i);

					Student student = new Student();
					student.setId(Integer.parseInt(studentObject
							.getString("id")));
					student.setName(studentObject.getString("name"));
					student.setSurname(studentObject.getString("surname"));
					student.setAge(Integer.parseInt(studentObject
							.getString("age")));

					result.add(student);
				}
			} catch (MalformedURLException e) {
				Log.e(TAG, "", e);
			} catch (IOException e) {
				Log.e(TAG, "", e);
			} catch (JSONException e) {
				Log.e(TAG, "", e);
			}

			return result;
		}

		@Override
		protected void onPostExecute(List<Student> result) {
			progressBar.setVisibility(View.GONE);
			listView.setVisibility(View.VISIBLE);

			data.clear();
			data.addAll(result);
			adapter.notifyDataSetChanged();
		}
	}
}
