package com.example.easylearnsce.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserLanguage;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.R;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserAbout extends AppCompatActivity {
    private TextView Title, TextViewSearchLanguage, Copyright, Version, About;
    private ImageView BackIcon, MenuIcon;
    private NavigationView UserNavigationView;
    private DrawerLayout drawerLayout;
    private User user = null;
    private UserLanguage lagnuage;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_about);
        init();
    }
    private void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavView();
        setLanguage();
        Version();
        Copyright();
        Info();
    }
    private void setID(){
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        Title = findViewById(R.id.Title);
        UserNavigationView = findViewById(R.id.UserNavigationView);
        Title.setText(R.string.About);
        Copyright = findViewById(R.id.Copyright);
        Version = findViewById(R.id.Version);
        About = findViewById(R.id.About);
        new UserMenuAdapter(user,UserAbout.this);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        lagnuage = new UserLanguage(UserAbout.this, user);
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { lagnuage.setDialog(); }
        });
    }
    private void Version(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("About");
        if (getResources().getConfiguration().locale.getDisplayLanguage().equals("English") || getResources().getConfiguration().locale.getDisplayLanguage().equals("אנגלית"))
            ref = database.getReference().child("About").child("Version").child("English");
        else if(getResources().getConfiguration().locale.getDisplayLanguage().equals("Hebrew") || getResources().getConfiguration().locale.getDisplayLanguage().equals("עברית"))
            ref = database.getReference().child("About").child("Version").child("Hebrew");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { Version.setText((String)snapshot.getValue()); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void Copyright(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("About");
        if (getResources().getConfiguration().locale.getDisplayLanguage().equals("English") || getResources().getConfiguration().locale.getDisplayLanguage().equals("אנגלית"))
            ref = database.getReference().child("About").child("Copyright").child("English");
        else if(getResources().getConfiguration().locale.getDisplayLanguage().equals("Hebrew") || getResources().getConfiguration().locale.getDisplayLanguage().equals("עברית"))
            ref = database.getReference().child("About").child("Copyright").child("Hebrew");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { Copyright.setText((String)snapshot.getValue()); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void Info(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("About");
        if (getResources().getConfiguration().locale.getDisplayLanguage().equals("English") || getResources().getConfiguration().locale.getDisplayLanguage().equals("אנגלית"))
            ref = database.getReference().child("About").child("Info").child("English");
        else if(getResources().getConfiguration().locale.getDisplayLanguage().equals("Hebrew") || getResources().getConfiguration().locale.getDisplayLanguage().equals("עברית"))
            ref = database.getReference().child("About").child("Info").child("Hebrew");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { About.setText((String)snapshot.getValue()); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemAbout);
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
                new UserNavView(UserAbout.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackPressed(); }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserAbout.this, Home.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}