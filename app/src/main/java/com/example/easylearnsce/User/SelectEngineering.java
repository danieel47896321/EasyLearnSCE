package com.example.easylearnsce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Select;
import com.example.easylearnsce.Class.SelectView;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserImage;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class SelectEngineering extends AppCompatActivity {
    private User user = new User();
    private TextView Title;
    private ImageView BackIcon, MenuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView UserNavigationView;
    private RecyclerView viewList;
    private List<Select> selects;
    private Intent intent;
    private final int SIZE = 7;
    private String Engineerings[] = new String[SIZE];
    private int EngineeringsIcon[] = {R.drawable.build,R.drawable.machines,R.drawable.electronics,R.drawable.software,R.drawable.nihol,R.drawable.chemistry,R.drawable.mehina};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_engineering);
        init();
    }
    private void init(){
        setID();
        setTags();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavView();
    }
    private void setID(){
        selects = new ArrayList<>();
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        viewList = findViewById(R.id.EngineeringRV);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        UserNavigationView = findViewById(R.id.UserNavigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.SelectEngineering));
        drawerLayout = findViewById(R.id.drawerLayout);
        Engineerings = getResources().getStringArray(R.array.Engineerings);
        new UserMenuAdapter(user,SelectEngineering.this);
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
    private void setTags(){
        for(int i=0; i<SIZE; i++)
            selects.add(new Select(Engineerings[i], EngineeringsIcon[i]));
        ShowTags(selects);
    }
    private void ShowTags(List<Select> engineeringsList){
        SelectView myEngineerings = new SelectView(this,engineeringsList);
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