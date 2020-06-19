package com.example.takemypackage.UI.Login.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.takemypackage.Data.MembersFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.Login.LoginActivity.LoginActivity;
import com.example.takemypackage.UI.MainActivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextPIN, editTextPhone, editTextAddress, editTextEmail, editTextFirstName, editTextLastName;
    private Button buttonSignUp;
    private ProgressBar progressBarAddMember;
    Member member;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                member = new Member(editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                        editTextAddress.getText().toString(), editTextPhone.getText().toString(),
                        editTextEmail.getText().toString(), editTextPIN.getText().toString());
                register(member.getEmail(), member.getPassword());

//                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                startActivity(intent);
            }


        });

    }

    private void addMember() {
        try {
            MembersFirebaseManager.addMemberToFirebase(member, new MembersFirebaseManager.Action<String>() {

                @Override
                public void onSuccess(String obj) {
                    Toast.makeText(getBaseContext(), "welcome " + obj, Toast.LENGTH_LONG).show();
                }
                @Override
                public void onFailure(Exception exception) {
                    Toast.makeText(getBaseContext(), "Error \n", Toast.LENGTH_LONG).show();
                }
                @Override
                public void onProgress(String status, double percent) {
                    if (percent != 100)
                        progressBarAddMember.setProgress((int) percent*100);
                }
            });

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error \n", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        progressBarAddMember = findViewById(R.id.progressBarAddMember);
        editTextPIN = findViewById(R.id.editTextPIN);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        mAuth = FirebaseAuth.getInstance();
    }

    private void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success
                    Toast.makeText(getBaseContext(), "Authentication succeeded ", Toast.LENGTH_LONG).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                    addMember();

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(getBaseContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}

