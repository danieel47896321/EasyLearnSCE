package com.example.easylearnsce.Guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.GuestLagnuage;
import com.example.easylearnsce.Class.GuestNavView;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class About extends AppCompatActivity {
    private TextView Title, TextViewSearchLanguage, Copyright, Version, About;
    private ImageView BackIcon, MenuIcon;
    private NavigationView GuestNavView;
    private DrawerLayout drawerLayout;
    private GuestLagnuage lagnuage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
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
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        Title = findViewById(R.id.Title);
        GuestNavView = findViewById(R.id.GuestNavView);
        Title.setText(R.string.About);
        Copyright = findViewById(R.id.Copyright);
        Version = findViewById(R.id.Version);
        About = findViewById(R.id.About);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        lagnuage = new GuestLagnuage(About.this);
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
        Menu menu= GuestNavView.getMenu();
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
        GuestNavView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new GuestNavView(About.this, item.getItemId());
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(About.this, EasyLearnSCE.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(About.this, EasyLearnSCE.class));
        finish();
    }
}