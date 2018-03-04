package com.ayush.mdbsocials;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.IOException;

public class NewMessageActivity extends AppCompatActivity implements View.OnClickListener {


    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static FirebaseUser mUser;

    private String eventName;
    private String eventDate;
    private String eventDesc;
    DatabaseReference myRef = database.getReference("socials");

    private Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_message);
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        final Button button = findViewById(R.id.button);
        button.setOnClickListener(this);
        final ImageView image = findViewById(R.id.imageView);
        image.setOnClickListener(this);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        final Button backbutton = findViewById(R.id.button2);
        backbutton.setOnClickListener(this);
    }

    /**
     * This function checks if the Name text is filled and if it is, stores the information.
     */
    private boolean checkName() {
        EditText nameText = findViewById(R.id.nametext);
        if (nameText.getText() == null || nameText.getText().toString().length() == 0) {
            nameText.setError("Please enter name!");
            return false;
        } else {
            eventName = nameText.getText().toString();
            return true;
        }
    }

    /**
     * This function checks if the Date text is filled and if it is, stores the information.
     */
    private boolean checkDate() {
        EditText dateText = findViewById(R.id.datetext);
        if (dateText.getText() == null || dateText.getText().toString().length() == 0) {
            dateText.setError("Please enter date!");
            return false;
        } else {
            eventDate = dateText.getText().toString();
            return true;
        }
    }

    /**
     * This function checks if the Description text is filled and if it is, stores the information.
     */
    private boolean checkDesc() {
        EditText descText = findViewById(R.id.desctext);
        if (descText.getText() == null || descText.getText().toString().length() == 0) {
            descText.setError("Please enter description!");
            return false;
        } else {
            eventDesc = descText.getText().toString();
            return true;
        }
    }

    /**
     * This function stores the image selected by the user.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ((ImageView) findViewById(R.id.imageView)).setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.data = data;
        }


    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.button2:
                startActivity(new Intent(NewMessageActivity.this, ListActivity.class));
                break;
            case R.id.imageView:
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.button:
                checkName();
                checkDate();
                checkDesc();
                if (checkDesc() && checkDate() && checkName()) {
                    final DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                    final String key = ref.child("socials").push().getKey();
                    final StorageReference storageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mdbsocials-10f4e.appspot.com");
                    StorageReference riversRef = storageRef.child(key + ".png");
                    riversRef.putFile(data.getData()).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(NewMessageActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Message social = new Message(eventName, eventDesc, mUser.getEmail() ,eventDate, taskSnapshot.getDownloadUrl().toString(), 0);
                            ref.child("socials").child(key).setValue(social);
                        }
                    });

                    startActivity(new Intent(NewMessageActivity.this, ListActivity.class));
                }
                break;

            }

        }
    }

