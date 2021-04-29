package com.example.kfbraille;
 
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
 
public class Login extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        final EditText login = (EditText)findViewById(R.id.editText1);
        final CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox1);
        final Button mainTaskActivity =(Button)findViewById(R.id.button1);
        mainTaskActivity.setOnClickListener(new View.OnClickListener() {
 
            @Override
            public void onClick(View view) {
            	String userid = login.getText().toString();
                final Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("userid",userid);
                startActivity(i);
            }
        });
        
}}
