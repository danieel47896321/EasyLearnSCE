package com.example.easylearnsce.SelectFunc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Engineering;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Profile;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Lecture extends AppCompatActivity {
    private ImageView back_icon,menu_icon;
    private TextView title,user_fullname,user_email;
    private User user = new User();
    private NavigationView user_navView;
    private RecyclerView viewList;
    private List<Engineering> selects;
    private Intent intent;
    private String Course = "Course";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lecture);
        init();
    }
    public void init(){
        setID();
        ShowLecture();
        ReturnBack();
        Menu();
        NavView();
    }
    public void setID(){
        intent = getIntent();
        selects = new ArrayList<>();
        title = findViewById(R.id.Title);
        user = (User)intent.getSerializableExtra("user");
        Course = (String)intent.getSerializableExtra("CourseName");
        title.setText(user.getEngineering()+" - "+Course);
        viewList = findViewById(R.id.MainScreenRV);
        menu_icon = findViewById(R.id.MenuIcon);
        back_icon = findViewById(R.id.BackIcon);
        user_navView = findViewById(R.id.UserNavigationView);

        user_fullname = user_navView.getHeaderView(0).findViewById(R.id.user_fullname);
        user_email = user_navView.getHeaderView(0).findViewById(R.id.user_email);
        user_fullname.setText(user.getFirstName()+" "+user.getLastName());
        user_email.setText(user.getEmail());
    }
    public void ShowLecture(){

    }
    public void Menu(){
        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(user_navView.getVisibility() == View.INVISIBLE)
                    user_navView.setVisibility(View.VISIBLE);
                else
                    user_navView.setVisibility(View.INVISIBLE);
            }
        });
    }
    public void NavView(){
        user_navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.ItemHome){
                    intent = new Intent(Lecture.this, Home.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(id == R.id.ItemSelectEngineering){
                    intent = new Intent(Lecture.this, SelectEngineering.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(id == R.id.ItemProfile) {
                    intent = new Intent(Lecture.this, Profile.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }

                else if(id == R.id.ItemSignOut)
                    startActivity(new Intent(Lecture.this, EasyLearnSCE.class));
                finish();
                return false;
            }
        });
    }
    public void ReturnBack(){
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(Lecture.this, GenericCourse.class);
                user.setLecture("null");
                intent.putExtra("user", user);
                intent.putExtra("title", user.getCourse());
                startActivity(intent);
                finish();
            }
        });
    }
}