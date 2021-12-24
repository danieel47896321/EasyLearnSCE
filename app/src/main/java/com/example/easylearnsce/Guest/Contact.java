package com.example.easylearnsce.Guest;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.GuestLagnuage;
import com.example.easylearnsce.Class.GuestNavView;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.Home;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {
    private TextView SendEmail, Title, TextViewSearchLanguage;
    private ImageView BackIcon, MenuIcon;
    private NavigationView GuestNavView;
    private DrawerLayout drawerLayout;
    private User user = null;
    private GuestLagnuage lagnuage;
    private Intent intent;
    private String Email ="EasyLearnSCE@gmail.com";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        init();
    }
    private void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavView();
        SendEmail();
        setLanguage();
    }
    private void setID() {
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        Title = findViewById(R.id.Title);
        GuestNavView = findViewById(R.id.GuestNavView);
        SendEmail = findViewById(R.id.SendEmail);
        Title.setText(R.string.Contact);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        lagnuage = new GuestLagnuage(Contact.this);
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { lagnuage.setDialog(); }
        });
    }
    private void MenuItem(){
        Menu menu= GuestNavView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemContact);
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
                new GuestNavView(Contact.this, item.getItemId());
                return false;
            }
        });
    }
    private void SendEmail(){
        SendEmail.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View v) {
                intent = new Intent(intent.ACTION_SEND);
                String toArray[] = new String[1];
                toArray[0] = Email;
                intent.putExtra(intent.EXTRA_EMAIL, toArray);
                intent.putExtra(intent.EXTRA_SUBJECT, getResources().getString(R.string.ContactSubject));
                intent.putExtra(intent.EXTRA_TEXT, getResources().getString(R.string.ContactText));
                intent.setType("message/rfc822");
                startActivity(intent.createChooser(intent, getResources().getString(R.string.ContactEmailChooser)));
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Contact.this, EasyLearnSCE.class));
                finish();
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(user != null) {
            Intent intent = new Intent(Contact.this, Home.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
        else
            startActivity(new Intent(Contact.this, EasyLearnSCE.class));
        finish();
    }
}