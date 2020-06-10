package com.example.takemypackage.UI.Login.LoginActivity;

import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.takemypackage.R;

public class LoginActivity extends AppCompatActivity {

    private EditText editTextPhone;
    private Button btnSendNumPhone;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        editTextPhone=findViewById(R.id.editTextPhone);
        btnSendNumPhone=findViewById(R.id.btnSendNumPhone);

        btnSendNumPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SmsManager smgr = SmsManager.getDefault();
                smgr.sendTextMessage(editTextPhone.getText().toString(),null,"your code: ",null,null);
            }
        });


    }
}
