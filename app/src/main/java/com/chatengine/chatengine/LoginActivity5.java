package com.chatengine.chatengine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginActivity5 extends AppCompatActivity {

    EditText password,confirmPassword;
    Button continueButton;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login5);
        Bundle extras = getIntent().getExtras();
        final String firstname = extras.getString("firstname");
        final String lastname = extras.getString("lastname");
        final String email = extras.getString("email");
        final String mobile = extras.getString("mobile");
        final String dob = extras.getString("dob");
        final String gender = extras.getString("gender");
       dbHandler = new DBHandler(getApplicationContext(),null,null,1);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        continueButton = (Button) findViewById(R.id.continueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!password.getText().toString().equals("Password") && !password.getText().toString().equals("") && !confirmPassword.getText().toString().equals("Confirm Password") && !confirmPassword.getText().toString().equals("")){
                    if(password.getText().toString().equals(confirmPassword.getText().toString())){
                        String pass = password.getText().toString();
                        MessageDigest m = null;
                        try {
                            m = MessageDigest.getInstance("MD5");
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                        m.reset();
                        m.update(pass.getBytes());
                        byte[] digest = m.digest();
                        BigInteger bigInt = new BigInteger(1,digest);
                        String hashtext = bigInt.toString(16);
                        // Now we need to zero pad it if you actually want the full 32 chars.
                        while(hashtext.length() < 32 ){
                            hashtext = "0"+hashtext;
                        }
                        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                        final User u = new User(firstname,lastname,email,hashtext,dob,mobile,gender,df.format(new Date()),email,"online","UserContent/profile_00.jpg",df.format(new Date()));
                        final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                        progressDialog.setIndeterminate(true);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.setMessage("Logging In...Please wait");
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressDialog.show();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String res = JDBCConnector.addUser(u);
                                if(res.equals("Query Successful!")){
                                    if(dbHandler.addUser(u)>0){
                                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                        System.exit(0);
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Error: 001 - Linking error!",Toast.LENGTH_LONG).show();
                                    }
                                }else if(res.equals("Query Unsuccessful!")){
                                    Toast.makeText(getApplicationContext(),"Oops! There was a problem while connecting!...Please try again",Toast.LENGTH_LONG).show();
                                }
                                progressDialog.dismiss();
                            }
                        }).start();
                    }else{
                        Toast.makeText(getApplicationContext(),"Password Mismatch!",Toast.LENGTH_LONG).show();
                        password.setText("");
                        confirmPassword.setText("");
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Please enter the fields before submitting!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
