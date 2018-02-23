package com.ayush.mdbsocials;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class NewSocial extends AppCompatActivity {
    public String eventName;
    public String eventDate;
    public String eventDesc;
    public int interestedCount;
    private ArrayList<Message> data;
    public int counter = 1;
    private boolean countcheck = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_social);

        Message social = (Message) getIntent().getSerializableExtra("socials");
        TextView name = findViewById(R.id.textView2);
        TextView date = findViewById(R.id.textView3);
        //ImageView pic = findViewById(imageView);
        TextView desc = findViewById(R.id.textView4);
        ImageView pic = findViewById(R.id.imageView);
        final Button interestedButton = findViewById(R.id.interestedbutton);
        TextView counted = findViewById(interestedCount);
        interestedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checker();
                buttonchange();
                count();

            }
        });

        final Button backbutton = findViewById(R.id.button);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(NewSocial.this, ListActivity.class));
            }
        });

        name.setText("Name: " + social.getName());
        date.setText("Date: " + social.getDate());
        desc.setText("Description: " + social.getDescription());
        Glide.with(getApplicationContext()).load(social.getFirebaseImageUrl()).into(pic);

    }



    public boolean checker() {
        if (countcheck == false) {
            countcheck = true;

            return countcheck;
        } else
            countcheck = false;
        return countcheck;
    }

    public void buttonchange() {
        Button interestedButton = findViewById(R.id.button);
        if (checker() == true) {
            interestedButton.setBackgroundColor(500);
        } else if (checker() == false) {
            interestedButton.setBackgroundColor(000);
            }


    }

    public void count() {
        TextView counted = findViewById(interestedCount);
        int counter = 1;
        if (countcheck == true) {
            counter = counter + 1;

        } else {
            counter = counter;


        }
    }
}


//    method is interested
//if false
//set button to white
//if true
//set button to grey




