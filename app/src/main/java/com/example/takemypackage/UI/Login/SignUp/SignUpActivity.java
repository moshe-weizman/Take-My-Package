package com.example.takemypackage.UI.Login.SignUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.takemypackage.Data.MembersFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {
    private EditText editTextPIN, editTextPhone, editTextAddress, editTextEmail, editTextFirstName, editTextLastName;
    private Button buttonSignUp;
    private Member member;
    private LoadingDialog loadingDialog;
    private FirebaseAuth mAuth;
    private ImageView urlImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        init();
        urlImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
        buttonSignUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                member = new Member(editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                        editTextAddress.getText().toString(), editTextPhone.getText().toString(),
                        editTextEmail.getText().toString(), editTextPIN.getText().toString());
                register(member.getEmail(), member.getPassword());
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
            });

        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Error \n", Toast.LENGTH_LONG).show();
        }
    }

    private void init() {
        urlImageView = findViewById(R.id.urlImageView);
        loadingDialog = new LoadingDialog(SignUpActivity.this);
        editTextPIN = findViewById(R.id.editTextPIN);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        buttonSignUp = findViewById(R.id.buttonSignUp);
        mAuth = FirebaseAuth.getInstance();
        member = new Member();
    }

    private void register(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success
                    Toast.makeText(getBaseContext(), "Authentication succeeded ", Toast.LENGTH_LONG).show();
                    addImage();
                    addMember();
                    loadingDialog.dismissDialog();
                } else {
                    // If sign in fails, display a message to the user.
                    loadingDialog.dismissDialog();
                    Toast.makeText(getBaseContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addImage() {
        member.setImageLocalUri((Uri) urlImageView.getTag());
        MembersFirebaseManager.addImageMember(member, new MembersFirebaseManager.Action<String>() {
            @Override
            public void onSuccess(String obj) {
                loadingDialog.dismissDialog();
                Toast.makeText(getBaseContext(), "Upload was successful", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Exception exception) {
                loadingDialog.dismissDialog();
                Toast.makeText(getBaseContext(), "Upload was failed ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            urlImageView.setImageURI(data.getData());
            urlImageView.setTag(data.getData());
        }
    }
}

