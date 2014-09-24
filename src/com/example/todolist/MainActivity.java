package com.example.todolist;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {


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

  
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.activity_main);  
        bodyText = (EditText)findViewById(R.id.body);
		listview = (ListView) findViewById(R.id.todoListView);

    }
	protected void onStart(){
		super.onStart();
		todoItems=new ArrayList<TodoItem>();
		archivedItems=new ArrayList<TodoItem>();
		theSummary=new ArrayList<String>();
		totalTodoItem=loadItems(todoItems, FILETODO,totalTodoItem);
		totalArchItem=loadItems(archivedItems, FILEARCH,totalArchItem);
		summaryViewAdapter = new ArrayAdapter<String>(this, R.layout.summarylist, theSummary);
		adaptertodo = new ListAdapter(this, todoItems);
		adapterAr = new ListAdapter(this, archivedItems);
		String theText=read(FILETEXT);
		bodyText.setText(theText);
		listview.setAdapter(adaptertodo);
	}	  

	public void setsummary(){
		String temp="";
		int j;
		int checkTodo=0;
		int checkArItem=0;
		
		theSummary.clear();
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
    	temp="Total items: "+(totalTodoItem+totalArchItem);
    	theSummary.add(temp);
    	temp="TODO items: "+totalTodoItem;
    	theSummary.add(temp);
    	temp="TODO items checked: "+checkTodo;
    	theSummary.add(temp);
    	temp="TODO items left unchecked: "+(totalTodoItem-checkTodo);
    	theSummary.add(temp);
    	temp="Archived TODO items: "+totalArchItem;
    	theSummary.add(temp);
    	temp="Checked archived TODO items: "+checkArItem;
    	theSummary.add(temp);
    	temp="Unchecked archived TODO items: "+(totalArchItem-checkArItem);
    	theSummary.add(temp);
	}
	
    public void save(View view){
		String text = bodyText.getText().toString();
		TodoItem a = new TodoItem(text);
		todoItems.add(a);
		adaptertodo.notifyDataSetChanged();
		bodyText.setText("");
		totalTodoItem+=1;

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
    	adaptertodo.notifyDataSetChanged();
    	adapterAr.notifyDataSetChanged();

    }
    
    public void email(View view){
    	String emailText = "";
    	String temp="";
    	int i;
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
		write(emailText,FILEEMAIL);
		Intent intent = new Intent(MainActivity.this, SendEmail.class);
		MainActivity.this.startActivity(intent);

        //String strTmp="email";  
        //bodyText.setText(strTmp);  
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

	public void saveItems(List<TodoItem> lts, String filename) {
        try {  
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);  
            ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(lts);
            fos.close();  
        } catch (Exception e) {  
        	String ex = e.toString();
            bodyText.setText(ex);
        }  
	}
    public int loadItems(ArrayList<TodoItem> aim, String filename, int theSize) {
		ArrayList<TodoItem> lts = new ArrayList<TodoItem>();
        try {  
            FileInputStream inputStream = openFileInput(filename);  
			ObjectInputStream ois = new ObjectInputStream(inputStream);
			lts = (ArrayList<TodoItem>) ois.readObject();
			inputStream.close();  

        } catch (Exception e) {  
        }
        aim.clear();
        int a;
        theSize=lts.size();
        for (a=0;a<theSize;a++){
        	aim.add(lts.get(a));
        }
        return theSize;
	}
    private String read(String filename) {  
        try {  
            FileInputStream inputStream = openFileInput(filename);  
            byte[] b = new byte[inputStream.available()];  
            inputStream.read(b);  
            return new String(b);  
        } catch (Exception e) {  
        }  
        return null;  
    }  
  
    private void write(String content,String filename) {  
        try {  
            FileOutputStream fos = openFileOutput(filename, MODE_PRIVATE);  
            fos.write(content.getBytes());  
            fos.close();  
        } catch (Exception e) {  
        }  
    }  
    
    protected void onStop(){
        super.onStop();  
		saveItems(todoItems, FILETODO);
		saveItems(archivedItems, FILEARCH);
		String text = bodyText.getText().toString();
		write(text,FILETEXT);
		

    }  

}

