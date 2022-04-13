package com.example.easylearnsce.Client.User;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Client.Class.Tag;
import com.example.easylearnsce.Client.Adapters.SelectEngineeringAdapter;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.Class.UserMenuInfo;
import com.example.easylearnsce.Client.Class.UserNavigationView;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class SelectEngineering extends AppCompatActivity {
    private User user = new User();
    private TextView Title;
    private ImageView BackIcon, MenuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private ArrayList<Tag> tags;
    private EditText User_search;
    private String EngineeringNames[];
    private int TagPhotos[] = {R.drawable.structural, R.drawable.mechanical, R.drawable.electrical, R.drawable.software,R.drawable.industrial,R.drawable.chemical,R.drawable.software,R.drawable.mehina};
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
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
        UserSearch();
    }
    private void setID(){
        tags = new ArrayList<>();
        intent = getIntent();
        User_search = findViewById(R.id.User_search);
        recyclerView = findViewById(R.id.recyclerView);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.SelectEngineering));
        user = (User)intent.getSerializableExtra("user");
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuInfo(user,SelectEngineering.this);
        EngineeringNames = new String[TagPhotos.length];
        EngineeringNames = getResources().getStringArray(R.array.Engineerings);
    }
    private void UserSearch() {
        User_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0)
                    EngineeringSearch(s.toString());
                else
                    setEngineering();
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    private void EngineeringSearch(String Text) {
        tags.clear();
        for(int i = 0; i< TagPhotos.length; i++) {
            if(EngineeringNames[i].toLowerCase().contains(Text.toLowerCase()))
                tags.add(new Tag(EngineeringNames[i], TagPhotos[i]));
        }
        ShowTags(tags);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
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
    private void NavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new UserNavigationView(SelectEngineering.this, item.getItemId(), user);
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
    private void setEngineering(){
        tags.clear();
        for(int i = 0; i< TagPhotos.length; i++)
            tags.add(new Tag(EngineeringNames[i], TagPhotos[i]));
        ShowTags(tags);
    }
    private void ShowTags(ArrayList<Tag> SelectEngineeringList){
        SelectEngineeringAdapter selectEngineeringAdapter = new SelectEngineeringAdapter(this,SelectEngineeringList,user);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(selectEngineeringAdapter);
    }
    private void StartActivity(Class Destination){
        intent = new Intent(SelectEngineering.this, Destination);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    @Override
    public void onBackPressed() { StartActivity(Home.class); }
}