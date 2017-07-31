package com.chatengine.chatengine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LoginActivity extends AppCompatActivity {

    RadioGroup accountGroup;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        accountGroup = (RadioGroup) findViewById(R.id.registrationGroup);
        continueButton = (Button) findViewById(R.id.continueButton);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId =  accountGroup.getCheckedRadioButtonId();
                Log.i("cE",String.valueOf(selectedId));
                if(selectedId==R.id.havingAccount){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity2.class);
                    startActivity(intent);
                }else if(selectedId==R.id.noAccount){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity3.class);
                    startActivity(intent);
                }
            }
        });

    }
}
