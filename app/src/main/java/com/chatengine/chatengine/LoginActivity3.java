package com.chatengine.chatengine;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

public class LoginActivity3 extends AppCompatActivity {

    EditText email;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login3);

        email = (EditText) findViewById(R.id.username);
        continueButton = (Button) findViewById(R.id.continueButton);
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userText = email.getText().toString();
                if (userText.equals("Enter username")) {
                    email.setText("");
                    email.setTextColor(Color.BLACK);
                }
            }
        });
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().equals("")){
                    String res = JDBCConnector.checkUser(email.getText().toString());
                    if(res.equals("User already Exists!")){
                        Toast.makeText(getApplicationContext(),"You are already registered! Redirecting to login page!",Toast.LENGTH_LONG).show();
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                                startActivity(intent);
                                finish();
                                System.exit(0);
                            }
                        },4000);
                    }else if(res.equals("New User!")){
                        Intent intent = new Intent(getApplicationContext(),LoginActivity4.class);
                        intent.putExtra("email",email.getText().toString());
                        startActivity(intent);
                    }else if(res.equals("Query Unsuccessful! - Exception Caught")){
                        Toast.makeText(getApplicationContext(),"Oops! There was a problem while connecting... Please try again",Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please enter Email-Id!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
