package com.example.todolist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<TodoItem>{
	private ArrayList<TodoItem> thelist=new ArrayList<TodoItem>();
	private Context context;
    //private static HashMap<Integer,Boolean> isSelected;  

	
	public ListAdapter(Context context,ArrayList<TodoItem> objects) {
		super(context, R.layout.listitem, objects);
		this.context=context;
		this.thelist=objects;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		/*
		View rowView = null;
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		if (convertView!=null){
			rowView=convertView;
		}
		else{
			convertView = inflater.inflate(R.layout.listitem, parent, false);
		}
		*/
		LayoutInflater inflater = ((Activity)context).getLayoutInflater();
		convertView = inflater.inflate(R.layout.listitem, parent, false);

		TextView name = (TextView) convertView.findViewById(R.id.testView1);
		CheckBox cb= (CheckBox) convertView.findViewById(R.id.checkBox1);
		name.setText(thelist.get(position).getBody());
		cb.setOnCheckedChangeListener(null);
		//check.setOnCheckedChangeListener(new TodoCheckboxListener(position));

		cb.setChecked(thelist.get(position).getChecked());
		cb.setOnCheckedChangeListener(new TodoCheckboxListerner(position));
		return convertView;
	}
	public class TodoCheckboxListerner implements OnCheckedChangeListener{
		int m_position;
		public TodoCheckboxListerner(int position){
			m_position=position;
		}
		public void onCheckedChanged(CompoundButton buttonView, boolean checked){
			thelist.get(m_position).setCheck(checked);
		}

		
	}
	

}
