package com.example.takemypackage.UI.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.takemypackage.UI.Login.LoginActivity.*;
import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.Login.LoginActivity.LoginActivity;
import com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment.FriendsParcelsFragment;
import com.example.takemypackage.UI.MainActivity.HistoryParcelsFragment.HistoryParcelsFragment;
import com.example.takemypackage.UI.MainActivity.ProfileEdit.ProfileEditFragment;

import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;

public class MainActivity extends AppCompatActivity {
    private FriendsParcelsFragment friendsParcelsFragment;
    Member member;// = new Member();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent myIntent = getIntent();
        member = (Member) myIntent.getSerializableExtra(MEMBER_KEY);
        getIntent().putExtra(MEMBER_KEY, member);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new ProfileEditFragment(), "SOMETAG").commit();
        // friendsParcelsFragment = new FriendsParcelsFragment();
        //fragmentTransaction.add(R.id.fragmentContainer, friendsParcelsFragment);
        //fragmentTransaction.commit();
    }
}
