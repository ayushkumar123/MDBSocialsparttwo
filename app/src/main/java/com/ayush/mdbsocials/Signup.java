package com.ayush.mdbsocials;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Signup extends AppCompatActivity {
    public String userPass;
    public String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        findViewById(R.id.signbut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                checkUser();
                checkPass();
            }
        });
    }

    private void checkUser() {
        EditText userText = findViewById(R.id.usertext);
        if (userText.getText() == null || userText.getText().toString().length() == 0) {
            userText.setError("Please enter an email!");
        } else {
            userEmail = userText.getText().toString();
        }
    }

    private void checkPass() {
        EditText passText = findViewById(R.id.passtext);
        if (passText.getText() == null || passText.getText().toString().length() < 6) {
            passText.setError("Please create a stronger password");
        } else {
            userPass = passText.getText().toString();
        }
    }
}
