package com.example.takemypackage.UI.Login.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takemypackage.Data.MembersFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.Login.LoginActivity.LoginActivity;
import com.example.takemypackage.UI.Login.VerifyMobileActivity.VerifyMobileActivity;
import com.example.takemypackage.UI.MainActivity.MainActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextPIN, editTextPhone, editTextAddress, editTextEmail, editTextFirstName, editTextLastName;
    private Button buttonSignUp;
    Member member;

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
                // try{
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

                    }
                });

//                  } catch {
//                    Toast.makeText(getBaseContext(), "Error \n", Toast.LENGTH_LONG).show();
//                }
                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }


        });

    }

    private void init() {
        editTextPIN = findViewById(R.id.editTextPIN);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonSignUp = findViewById(R.id.buttonSignUp);
    }
}
