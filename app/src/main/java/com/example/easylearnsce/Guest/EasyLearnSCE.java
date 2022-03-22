package com.example.easylearnsce.Guest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Adapters.EasyLearnSceAdapter;
import com.example.easylearnsce.Class.GuestLagnuage;
import com.example.easylearnsce.Class.GuestNavigationView;
import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;
import java.util.List;

public class EasyLearnSCE extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private List<Tag> Selects;
    private NavigationView navigationView;
    private TextView Title, TextViewSearchLanguage;
    private ImageView BackIcon, MenuIcon;
    private GuestLagnuage guestLagnuage;
    private String EasyLearnSCETagsName[];
    private int TagsPhotos[] = {R.drawable.login,R.drawable.register,R.drawable.forgotpassword,R.drawable.about,R.drawable.contact};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easylearnsce);
        init();
    }
    private void init(){
        Selects = new ArrayList<>();
        setID();
        setTags();
        setLanguage();
        MenuItem();
        MenuIcon();
        NavigationView();
    }
    private void setID(){
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        BackIcon.setVisibility(View.INVISIBLE);
        drawerLayout = findViewById(R.id.drawerLayout);
        Title = findViewById(R.id.Title);
        Title.setText("");
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        guestLagnuage = new GuestLagnuage(EasyLearnSCE.this);
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerView);
        EasyLearnSCETagsName = new String[TagsPhotos.length];
        EasyLearnSCETagsName = getResources().getStringArray(R.array.EasyLearnSCETagsName);

    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { guestLagnuage.setDialog(); }
        });
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemEasyLearnSCE);
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
                new GuestNavigationView(EasyLearnSCE.this, item.getItemId());
                return false;
            }
        });
    }
    private void setTags(){
        for(int i=0; i<TagsPhotos.length; i++)
            Selects.add(new Tag(EasyLearnSCETagsName[i], TagsPhotos[i]));
        ShowTags(Selects);
    }
    private void ShowTags(List<Tag> selects){
        EasyLearnSceAdapter Select = new EasyLearnSceAdapter(this,selects);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(Select);
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EasyLearnSCE.this);
        builder.setTitle(getResources().getString(R.string.Exit)).setMessage(getResources().getString(R.string.AreYouSureToExit)).setCancelable(true).setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        System.exit(0);
                    }
        }).setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        }).show();
    }
}