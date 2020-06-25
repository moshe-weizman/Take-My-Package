package com.example.takemypackage.UI.Login.LoginActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.takemypackage.Data.MembersFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.Login.SignUp.SignUpActivity;
import com.example.takemypackage.UI.MainActivity.MainActivity;
import com.example.takemypackage.Utils.LoadingDialog;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

import static com.example.takemypackage.Data.MembersFirebaseManager.memberRef;

public class LoginActivity extends AppCompatActivity {
    public final static String MEMBER_KEY = "com.example.takemypackage.Entities.Member";
    private LoadingDialog loadingDialog;
    private FirebaseAuth mAuth;
    private TextView textViewSignUp;
    private EditText editTextPhoneLogIn, editTextPIN, editTextEmail;
    private Button btnLogIn;
    Member memberLogin;
    String emailUser;
    String phoneUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                if (!TextUtils.isEmpty(editTextEmail.getText())) {
                    emailUser = editTextEmail.getText().toString();
                    Query queryEmail = memberRef.orderByChild("email").equalTo(emailUser);
                    queryEmail.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            memberLogin = new Member();
                            for (DataSnapshot child : dataSnapshot.getChildren()) {
                                memberLogin = child.getValue(Member.class);
                                memberLogin.setPhone(child.getKey());
                            }
                            if (memberLogin != null)
                                singIn(emailUser, editTextPIN.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "Failed to read value", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    //TODO to change from query to loop (foreach) because the application is fall down when the user insert phone number that not exsist or to do try catch or to do what roni suggested
                    Query query = memberRef.orderByKey().equalTo(editTextPhoneLogIn.getText().toString());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            phoneUser = editTextPhoneLogIn.getText().toString();
                            memberLogin = new Member();
                            memberLogin = dataSnapshot.child(phoneUser).getValue(Member.class);
                            memberLogin.setPhone(phoneUser);
                            if (memberLogin != null)
                                singIn(memberLogin.getEmail(), editTextPIN.getText().toString());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(LoginActivity.this, "Failed to read value", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        loadingDialog = new LoadingDialog(LoginActivity.this);
        editTextPIN = findViewById(R.id.editTextPIN);
        editTextPhoneLogIn = findViewById(R.id.editTextPhoneLogIn);
        textViewSignUp = findViewById(R.id.textViewSignUp);
        editTextEmail = findViewById(R.id.editTextEmail);
        btnLogIn = findViewById(R.id.btnLogIn);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    private void singIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra(MEMBER_KEY, memberLogin);
                    loadingDialog.dismissDialog();
                    startActivity(intent);
                } else {
                    // If sign in fails, display a message to the user.
                    loadingDialog.dismissDialog();
                    Toast.makeText(getBaseContext(), "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
