package com.example.as1;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;


/* this part is almost copy from our lab3
 * and my FileDataManager will take the filename as an input in the methods
 * so it will save any arraylist to different file
 * and load them from different files
 */
public class FileDataManager {
		
	private Context ctx;
	
	public FileDataManager(Context ctx) {
		this.ctx = ctx;
	}
	
	public ArrayList<TodoItem> loadItems(String filename) {
		ArrayList<TodoItem> lts = new ArrayList<TodoItem>();

		try {
			FileInputStream fis = ctx.openFileInput(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);

			lts = (ArrayList<TodoItem>) ois.readObject();

		} catch (Exception e) {
			Log.i("LonelyTwitter", "Error casting");
			e.printStackTrace();
		} 

		return lts;
	}
	
	public void saveItems(List<TodoItem> lts, String filename) {
		try {
			FileOutputStream fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(lts);
			fos.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String read(String filename) {  
        try {  
            FileInputStream inputStream = ctx.openFileInput(filename);  
            byte[] b = new byte[inputStream.available()];  
            inputStream.read(b);  
            return new String(b);  
        } catch (Exception e) {  
        }  
        return null;  
    }  
  
	public void write(String content,String filename) {  
        try {  
            FileOutputStream fos = ctx.openFileOutput(filename, Context.MODE_PRIVATE);  
            fos.write(content.getBytes());  
            fos.close();  
        } catch (Exception e) {  
        }  
    }  
}

