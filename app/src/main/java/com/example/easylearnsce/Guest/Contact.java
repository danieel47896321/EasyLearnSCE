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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Contact extends AppCompatActivity {
    private TextView SendEmail, Title, TextViewSearchLanguage, ContactText;
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
        Info();
        Email();
    }
    private void setID() {
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        ContactText = findViewById(R.id.ContactText);
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
    private void Email(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("Contact").child("Email");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { SendEmail.setText((String)snapshot.getValue()); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void Info(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference().child("About");
        if (getResources().getConfiguration().locale.getDisplayLanguage().equals("English") || getResources().getConfiguration().locale.getDisplayLanguage().equals("אנגלית"))
            ref = database.getReference().child("Contact").child("Info").child("English");
        else if(getResources().getConfiguration().locale.getDisplayLanguage().equals("Hebrew") || getResources().getConfiguration().locale.getDisplayLanguage().equals("עברית"))
            ref = database.getReference().child("Contact").child("Info").child("Hebrew");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { ContactText.setText((String)snapshot.getValue()); }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
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
                Email = SendEmail.getText().toString();
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