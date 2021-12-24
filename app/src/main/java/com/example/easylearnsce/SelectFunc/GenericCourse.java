package com.example.easylearnsce.SelectFunc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Class.Select;
import com.example.easylearnsce.Class.SelectView;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Profile;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class GenericCourse extends AppCompatActivity {
    private ImageView back_icon,menu_icon;
    private TextView title,user_fullname,user_email;
    private User user = new User();
    private NavigationView user_navView;
    private RecyclerView viewList;
    private List<Select> selects;
    private Intent intent;
    private String lectures[] = {"הרצאה 1","הרצאה 2","הרצאה 3","הרצאה 4","הרצאה 5","הרצאה 6","הרצאה 7","הרצאה 8","הרצאה 9","הרצאה 10"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_course);
        init();
    }
    public void init(){
        setID();
        ReturnBack();
        ChoseEngineering();
        Menu();
        NavView();
    }
    public void setID(){
        intent = getIntent();
        selects = new ArrayList<>();
        title = findViewById(R.id.Title);
        menu_icon = findViewById(R.id.MenuIcon);
        back_icon = findViewById(R.id.BackIcon);
        user_navView = findViewById(R.id.UserNavigationView);
        viewList = findViewById(R.id.MainScreenRV);
        user = (User)intent.getSerializableExtra("user");
        title.setText(user.getEngineering()+"\n"+(String)intent.getSerializableExtra("title"));
        user.setCourse((String)intent.getSerializableExtra("title"));
        user_fullname = user_navView.getHeaderView(0).findViewById(R.id.user_fullname);
        user_email = user_navView.getHeaderView(0).findViewById(R.id.user_email);
        user_fullname.setText(user.getFirstname()+" "+user.getLastname());
        user_email.setText(user.getEmail());
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
                    intent = new Intent(GenericCourse.this, Home.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(id == R.id.ItemSelectEngineering){
                    intent = new Intent(GenericCourse.this, SelectEngineering.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }
                else if(id == R.id.ItemProfile) {
                    intent = new Intent(GenericCourse.this, Profile.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                }

                else if(id == R.id.ItemSignOut)
                    startActivity(new Intent(GenericCourse.this, EasyLearnSCE.class));
                finish();
                return false;
            }
        });
    }
    public void ReturnBack(){
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(GenericCourse.this, GenericEngineering.class);
                user.setCourse("null");
                intent.putExtra("user", user);
                intent.putExtra("title", user.getEngineering());
                startActivity(intent);
                finish();
            }
        });
    }
    public void SetTags(String[] engineering){
        for(int i=0; i<engineering.length; i++)
            selects.add(new Select(engineering[i],R.drawable.book));
        ShowTags(selects);
    }
    public void ChoseEngineering(){
        SetTags(lectures);
    }
    public void ShowTags(List<Select> selects){
        SelectView mySelects = new SelectView(this,selects);
        mySelects.setUser(user);
        viewList.setLayoutManager(new GridLayoutManager(this,1));
        viewList.setAdapter(mySelects);
    }
}