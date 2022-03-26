package com.example.easylearnsce.EngineeringFunc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuInfo;
import com.example.easylearnsce.Class.UserNavigationView;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class GenericCourse extends AppCompatActivity {
    private ImageView BackIcon, MenuIcon;
    private TextView title,user_fullname,user_email;
    private User user = new User();
    private DrawerLayout drawerLayout;
    private NavigationView UserNavigationView;
    private RecyclerView viewList;
    private List<Tag> selects;
    private Intent intent;
    private String Course = "Course";
    private String Engineering = "Tag";
    private String lectures[] = {"הרצאה 1","הרצאה 2","הרצאה 3","הרצאה 4","הרצאה 5","הרצאה 6","הרצאה 7","הרצאה 8","הרצאה 9","הרצאה 10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_course);
        init();
    }
    public void init(){
        setID();
        ChoseEngineering();
        BackIcon();
        MenuIcon();
        NavView();
    }
    public void setID(){
        intent = getIntent();
        selects = new ArrayList<>();
        title = findViewById(R.id.Title);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        viewList = findViewById(R.id.MainScreenRV);
        user = (User)intent.getSerializableExtra("user");
        Engineering = (String)intent.getSerializableExtra("Tag");
        Course = (String)intent.getSerializableExtra("Course");
        title.setText(Engineering+"\n"+Course);
        UserNavigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        user_fullname = UserNavigationView.getHeaderView(0).findViewById(R.id.user_fullname);
        user_email = UserNavigationView.getHeaderView(0).findViewById(R.id.user_email);
        user_fullname.setText(user.getFirstName()+" "+user.getLastName());
        user_email.setText(user.getEmail());
        new UserMenuInfo(user,GenericCourse.this);
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
                new UserNavigationView(GenericCourse.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(GenericCourse.this, GenericEngineering.class);
                intent.putExtra("user", user);
                intent.putExtra("Tag", Engineering);
                startActivity(intent);
                finish();
            }
        });
    }
    public void SetTags(String[] engineering){
        for(int i=0; i<engineering.length; i++)
            selects.add(new Tag(engineering[i],R.drawable.book));
        ShowTags(selects);
    }
    public void ChoseEngineering(){
        SetTags(lectures);
    }
    public void ShowTags(List<Tag> selects){
        /*SelectView mySelects = new SelectView(this,selects);
        mySelects.setUser(user);
        viewList.setLayoutManager(new GridLayoutManager(this,1));
        viewList.setAdapter(mySelects);*/
    }
}