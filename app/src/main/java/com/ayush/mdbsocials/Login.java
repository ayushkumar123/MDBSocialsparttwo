package com.ayush.mdbsocials;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity implements View.OnClickListener {

    static FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        FirebaseAuth.AuthStateListener mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d("User signed in", "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d("User signed out", "onAuthStateChanged:signed_out");
                }
            }
        };

        Button logBut = findViewById(R.id.logbut);
        logBut.setOnClickListener(this);

        Button signupBut = findViewById(R.id.signupbut);
        signupBut.setOnClickListener(this);


    }

    /**
     * This function shows what happens when a user attempts to login and the possible end results
     */
    private void attemptLogin() {
        String email = ((EditText) findViewById(R.id.logintext)).getText().toString();
        String password = ((EditText) findViewById(R.id.passwordtext)).getText().toString();
        if (!email.equals("") && !password.equals("")) {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Sign in", "signInWithEmail:onComplete:" + task.isSuccessful());
                            startActivity(new Intent(Login.this, ListActivity.class));
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Log.w("Sign in Failed", "signInWithEmail:failed", task.getException());
                                Toast.makeText(Login.this, "sign in failed", Toast.LENGTH_SHORT).show();
                            } else {
                                startActivity(new Intent(Login.this, ListActivity.class));
                            }
                        }
                    });
        }
    }

    /**
     * This function shows what happens when a user attempts to sign up and the possible end results
     */
    private void attemptSignup() {
        String email = ((EditText) findViewById(R.id.logintext)).getText().toString();
        final String password = ((EditText) findViewById(R.id.passwordtext)).getText().toString();

        if (!email.equals("") && !password.equals("")) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("Complete", "createUserWithEmail:onComplete:" + task.isSuccessful());

                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful() || password.length() < 6) {
                                Toast.makeText(Login.this, "failed Sign up, remember, password needs to be 6 characters or greater", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Login.this, "Signup Successful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, ListActivity.class));
                            }
                        }
                    });
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logbut:
                attemptLogin();
            case R.id.signupbut:
                attemptSignup();
        }
    }
}