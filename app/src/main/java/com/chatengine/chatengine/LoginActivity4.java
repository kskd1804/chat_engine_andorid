package com.chatengine.chatengine;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class LoginActivity4 extends AppCompatActivity {

    EditText firstname,lastname,mobile,dob;
    RadioGroup genderGroup;
    Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login4);
        final String email = getIntent().getExtras().getString("email");
        firstname = (EditText) findViewById(R.id.firstname);
        lastname = (EditText) findViewById(R.id.lastname);
        mobile = (EditText) findViewById(R.id.mobile);
        dob = (EditText) findViewById(R.id.dob);
        genderGroup = (RadioGroup) findViewById(R.id.genderGroup);
        continueButton = (Button) findViewById(R.id.continueButton);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstname.getText().toString().equals("") && !lastname.getText().toString().equals("") && !mobile.getText().toString().equals("") && !firstname.getText().toString().equals("Firstname") && !lastname.getText().toString().equals("Lastname") && !mobile.getText().toString().equals("Mobile")) {
                    Intent intent = new Intent(getApplicationContext(), LoginActivity5.class);
                    intent.putExtra("firstname", firstname.getText().toString());
                    intent.putExtra("lastname", lastname.getText().toString());
                    intent.putExtra("email", email);
                    intent.putExtra("mobile", mobile.getText().toString());
                    intent.putExtra("dob", dob.getText().toString());
                    int id = genderGroup.getCheckedRadioButtonId();
                    String gender;
                    if (id == R.id.male) gender = "male";
                    else gender = "female";
                    intent.putExtra("gender", gender);
                    startActivity(intent);
                }
            }
        });
    }

}
