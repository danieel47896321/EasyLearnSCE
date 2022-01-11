package com.example.easylearnsce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Engineering;
import com.example.easylearnsce.Class.SelectView;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserLanguage;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private TextView Title, TextViewSearchLanguage;
    private UserLanguage lagnuage;
    private DrawerLayout drawerLayout;
    private ImageView BackIcon, MenuIcon;
    private User user = new User();
    private RecyclerView viewList;
    private List<Engineering> selects;
    private NavigationView UserNavigationView;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private final int SIZE = 5;
    private String HomeTagsName[] = new String[SIZE] ;
    private int EngineeringsPhotos[] = {R.drawable.engineering, R.drawable.message, R.drawable.person, R.drawable.forgotpassword ,R.drawable.signout};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
    private void init(){
        setID();
        setLanguage();
        setTags();
        MenuItem();
        SignOutIcon();
        MenuIcon();
        NavView();
    }
    private void setID(){
        intent = getIntent();
        selects = new ArrayList<>();
        user = (User)intent.getSerializableExtra("user");
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        BackIcon.setImageResource(R.drawable.signout);
        UserNavigationView = findViewById(R.id.UserNavigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.Home));
        drawerLayout = findViewById(R.id.drawerLayout);
        viewList = findViewById(R.id.MainScreenRV);
        HomeTagsName = getResources().getStringArray(R.array.HomeTagsName);
        new UserMenuAdapter(user,Home.this);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        lagnuage = new UserLanguage(Home.this, user);
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { lagnuage.setDialog(); }
        });
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemHome);
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
                new UserNavView(Home.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void SignOutIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
    private void setTags(){
        for(int i=0; i<SIZE; i++)
            selects.add(new Engineering(HomeTagsName[i], EngineeringsPhotos[i]));
        ShowTags(selects);
    }
    private void ShowTags(List<Engineering> selects){
        SelectView Select = new SelectView(this,selects);
        Select.setUser(user);
        viewList.setLayoutManager(new GridLayoutManager(this,1));
        viewList.setAdapter(Select);
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle(getResources().getString(R.string.SignOut)).setMessage(getResources().getString(R.string.AreYouSure)).setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();
                startActivity(new Intent(Home.this, EasyLearnSCE.class));
                finish();
            }
        }).setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        }).show();
    }
}