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

import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Adapters.HomeSelectAdapter;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuInfo;
import com.example.easylearnsce.Class.UserNavigationView;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class Home extends AppCompatActivity {
    private TextView Title;
    private DrawerLayout drawerLayout;
    private ImageView BackIcon, MenuIcon;
    private User user = new User();
    private RecyclerView recyclerView;
    private List<Tag> Tags;
    private NavigationView navigationView;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private String HomeTags[] ;
    private int TagsPhotos[] = {R.drawable.engineering, R.drawable.message, R.drawable.request_permisstions,R.drawable.all_requests, R.drawable.person, R.drawable.forgotpassword,R.drawable.signout};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
    }
    private void init(){
        setID();
        setTags();
        MenuItem();
        SignOutIcon();
        MenuIcon();
        NavigationView();
    }
    private void setID(){
        intent = getIntent();
        Tags = new ArrayList<>();
        user = (User)intent.getSerializableExtra("user");
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        BackIcon.setImageResource(R.drawable.signout);
        navigationView = findViewById(R.id.navigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.Home));
        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView = findViewById(R.id.recyclerView);
        HomeTags = new String[TagsPhotos.length];
        HomeTags = getResources().getStringArray(R.array.HomeTagsName);
        new UserMenuInfo(user,Home.this);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
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
    private void NavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new UserNavigationView(Home.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void SignOutIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackPressed(); }
        });
    }
    private void setTags(){
        for(int i = 0; i< TagsPhotos.length; i++)
            Tags.add(new Tag(HomeTags[i], TagsPhotos[i]));
        ShowTags(Tags);
    }
    private void ShowTags(List<Tag> selects){
        HomeSelectAdapter Select = new HomeSelectAdapter(this,selects);
        Select.setUser(user);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(Select);
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
        builder.setTitle(getResources().getString(R.string.SignOut)).setMessage(getResources().getString(R.string.AreYouSure)).setCancelable(true)
                .setPositiveButton(getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(firebaseAuth.getCurrentUser() != null)
                    firebaseAuth.signOut();
                /*GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(Home.this.getString(R.string.default_web_client_id)).requestEmail().build();
                GoogleSignInClient googleClient = GoogleSignIn.getClient(Home.this, options);
                googleClient.signOut();
                startActivity(new Intent(Home.this, EasyLearnSCE.class));
                finish();*/
            }
        }).setNegativeButton(getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { }
        }).show();
    }
}