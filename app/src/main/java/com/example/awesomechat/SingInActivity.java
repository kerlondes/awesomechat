package com.example.awesomechat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SingInActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    private static final String TAG = "SingInActivity";
    private EditText emailEditText;
    private EditText passwordEditText;
    private EditText repeatPasswordEditText;
    private EditText nameEditText;
    private TextView toggleLoginSingUpTextView;
    private Button loginSingUpButton;
    private boolean loginModeActive;
    FirebaseDatabase database;
    DatabaseReference usersDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_in);

        auth= FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        usersDatabaseReference = database.getReference().child("users");

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        repeatPasswordEditText = findViewById(R.id.repeatPasswordEditText);
        nameEditText = findViewById(R.id.nameEditText);
        toggleLoginSingUpTextView = findViewById(R.id.toggleLoginSingUpTextView);
        loginSingUpButton = findViewById(R.id.loginSingUpButton);

        loginSingUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginSingUpUser(emailEditText.getText().toString().trim(),
                        passwordEditText.getText().toString().trim());
            }
        });
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(SingInActivity.this, UserListActivity.class));
        }
    }

    private void loginSingUpUser(String email,String password){
        if (loginModeActive){
            if(passwordEditText.getText().toString().trim().length()<7){
                Toast.makeText(this,"Passwords must be at least 7 characters",
                        Toast.LENGTH_SHORT).show();
            }else if(emailEditText.getText().toString().trim().equals("")){
                Toast.makeText(this,"Please input yore email",
                        Toast.LENGTH_SHORT).show();
            }else{
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    //updateUI(user);
                                    Intent intent = new Intent(SingInActivity.this,
                                            UserListActivity.class);
                                    intent.putExtra("userName", nameEditText.getText().toString().trim());
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    Toast.makeText(SingInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        }else{
            if (!passwordEditText.getText().toString().trim().equals(
                    repeatPasswordEditText.getText().toString().trim()
            )){
                Toast.makeText(this,"Passwords don't match", Toast.LENGTH_SHORT).show();
            }else if(passwordEditText.getText().toString().trim().length()<7){
                Toast.makeText(this,"Passwords must be at least 7 characters",
                        Toast.LENGTH_SHORT).show();
            }else if(emailEditText.getText().toString().trim().equals("")){
                Toast.makeText(this,"Please input yore email",
                        Toast.LENGTH_SHORT).show();
            }else{
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    FirebaseUser user = auth.getCurrentUser();
                                    CreateUser(user);
                                    //updateUI(user);
                                    Intent intent = new Intent(SingInActivity.this,
                                            UserListActivity.class);
                                    intent.putExtra("userName", nameEditText.getText().toString().trim());
                                    startActivity(intent);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SingInActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }
                            }
                        });
            }
        }
    }

    private void CreateUser(FirebaseUser firebaseUser) {
        User user = new User();
        user.setId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(nameEditText.getText().toString().trim());
        usersDatabaseReference.push().setValue(user);
    }

    public void toggleLoginMode(View view) {
        if (loginModeActive){
            loginModeActive = false;
            loginSingUpButton.setText("Sing Up");
            toggleLoginSingUpTextView.setText("Or, log in");
            repeatPasswordEditText.setVisibility(View.VISIBLE);
        }else{
            loginModeActive = true;
            loginSingUpButton.setText("Log In");
            toggleLoginSingUpTextView.setText("Or, sing in");
            repeatPasswordEditText.setVisibility(View.GONE);
        }
    }
}