/*
This application lets you to save a list of todo items and manipulate them.

Copyright (C) 2014 Xiaolu Wang

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.

*/
package com.example.as1;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

/* this is the main activity that relate to the main page of the app

 */
public class TodoActivity extends Activity {


	private static final String FILETODO = "file1.sav";
	private static final String FILEARCH = "file2.sav";
	private static final String FILETEXT = "file3.sav";
	private static final String FILEEMAIL = "file4.sav";

	private ListView listview;
	private ArrayList<TodoItem> todoItems;
	private ArrayList<TodoItem> archivedItems;
	private EditText bodyText;
	private ListAdapter adaptertodo;
	private ListAdapter adapterAr;
	private int totalTodoItem;
	private int totalArchItem;
	private ArrayList<String> theSummary;
	private ArrayAdapter<String> summaryViewAdapter;
	private FileDataManager dataManager;

  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        bodyText = (EditText)findViewById(R.id.body);
		listview = (ListView) findViewById(R.id.todoListView);
		dataManager = new FileDataManager(this);

    }
	protected void onStart(){
		super.onStart();
		todoItems=new ArrayList<TodoItem>();
		archivedItems=new ArrayList<TodoItem>();
		theSummary=new ArrayList<String>();
		todoItems=dataManager.loadItems(FILETODO);
		totalTodoItem=todoItems.size();
		archivedItems=dataManager.loadItems(FILEARCH);
		totalArchItem=archivedItems.size();
		summaryViewAdapter = new ArrayAdapter<String>(this, R.layout.summarylist, theSummary);
		adaptertodo = new ListAdapter(this, todoItems);
		adapterAr = new ListAdapter(this, archivedItems);
		String theText=dataManager.read(FILETEXT);
		bodyText.setText(theText);
		listview.setAdapter(adaptertodo);
	}	  
	
	/*the method will get the data by checking through the todoItems and archivedItems*/
	public void setsummary(){
		String temp="";
		int j;
		int checkTodo=0;
		int checkArItem=0;

	    for (j=0;j<totalTodoItem;j++){
	    	if (todoItems.get(j).getChecked()){
	    		checkTodo+=1;
	    	}
	    }
	    for (j=0;j<totalArchItem;j++){
	    	if (archivedItems.get(j).getChecked()){
	    			checkArItem+=1;
	    	}
	    }
	    theSummary.clear();
    	temp="Total items: "+(totalTodoItem+totalArchItem);
    	theSummary.add(temp);
    	temp="TODO items: "+(totalTodoItem);
    	theSummary.add(temp);
    	temp="TODO items checked: "+(checkTodo);
    	theSummary.add(temp);
    	temp="TODO items left unchecked: "+(totalTodoItem-checkTodo);
    	theSummary.add(temp);
    	temp="Archived TODO items: "+(totalArchItem);
    	theSummary.add(temp);
    	temp="Checked archived TODO items: "+(checkArItem);
    	theSummary.add(temp);
    	temp="Unchecked archived TODO items: "+(totalArchItem-checkArItem);
    	theSummary.add(temp);
	}
	
	
	/*the methods below is defined as the functions for the buttons*/

    public void save(View view){
		String text = bodyText.getText().toString();
		TodoItem a = new TodoItem(text);
		todoItems.add(a);
		adaptertodo.notifyDataSetChanged();
		bodyText.setText("");
		totalTodoItem+=1;
		dataManager.saveItems(todoItems, FILETODO);

    }
    
    public void delete(View view){
    	int i;
    	for (i=0;i<totalTodoItem;i++){
    		if (todoItems.get(i).getChecked()){
    			todoItems.remove(i);
    			totalTodoItem-=1;
    			i-=1;
    		}
    	}
    	for (i=0;i<totalArchItem;i++){
    		if (archivedItems.get(i).getChecked()){
    			archivedItems.remove(i);
    			totalArchItem-=1;
    			i-=1;
    		}
    	}
		dataManager.saveItems(todoItems, FILETODO);
		dataManager.saveItems(archivedItems, FILEARCH);
    	adaptertodo.notifyDataSetChanged();
    	adapterAr.notifyDataSetChanged();

    }
    
    public void email(View view){
    	String emailText = "";
    	String temp="";
    	int i;
    	
    	/*set the email to do part to String and save it in the email file*/
    	for (i=0;i<totalTodoItem;i++){
    		if (todoItems.get(i).getChecked()){
    			temp=todoItems.get(i).getBody().replaceAll("\n", "\n                    ");
    			emailText=emailText+"Todo item: "+temp+"\n";
    			
    		}
    	}
    	for (i=0;i<totalArchItem;i++){
    		if (archivedItems.get(i).getChecked()){
    			temp=archivedItems.get(i).getBody().replaceAll("\n", "\n                          ");
    			emailText=emailText+"Archived item: "+temp+"\n";
    		}
    	}
    	dataManager.write(emailText,FILEEMAIL);
		Intent intent = new Intent(TodoActivity.this, SendEmail.class);
		TodoActivity.this.startActivity(intent);

    }
    public void archive(View view){
    	int j;
    	for (j=0;j<totalTodoItem;j++){
    		if (todoItems.get(j).getChecked()){
    			todoItems.get(j).setCheck(false);
    			archivedItems.add(todoItems.get(j));
    			todoItems.remove(j);
    			totalTodoItem-=1;
    			totalArchItem+=1;
    			j-=1;
    		}
    	}
    	for (j=0;j<totalArchItem;j++){
    		if (archivedItems.get(j).getChecked()){
    			archivedItems.get(j).setCheck(false);
    			todoItems.add(archivedItems.get(j));
    			archivedItems.remove(j);
    			totalArchItem-=1;
    			totalTodoItem+=1;
    			j-=1;
    		}
    	}
		dataManager.saveItems(todoItems, FILETODO);
		dataManager.saveItems(archivedItems, FILEARCH);
    	adaptertodo.notifyDataSetChanged();
    	adapterAr.notifyDataSetChanged();
    }    
    public void todoview(View view){
		listview.setAdapter(adaptertodo);
    }
    public void archived(View view){
		listview.setAdapter(adapterAr);
    }
    public void summary(View view){
    	setsummary();
		summaryViewAdapter.notifyDataSetChanged();
		listview.setAdapter(summaryViewAdapter);
    }
	/*the methods for buttons end*/
    
    /*the stop of the activity will save all the data*/
    protected void onStop(){
        super.onStop();
		dataManager.saveItems(todoItems, FILETODO);
		dataManager.saveItems(archivedItems, FILEARCH);
		String text = bodyText.getText().toString();
		dataManager.write(text,FILETEXT);

    }  

}

