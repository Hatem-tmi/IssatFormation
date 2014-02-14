package com.example.issatsousse2.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.issatsousse2.R;
import com.example.issatsousse2.model.Student;

/**
 * Students List Adapter
 * 
 * @author "Hatem Toumi"
 */
public class StudentsListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<Student> data;

	public StudentsListAdapter(Context context, List<Student> data) {
		this.data = data;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (data != null)
			return data.size();
		else
			return 0;
	}

	@Override
	public Object getItem(int position) {
		if (data != null)
			return data.get(position);
		else
			return null;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		Student student = data.get(position);

		if (convertView == null) {
			convertView = inflater
					.inflate(R.layout.student_item, parent, false);

			viewHolder = new ViewHolder();
			viewHolder.label = (TextView) convertView.findViewById(R.id.label);
			viewHolder.studentName = (TextView) convertView
					.findViewById(R.id.studentName);
			viewHolder.studentSurname = (TextView) convertView
					.findViewById(R.id.studentSurname);
			viewHolder.studentAge = (TextView) convertView
					.findViewById(R.id.studentAge);

			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.label.setText("Étudiant d'id: " + student.getId());
		viewHolder.studentName.setText("Nom: " + student.getName());
		viewHolder.studentSurname.setText("Prénom: " + student.getSurname());
		viewHolder.studentAge.setText("Age: " + student.getAge());

		return convertView;
	}

	private class ViewHolder {
		TextView label;
		TextView studentName;
		TextView studentSurname;
		TextView studentAge;
	}
}
