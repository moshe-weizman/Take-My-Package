package com.example.takemypackage.UI.MainActivity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.takemypackage.Entities.Member;
import com.example.takemypackage.R;
import com.example.takemypackage.UI.MainActivity.FriendsParcelsFragment.FriendsParcelsFragment;
import com.example.takemypackage.UI.MainActivity.HistoryParcelsFragment.HistoryParcelsFragment;
import com.example.takemypackage.UI.MainActivity.ProfileEdit.ProfileEditFragment;
import com.example.takemypackage.UI.MainActivity.RegisteredParcelsFragment.RegisteredParcelsFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import static com.example.takemypackage.UI.Login.LoginActivity.LoginActivity.MEMBER_KEY;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private Toolbar toolbar;
    private NavigationView nvDrawer;
    private ActionBarDrawerToggle drawerToggle;
    private FirebaseAuth auth;
    private FragmentTransaction fragmentTransaction;

    private FriendsParcelsFragment friendsParcelsFragment;
    Member member;// = new Member();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();

        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent myIntent = getIntent();
        member = (Member) myIntent.getSerializableExtra(MEMBER_KEY);
        getIntent().putExtra(MEMBER_KEY, member);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawer = findViewById(R.id.drawer_layout);
        // Find our drawer view
        nvDrawer = findViewById(R.id.nvView);
        // Setup drawer view
        setupDrawerContent(nvDrawer);

//        FragmentManager fragmentManager = getSupportFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, new RegisteredParcelsFragment(), "SOMETAG").commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
              new NavigationView.OnNavigationItemSelectedListener() {
                  @Override
                  public boolean onNavigationItemSelected(MenuItem menuItem) {
                      selectDrawerItem(menuItem);
                      return true;
                  }
              });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass;
        switch(menuItem.getItemId()) {
            case R.id.nav_registered_parcels_fragment:
                fragmentClass = RegisteredParcelsFragment.class;
                break;
            case R.id.nav_friend_parcels_fragment:
                fragmentClass = FriendsParcelsFragment.class;
                break;
            case R.id.nav_history_parcels_fragment:
                fragmentClass = HistoryParcelsFragment.class;
                break;
            case R.id.profile_update:
                fragmentClass = ProfileEditFragment.class;
                break;
            case R.id.sign_out:
                signOut();
            default:
                fragmentClass = RegisteredParcelsFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment

        fragmentTransaction.replace(R.id.flContent, fragment).commit();

        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title
        setTitle(menuItem.getTitle());
        // Close the navigation drawer
        mDrawer.closeDrawers();
    }

    private void signOut() {
        auth.signOut();
    }
}
