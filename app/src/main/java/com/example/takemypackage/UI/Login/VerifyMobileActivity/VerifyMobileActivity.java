package com.example.takemypackage.UI.Login.VerifyMobileActivity;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.takemypackage.R;
import com.example.takemypackage.UI.Login.LoginActivity.LoginActivity;
import com.example.takemypackage.UI.MainActivity.MainActivity;

import java.util.Random;

public class VerifyMobileActivity  extends AppCompatActivity {
    private EditText editTextPhone;
    private Button buttonEnter;
    private EditText editTextPIN;
    Intent myIntent = getIntent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verify_mobile_activity);

        editTextPhone=findViewById(R.id.editTextPhone);
        buttonEnter=findViewById(R.id.buttonEnter);
        editTextPIN=findViewById(R.id.editTextPIN);


        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

    }
}
