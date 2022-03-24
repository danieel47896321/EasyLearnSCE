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

import com.example.easylearnsce.Adapters.AllRequestsAdapter;
import com.example.easylearnsce.Adapters.UserAdapter;
import com.example.easylearnsce.Adapters.UserMenuAdapter;
import com.example.easylearnsce.Class.Request;
import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserLanguage;
import com.example.easylearnsce.Class.UserNavigationView;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AllRequests extends AppCompatActivity {
    private TextView Title, TextViewSearchLanguage;
    private UserLanguage userLanguage;
    private DrawerLayout drawerLayout;
    private ImageView BackIcon, MenuIcon;
    private User user = new User();
    private RecyclerView recyclerView;
    private List<Request> Requests;
    private NavigationView UserNavigationView;
    private Intent intent;
    private EditText User_search;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private int TagsPhotos[] = {R.drawable.engineering, R.drawable.message, R.drawable.request_permisstions,R.drawable.all_requests, R.drawable.person, R.drawable.forgotpassword,R.drawable.signout};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_requests);
        init();
    }
    private void init(){
        setID();
        setLanguage();
        setTags();
        MenuItem();
        SignOutIcon();
        MenuIcon();
        NavigationView();
        UserSearch();
    }
    private void setID(){
        intent = getIntent();
        Requests = new ArrayList<>();
        user = (User)intent.getSerializableExtra("user");
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        User_search = findViewById(R.id.User_search);
        UserNavigationView = findViewById(R.id.UserNavigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.AllRequests));
        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView = findViewById(R.id.recyclerView);
        new UserMenuAdapter(user, AllRequests.this);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        userLanguage = new UserLanguage(AllRequests.this, user);
    }
    private void UserSearch() {
        User_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { RequestSearch(s.toString()); }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    private void RequestSearch(String text) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        Query query;
        if(user.getType().equals("Admin") || user.getType().equals("אדמין")) {
            query = FirebaseDatabase.getInstance().getReference().child("Requests");
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Requests.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            Request request = dataSnapshot1.getValue(Request.class);
                            if (request.getRequest().toLowerCase().contains(text.toLowerCase()) || request.getDetails().toLowerCase().contains(text.toLowerCase()) || request.getAnswer().toLowerCase().contains(text.toLowerCase()) || request.getState().toLowerCase().contains(text.toLowerCase()))
                                Requests.add(request);
                    }
                    ShowRequests(Requests);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
        else {
            query = FirebaseDatabase.getInstance().getReference().child("Requests").child(user.getUid());
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Requests.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Request request = dataSnapshot.getValue(Request.class);
                        if (request.getRequest().toLowerCase().contains(text.toLowerCase()) || request.getDetails().toLowerCase().contains(text.toLowerCase()) || request.getAnswer().toLowerCase().contains(text.toLowerCase()) || request.getState().toLowerCase().contains(text.toLowerCase()))
                            Requests.add(request);
                    }
                    ShowRequests(Requests);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { userLanguage.setDialog(); }
        });
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemAllRequests);
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
        UserNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new UserNavigationView(AllRequests.this, item.getItemId(), user);
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
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        if(user.getType().equals("Admin") || user.getType().equals("אדמין")) {
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests");
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Requests.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                            Request request = dataSnapshot1.getValue(Request.class);
                            Requests.add(request);
                        }
                    ShowRequests(Requests);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });
        }
        else {
            DatabaseReference databaseReference = firebaseDatabase.getReference().child("Requests").child(user.getUid());
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (User_search.getText().toString().equals("")) {
                        Requests.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Request request = dataSnapshot.getValue(Request.class);
                            Requests.add(request);
                            ShowRequests(Requests);
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
    }
    private void ShowRequests(List<Request> requests){
        AllRequestsAdapter Select = new AllRequestsAdapter(this,requests);
        recyclerView.setLayoutManager(new GridLayoutManager(this,1));
        recyclerView.setAdapter(Select);
    }
    public void onBackPressed() {
        intent = new Intent(AllRequests.this, Home.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}