package com.ayush.mdbsocials;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;



public class ListActivity extends AppCompatActivity {
    final ArrayList<Message> socials = new ArrayList<>();
    final ListAdapter adapter = new ListAdapter(this, socials);
    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/socials");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerAdapter = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerAdapter.setLayoutManager(new LinearLayoutManager(this));


        //Part 2: implement getList
        //Question 1: add Firebase Realtime Database to your project
        recyclerAdapter.setAdapter(adapter);
        //Question 2: initialize the messages based on what is in the database
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                socials.clear();
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                    socials.add(dataSnapshot2.getValue(Message.class));
                }
                Log.d("onDataChange",String.valueOf(socials.size()));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("Failed", "Failed to read value.", error.toException());
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewMessageActivity.class);
                startActivity(intent);
            }
        });

        //Question 3: add an event listener for the children of the ref, and make it such that
        // every time a message is added, it creates a new message, adds it to messages and updates
        // the UI

        //Next part in NewMessageActivity
    }
}