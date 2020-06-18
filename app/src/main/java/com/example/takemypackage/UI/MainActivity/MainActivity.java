package com.example.takemypackage.UI.MainActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.takemypackage.R;
import com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment.FriendsParcelsFragment;

public class MainActivity extends AppCompatActivity {
    private FriendsParcelsFragment friendsParcelsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new FriendsParcelsFragment(),"SOMETAG" ).commit();
       // friendsParcelsFragment = new FriendsParcelsFragment();
        //fragmentTransaction.add(R.id.fragmentContainer, friendsParcelsFragment);
        //fragmentTransaction.commit();
    }
}
