package com.chatengine.chatengine;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.net.URI;

public class ImageConfirmActivity extends AppCompatActivity {

    ImageView selectedImage;
    ImageView send,delete;
    EditText desc;
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_confirm);
        String path = getIntent().getExtras().getString("path");
        email = getIntent().getExtras().getString("email");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RelativeLayout rl = (RelativeLayout) toolbar.findViewById(R.id.toolbar_rl);
        selectedImage = (ImageView) findViewById(R.id.selected_image);
        send = (ImageView) findViewById(R.id.send);
        delete = (ImageView) rl.findViewById(R.id.delete);
        desc = (EditText) findViewById(R.id.imgDesc);
        selectedImage.setImageBitmap(BitmapFactory.decodeFile(path));
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),ImageConfirmActivity.class);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
                System.exit(0);
            }
        });
    }
}
