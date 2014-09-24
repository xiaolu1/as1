package com.example.todolist;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.support.v7.app.ActionBarActivity;
import android.widget.EditText;


public class SRFile extends ActionBarActivity{
	private static final String FILETEXT = "file3.sav";

    public String read() {

        try {  
            FileInputStream inputStream = openFileInput(FILETEXT);  
            byte[] b = new byte[inputStream.available()];  
            inputStream.read(b);  
            return new String(b);  
        } catch (Exception e) {  
        }  
        return null;  
    }  
  
    public void write() {  
    	String content="asdgasga";
        try {  
            FileOutputStream fos = openFileOutput(FILETEXT, MODE_PRIVATE);  
            fos.write(content.getBytes());  
            fos.close();  
        } catch (Exception e) {  
        }  
    }  
}
