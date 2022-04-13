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

import com.example.easylearnsce.Client.Adapters.AllRequestsAdapter;
import com.example.easylearnsce.Client.Class.UserMenuInfo;
import com.example.easylearnsce.Client.Class.Request;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.Class.UserNavigationView;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AllRequests extends AppCompatActivity {
    private TextView Title;
    private DrawerLayout drawerLayout;
    private ImageView BackIcon, MenuIcon;
    private User user = new User();
    private RecyclerView recyclerView;
    private ArrayList<Request> Requests;
    private NavigationView navigationView;
    private Intent intent;
    private Query query;
    private EditText User_search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_requests);
        init();
    }
    private void init(){
        setID();
        setTags();
        MenuItem();
        BackIcon();
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
        navigationView = findViewById(R.id.navigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.AllRequests));
        drawerLayout = findViewById(R.id.drawerLayout);
        recyclerView = findViewById(R.id.recyclerView);
        new UserMenuInfo(user, AllRequests.this);
    }
    private void UserSearch() {
        User_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { CheckType(s.toString()); }
            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
    private void CheckType(String text){
        if(user.getType().equals("Admin") || user.getType().equals("אדמין"))
            Admin(text);
        else if(user.getType().equals("Teacher") || user.getType().equals("מרצה") || user.getType().equals("Student") || user.getType().equals("סטודנט"))
            TeacherAndStudent(text);
    }
    private void Admin(String text){
        query = FirebaseDatabase.getInstance().getReference().child("Requests");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Requests.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                    for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        Request request = dataSnapshot1.getValue(Request.class);
                        String FullName = request.getFirstName() + " " + request.getLastName();
                        if (request.getRequest().toLowerCase().contains(text.toLowerCase()) || request.getEmail().toLowerCase().contains(text.toLowerCase()) || request.getType().toLowerCase().contains(text.toLowerCase())
                                || FullName.toLowerCase().contains(text.toLowerCase()) || request.getDetails().toLowerCase().contains(text.toLowerCase()) || request.getAnswer().toLowerCase().contains(text.toLowerCase())
                                || request.getStatus().toLowerCase().contains(text.toLowerCase()))
                            Requests.add(request);
                    }
                ShowRequests(Requests,"Admin");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void TeacherAndStudent(String text){
        query = FirebaseDatabase.getInstance().getReference().child("Requests").child(user.getUid());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Requests.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Request request = dataSnapshot.getValue(Request.class);
                    String FullName = request.getFirstName() + " " + request.getLastName();
                    if (request.getRequest().toLowerCase().contains(text.toLowerCase()) || request.getEmail().toLowerCase().contains(text.toLowerCase()) || request.getType().toLowerCase().contains(text.toLowerCase())
                            || FullName.toLowerCase().contains(text.toLowerCase()) || request.getDetails().toLowerCase().contains(text.toLowerCase()) || request.getAnswer().toLowerCase().contains(text.toLowerCase())
                            || request.getStatus().toLowerCase().contains(text.toLowerCase()))
                        Requests.add(request);
                }
                ShowRequests(Requests, "Else");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new UserNavigationView(AllRequests.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { onBackPressed(); }
        });
    }
    private void setTags(){ CheckType(""); }
    private void ShowRequests(ArrayList<Request> requests, String type){
        AllRequestsAdapter Select = new AllRequestsAdapter(this,requests,type);
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