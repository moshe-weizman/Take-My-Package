package com.example.takemypackage.UI.Login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.takemypackage.Data.MembersFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.Login.SignUp.SignUpActivity;
import com.example.takemypackage.UI.Login.VerifyMobileActivity.VerifyMobileActivity;
import com.example.takemypackage.UI.MainActivity.MainActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private TextView textViewLoginEmail, textViewSignUp;
    private EditText editTextPhoneLogIn, editTextEmailLogIn, editTextPIN;
    private Button btnLogIn;
    private String password;
    //    Random rand = new Random();
//    public static final String EXTRA_PIN = "com.example.takemypackage.login.pin";
//    int randPin = rand.nextInt(9999 - 1000) + 1000;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();


        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        editTextPIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void init() {
        editTextPIN = findViewById(R.id.editTextPIN);
        textViewLoginEmail = findViewById(R.id.textViewLoginEmail);
        editTextPhoneLogIn = findViewById(R.id.editTextPhoneLogIn);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        // editTextEmailLogIn = findViewById(R.id.editTextEmailLogIn);
        btnLogIn = findViewById(R.id.btnLogIn);
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
//    }


//    public void sendMessage(View view) {
//        btnSendNumPhone=findViewById(R.id.btnSendNumPhone);
//        final SmsManager smsManager  = SmsManager.getDefault();
//        //smsManager .sendTextMessage(editTextPhone.getText().toString() ,null,"Your code is: " ,null,null);
//        Intent intent = new Intent(LoginActivity.this, VerifyMobileActivity.class);
//        intent.putExtra(EXTRA_PIN, randPin);
//        startActivity(intent);
//    }


}
