package com.example.takemypackage.UI.Login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.takemypackage.Data.MembersFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.Login.VerifyMobileActivity.VerifyMobileActivity;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Random;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextPhone, editTextAddress, editTextEmail, editTextFirstName, editTextLastName, editTextPhoneLogIn, editTextEmailLogIn;
    private Button buttonSignUp, btnLogIn;
    Random rand = new Random();
    public static final String EXTRA_PIN = "com.example.takemypackage.login.pin";
    int randPin = rand.nextInt(9999 - 1000) + 1000;
    Member member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                member = new Member(editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                        editTextAddress.getText().toString(), editTextPhone.getText().toString(),
                        editTextEmail.getText().toString());
                Task<Void> task = MembersFirebaseManager.addMemberToFirebase(member);
                Intent intent = new Intent(LoginActivity.this, VerifyMobileActivity.class);
                intent.putExtra(EXTRA_PIN, randPin);
                startActivity(intent);
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // final SmsManager smsManager  = SmsManager.getDefault();
                //   smsManager .sendTextMessage(editTextPhone.getText().toString(),null,"Your code is: " ,null,null);
                // Intent intent = new Intent(LoginActivity.this, VerifyMobileActivity.class);
                Intent intent = new Intent(LoginActivity.this, VerifyMobileActivity.class);
                intent.putExtra(EXTRA_PIN, randPin);
                startActivity(intent);
            }
        });


    }

    private void init() {
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhoneLogIn = findViewById(R.id.editTextPhoneLogIn);
        editTextEmailLogIn = findViewById(R.id.editTextEmailLogIn);
        buttonSignUp = findViewById(R.id.buttonSignUp);
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
