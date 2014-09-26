package com.example.as1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/* it get to a new layout and ask for the email address
 * the text will be loaded from the email file 
 */
public class SendEmail extends Activity{	
    private EditText address;
	private static final String FILEEMAIL = "file4.sav";
	private FileDataManager dataManager;
	

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);
		dataManager = new FileDataManager(this);
        
    }
    public void send(View view){
    	String theText="";
    	theText=dataManager.read(FILEEMAIL);

        address   = (EditText)findViewById(R.id.emailAddress);

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{address.getText().toString()}); // recipients
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Selected Todo items from the TODO list application");
        emailIntent.putExtra(Intent.EXTRA_TEXT, theText);
     
        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SendEmail.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        } 
		SendEmail.this.finish();

    }

}
