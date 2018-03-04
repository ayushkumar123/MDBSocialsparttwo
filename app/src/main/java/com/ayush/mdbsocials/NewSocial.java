package com.ayush.mdbsocials;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class NewSocial extends AppCompatActivity implements View.OnClickListener {
    private ArrayList<Message> data;
    private Message social;
    private boolean rsvp = false;
    private TextView rsvpupdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_social);
        rsvpupdate = findViewById(R.id.textView);
        Button interestedButton = findViewById(R.id.interestedbutton);
        social = (Message) getIntent().getSerializableExtra("socials");
        social.id = getIntent().getStringExtra("social id");
        TextView name = findViewById(R.id.textView2);
        TextView date = findViewById(R.id.textView3);
        TextView desc = findViewById(R.id.textView4);
        ImageView pic = findViewById(R.id.imageView);
        interestedButton.setOnClickListener(this);

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("socials").child(social.id).child("num_interested").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                rsvpupdate.setText(dataSnapshot.getValue(Integer.class) + " are interested");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        final Button backbutton = findViewById(R.id.button);
        backbutton.setOnClickListener(this);

        name.setText("Name: " + social.getName());
        date.setText("Date: " + social.getDate());
        desc.setText("Description: " + social.getDescription());
        Glide.with(getApplicationContext()).load(social.getFirebaseImageUrl()).into(pic);

        FirebaseDatabase.getInstance().getReference("users").child(Login.mAuth.getCurrentUser().getUid())
                .child("interestedList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String socialId = child.getValue(String.class);
                    if (Objects.equals(socialId, social.id)) {
                        rsvp = true;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

    /**
     * This function is to keep a tally of how many users are interested in a certain event.
     */
    private void transaction() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        String id = Login.mAuth.getCurrentUser().getUid();
        final DatabaseReference newRef = ref.child("users").child(id).child("interestedList");
        final String socialid = social.id;
        newRef.child(social.id).setValue(social.id);
        ref.child("socials").child(social.id).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Message social = mutableData.getValue(Message.class);
                if (rsvp) {
                    social.num_interested = social.num_interested - 1;
                    newRef.child(socialid).setValue(null);
                    rsvp = false;
                } else {
                    social.num_interested = social.num_interested + 1;
                    newRef.child(socialid).setValue(socialid);
                    rsvp = true;
                }
                mutableData.setValue(social);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                Log.d("bug", "postTransaction:onComplete:" + databaseError);
            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(NewSocial.this, ListActivity.class));
                break;
            case R.id.interestedbutton:
                transaction();
                break;

        }
    }
}




