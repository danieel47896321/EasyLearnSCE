package com.example.easylearnsce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserImage;
import com.example.easylearnsce.Class.UserLanguage;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.Class.ViewPagerAdapter;
import com.example.easylearnsce.Fragments.ChatsFragment;
import com.example.easylearnsce.Fragments.UsersFragment;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class EasyLearnChat extends AppCompatActivity {
    private User user = new User();
    private TextView Title, TextViewSearchLanguage;
    private UserLanguage lagnuage;
    private ImageView BackIcon, MenuIcon;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private NavigationView UserNavigationView;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_learn_chat);
        init();
    }
    private void init(){
        setID();
        setLanguage();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavView();
    }
    private void setID(){
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        UserNavigationView = findViewById(R.id.UserNavigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.EasyLearnChat));
        drawerLayout = findViewById(R.id.drawerLayout);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ChatsFragment(), getResources().getString(R.string.Chats));
        viewPagerAdapter.addFragment(new UsersFragment(), getResources().getString(R.string.Users));
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        new UserMenuAdapter(user,EasyLearnChat.this);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        lagnuage = new UserLanguage(EasyLearnChat.this, user);
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { lagnuage.setDialog(); }
        });
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemEasyLearnChat);
        menuItem.setCheckable(false);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);
    }
    private void MenuIcon(){
        MenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { drawerLayout.open(); }
        });
    }
    private void NavView(){
        UserNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new UserNavView(EasyLearnChat.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(Home.class); }
        });
    }
    private void StartActivity(Class Destination){
        intent = new Intent(EasyLearnChat.this, Destination);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        intent = new Intent(EasyLearnChat.this, Home.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}