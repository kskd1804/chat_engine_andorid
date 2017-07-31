package com.chatengine.chatengine;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity2 extends AppCompatActivity {

    EditText username,password;
    Button continueButton;
    TextView notRegistered;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        continueButton = (Button) findViewById(R.id.continueButton);
        notRegistered = (TextView) findViewById(R.id.notOncE);
        notRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),LoginActivity3.class);
                startActivity(intent);
            }
        });
        dbHandler = new DBHandler(getApplicationContext(),null,null,1);
        final String userText = username.getText().toString();
        final String passText = password.getText().toString();
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!username.getText().toString().equals("") && !password.getText().toString().equals("")){
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
                    Log.i("cE",hashtext);
                    final User u = new User("","",username.getText().toString(),hashtext,"","","","","","","","");
                    final ProgressDialog progressDialog = new ProgressDialog(v.getContext());
                    progressDialog.setIndeterminate(true);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Logging In...Please wait");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            User user = JDBCConnector.getUser(u);
                            if(user!=null){
                                if(user.getEmail().equals(u.getEmail())){
                                    if(dbHandler.addUser(user)>0){
                                        Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                                        startActivity(intent);
                                        finish();
                                        System.exit(0);
                                    }else{
                                        runOnUiThread(makeToast("Error: 001 - Linking error!"));
                                    }
                                }
                            }else{
                                runOnUiThread(makeToast("Invalid Username/Password!"));
                            }
                            progressDialog.dismiss();
                        }
                    }).start();
                }else{
                    Toast.makeText(getApplicationContext(),"Please enter username and password!",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private Runnable makeToast(final String text){
        Runnable r = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
            }
        };
        return r;
    }
}
