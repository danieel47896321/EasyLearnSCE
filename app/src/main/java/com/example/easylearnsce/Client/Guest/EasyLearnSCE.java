package com.example.easylearnsce.Client.Guest;

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

import com.example.easylearnsce.Client.Adapters.EasyLearnAdapter;
import com.example.easylearnsce.Client.Class.GuestNavigationView;
import com.example.easylearnsce.Client.Class.Tag;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;

public class EasyLearnSCE extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private ArrayList<Tag> tags;
    private NavigationView navigationView;
    private TextView Title;
    private ImageView BackIcon, MenuIcon;
    private String EasyLearnSCETagNames[];
    private int TagPhotos[] = {R.drawable.login,R.drawable.register,R.drawable.forgotpassword,R.drawable.about,R.drawable.contact};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easylearnsce);
        init();
    }
    private void init(){
        tags = new ArrayList<>();
        setID();
        setTags();
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
        navigationView = findViewById(R.id.navigationView);
        recyclerView = findViewById(R.id.recyclerView);
        EasyLearnSCETagNames = new String[TagPhotos.length];
        EasyLearnSCETagNames = getResources().getStringArray(R.array.EasyLearnSCETagsName);

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
        for(int i = 0; i< TagPhotos.length; i++)
            tags.add(new Tag(EasyLearnSCETagNames[i], TagPhotos[i]));
        ShowTags(tags);
    }
    private void ShowTags(ArrayList<Tag> EasyLearnList){
        EasyLearnAdapter easyLearnAdapter = new EasyLearnAdapter(this,EasyLearnList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(easyLearnAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(EasyLearnSCE.this,R.style.AppCompatAlertDialogStyle);
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