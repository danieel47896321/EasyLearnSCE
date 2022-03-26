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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Adapters.EngineeringAdapter;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuInfo;
import com.example.easylearnsce.Class.UserNavigationView;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class SelectEngineering extends AppCompatActivity {
    private User user = new User();
    private TextView Title;
    private ImageView BackIcon, MenuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private List<Tag> tags;
    private EditText User_search;
    private String HomeTags[];
    private int TagsPhotos[] = {R.drawable.structural, R.drawable.mechanical, R.drawable.electrical, R.drawable.software,R.drawable.nihol,R.drawable.chemistry,R.drawable.software,R.drawable.mehina};
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
        HomeTags = new String[TagsPhotos.length];
        HomeTags = getResources().getStringArray(R.array.Engineerings);
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
        for(int i = 0; i< TagsPhotos.length; i++) {
            if(HomeTags[i].toLowerCase().contains(Text.toLowerCase()))
                tags.add(new Tag(HomeTags[i], TagsPhotos[i]));
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
        for(int i = 0; i< TagsPhotos.length; i++)
            tags.add(new Tag(HomeTags[i], TagsPhotos[i]));
        ShowTags(tags);
    }
    private void ShowTags(List<Tag> engineeringList){
        EngineeringAdapter engineeringAdapter = new EngineeringAdapter(this,engineeringList);
        engineeringAdapter.setUser(user);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(engineeringAdapter);
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