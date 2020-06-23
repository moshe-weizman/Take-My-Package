package com.example.takemypackage.UI.MainActivity.ProfileEdit;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.takemypackage.Data.MembersFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.Utils.LoadingDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileEditFragment extends Fragment {
    private LoadingDialog loadingDialog;
    private FirebaseAuth mAuth;
    FirebaseUser user;
    Member member;
    Member newMember;
    Button buttonUpdate, buttonDelete;
    EditText editTextPIN, editTextPhone, editTextAddress, editTextEmail, editTextFirstName, editTextLastName;

    public ProfileEditFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_edit, container, false);
        init(view);
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                newMember = new Member(editTextFirstName.getText().toString(), editTextLastName.getText().toString(),
                        editTextAddress.getText().toString(), editTextPhone.getText().toString(),
                        editTextEmail.getText().toString(), editTextPIN.getText().toString());
                MembersFirebaseManager.UpdateUserProfile(member.getPhone(), newMember, new MembersFirebaseManager.Action<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        user.updateEmail(editTextEmail.getText().toString());
                        user.updatePassword(editTextPIN.getText().toString());
                        loadingDialog.dismissDialog();
                        Toast.makeText(getContext(), obj, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        loadingDialog.dismissDialog();
                        Toast.makeText(getContext(), "update was failed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProgress(String status, double percent) {
                    }
                });
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDialog.startLoadingDialog();
                MembersFirebaseManager.deleteMember(member.getPhone(), new MembersFirebaseManager.Action<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        user.delete();
                        loadingDialog.dismissDialog();
                        Toast.makeText(getContext(), obj, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        loadingDialog.dismissDialog();
                    }

                    @Override
                    public void onProgress(String status, double percent) {

                    }
                });
            }
        });
        return view;
    }

    private void init(View view) {
        Intent intent = getActivity().getIntent();
        loadingDialog = new LoadingDialog(getActivity());
        member = (Member) intent.getSerializableExtra(MEMBER_KEY);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextPIN = view.findViewById(R.id.editTextPIN);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextPhone.setText(member.getPhone());
        editTextAddress.setText(member.getAddress());
        editTextEmail.setText(member.getEmail());
        editTextFirstName.setText(member.getfName());
        editTextLastName.setText(member.getlName());
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }
}
