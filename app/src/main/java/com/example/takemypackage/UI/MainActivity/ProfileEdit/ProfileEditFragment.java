/*
Java Workshop 2020
First Application
25/06/2020
Moshe Weizman 305343931
Aharon Packter 201530508
 */

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
import com.example.takemypackage.Data.PendingParcelsFirebaseManager;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.Login.LoginActivity.LoginActivity;
import com.example.takemypackage.UI.MainActivity.MainActivity;
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
    private FirebaseUser user;
    private Member member;
    private  Member newMember;
    private Button buttonUpdate, buttonDelete;
    private EditText editTextPIN, editTextAddress, editTextEmail, editTextFirstName, editTextLastName;

    public ProfileEditFragment() {
    }
//------------------------------------------------------------------------

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
                        editTextAddress.getText().toString(), member.getPhone(),
                        editTextEmail.getText().toString(), editTextPIN.getText().toString());
                newMember.setImageFirebaseUri(member.getImageFirebaseUri());
                newMember.setImageLocalUri(member.getImageLocalUri());

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
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        loadingDialog.dismissDialog();
                    }
                });

                PendingParcelsFirebaseManager.deleteAllPedingsParcelsOfMember(member.getPhone(),  new PendingParcelsFirebaseManager.Action<String>() {
                    @Override
                    public void onSuccess(String obj) {
                        loadingDialog.dismissDialog();
                        Toast.makeText(getContext(), obj, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Exception exception) {
                        loadingDialog.dismissDialog();
                    }
                });
            }
        });
        return view;
    }
//------------------------------------------------------------------------

    /**
     * Initialize variables
     * @param view
     */
    private void init(View view) {
        Intent intent = getActivity().getIntent();
        loadingDialog = new LoadingDialog(getActivity());
        member = (Member) intent.getSerializableExtra(MEMBER_KEY);
        buttonUpdate = view.findViewById(R.id.buttonUpdate);
        buttonDelete = view.findViewById(R.id.buttonDelete);
        editTextPIN = view.findViewById(R.id.editTextPIN);
        editTextAddress = view.findViewById(R.id.editTextAddress);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        editTextAddress.setText(member.getAddress());
        editTextEmail.setText(member.getEmail());
        editTextFirstName.setText(member.getfName());
        editTextLastName.setText(member.getlName());
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }
}
