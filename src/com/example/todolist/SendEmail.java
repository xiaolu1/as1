package com.example.todolist;


import java.io.FileInputStream;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class SendEmail extends ActionBarActivity{	
    EditText address;
	private static final String FILEEMAIL = "file4.sav";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
        
    }
    public void send(View view){
    	String theText="";
        try {  
            FileInputStream inputStream = openFileInput(FILEEMAIL);  
            byte[] b = new byte[inputStream.available()];  
            inputStream.read(b);  
            theText= new String(b);  
        } catch (Exception e) {  
        }  

        address   = (EditText)findViewById(R.id.emailAddress);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        // The intent does not have a URI, so declare the "text/plain" MIME type
        emailIntent.setType("message/rfc822");

        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address.getText().toString()}); // recipients
        //emailIntent.setData(Uri.parse(emailid.toString()));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, theText);
        //emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("content://path/to/email/at
     
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendEmail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        } 
		SendEmail.this.finish();

    }

}
