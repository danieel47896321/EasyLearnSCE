package com.example.easylearnsce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Engineering;
import com.example.easylearnsce.Class.EngineeringAdapter;
import com.example.easylearnsce.Class.SelectView;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserLanguage;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.R;
import com.example.easylearnsce.SelectFunc.GenericEngineering;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectEngineering extends AppCompatActivity {
    private User user = new User();
    private TextView Title, TextViewSearchLanguage, User_search,user_fullname,user_email;
    private UserLanguage lagnuage;
    private ImageView BackIcon, MenuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView UserNavigationView;
    private RecyclerView viewList;
    private List<Engineering> Engineerings;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_engineering);
        init();
    }
    private void init(){
        setID();
        setEngineering();
        setLanguage();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavView();
        User_Search();
    }
    private void setID(){
        Engineerings = new ArrayList<>();
        intent = getIntent();
        User_search = findViewById(R.id.User_search);
        viewList = findViewById(R.id.EngineeringRV);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.SelectEngineering));
        user = (User)intent.getSerializableExtra("user");
        UserNavigationView = findViewById(R.id.UserNavigationView);
        user_fullname = UserNavigationView.getHeaderView(0).findViewById(R.id.user_fullname);
        user_email = UserNavigationView.getHeaderView(0).findViewById(R.id.user_email);
        user_fullname.setText(user.getFirstname()+" "+user.getLastname());
        user_email.setText(user.getEmail());
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuAdapter(user,SelectEngineering.this);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        lagnuage = new UserLanguage(SelectEngineering.this, user);
    }
    private void User_Search() {
        User_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { UserSearch(s.toString()); }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    private void UserSearch(String text) {
        Query query;
        if (getResources().getConfiguration().locale.getDisplayName().equals("Hebrew"))
            query = FirebaseDatabase.getInstance().getReference().child("Engineering").child("Hebrew");
        else
            query = FirebaseDatabase.getInstance().getReference().child("Engineering").child("English");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Engineerings.clear();
                for (DataSnapshot eng : snapshot.getChildren()) {
                    Engineering get_engineering = eng.getValue(Engineering.class);
                    if(get_engineering.getEngineeringname().toLowerCase().contains(text.toLowerCase())){
                        Engineerings.add(get_engineering);
                    }
                }
                ShowTags(Engineerings);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setEngineering(){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference;
            if (getResources().getConfiguration().locale.getDisplayName().equals("Hebrew"))
                reference = database.getReference().child("Engineering").child("Hebrew");
            else
                reference = database.getReference().child("Engineering").child("English");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(User_search.getText().toString().equals("")) {
                        Engineerings.clear();
                        for (DataSnapshot eng : snapshot.getChildren()) {
                            Engineering get_engineering = eng.getValue(Engineering.class);
                            Engineerings.add(get_engineering);
                        }
                        ShowTags(Engineerings);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { lagnuage.setDialog(); }
        });
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemSelectEngineering);
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
                new UserNavView(SelectEngineering.this, item.getItemId(), user);
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
    private void ShowTags(List<Engineering> engineeringsList){
        EngineeringAdapter myEngineerings = new EngineeringAdapter(this,engineeringsList);
        myEngineerings.setUser(user);
        viewList.setLayoutManager(new GridLayoutManager(this,1));
        viewList.setAdapter(myEngineerings);
    }
    private void StartActivity(Class Destination){
        intent = new Intent(SelectEngineering.this, Destination);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() {
        intent = new Intent(SelectEngineering.this, Home.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}