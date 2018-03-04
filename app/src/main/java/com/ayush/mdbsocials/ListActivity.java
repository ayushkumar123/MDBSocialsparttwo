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
    private final ArrayList<Message> socials = new ArrayList<>();
    private final ListAdapter adapter = new ListAdapter(this, socials);
    private final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("/socials");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        RecyclerView recyclerAdapter = findViewById(R.id.recyclerView);
        recyclerAdapter.setLayoutManager(new LinearLayoutManager(this));
        recyclerAdapter.setAdapter(adapter);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                socials.clear();
                for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                    Message m = dataSnapshot2.getValue(Message.class);
                    m.id = dataSnapshot2.getKey();
                    socials.add(m);
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

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NewMessageActivity.class);
                startActivity(intent);
            }
        });
    }
}