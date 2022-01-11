package com.example.easylearnsce.SelectFunc;

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

import com.example.easylearnsce.Class.AddCourseDialog;
import com.example.easylearnsce.Class.AllCourses;
import com.example.easylearnsce.Class.Course;
import com.example.easylearnsce.Class.CourseView;
import com.example.easylearnsce.Class.Engineering;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserLanguage;
import com.example.easylearnsce.Class.UserMenuAdapter;
import com.example.easylearnsce.Class.UserNavView;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GenericEngineering extends AppCompatActivity {
    private ImageView BackIcon, MenuIcon, addCourse, removeCourse;
    private DrawerLayout drawerLayout;
    private NavigationView UserNavigationView;
    private UserLanguage lagnuage;
    private TextView title,user_fullname,user_email,TextViewSearchLanguage, User_search;
    private User user = new User();
    private String Engineering = "Engineering";
    private RecyclerView viewList;
    private NavigationView user_navView;
    private List<Course> courses;
    private Intent intent;
    private AllCourses allCourses = new AllCourses();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_engineering);
        init();
    }
    public void init(){
        setID();
        ChoseEngineering();
        BackIcon();
        MenuIcon();
        setLanguage();
        NavView();
        setCourses();
        addCourse();
        removeCourse();
    }
    public void setID(){
        intent = getIntent();
        courses = new ArrayList<>();
        title = findViewById(R.id.Title);
        addCourse = findViewById(R.id.addCourse);
        removeCourse = findViewById(R.id.removeCourse);
        User_search = findViewById(R.id.User_search);
        viewList = findViewById(R.id.CoursesRV);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        user_navView = findViewById(R.id.UserNavigationView);
        Engineering = (String)intent.getSerializableExtra("Engineering");
        title.setText(Engineering);
        user = (User)intent.getSerializableExtra("user");
        user.setEngineering(title.getText().toString());
        user_fullname = user_navView.getHeaderView(0).findViewById(R.id.user_fullname);
        user_email = user_navView.getHeaderView(0).findViewById(R.id.user_email);
        user_fullname.setText(user.getFirstname()+" "+user.getLastname());
        user_email.setText(user.getEmail());
        UserNavigationView = findViewById(R.id.UserNavigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuAdapter(user,GenericEngineering.this);
        TextViewSearchLanguage = findViewById(R.id.TextViewSearchLanguage);
        lagnuage = new UserLanguage(GenericEngineering.this, user);
    }
    private void MenuItem(){
        Menu menu= UserNavigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemSelectEngineering);
        menuItem.setCheckable(false);
        menuItem.setChecked(true);
        menuItem.setEnabled(false);
    }
    private void addCourse(){
        if(user.getType().equals("Admin") || user.getType().equals("Teacher")) {
            addCourse.setVisibility(View.VISIBLE);
            addCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddCourseDialog addCourseDialog = new AddCourseDialog();
                    addCourseDialog.show(getSupportFragmentManager(), "Add Course");
                }
            });
        }
    }
    private void removeCourse(){
        if(user.getType().equals("Admin") || user.getType().equals("Teacher")) {
            removeCourse.setVisibility(View.VISIBLE);
            removeCourse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
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
                new UserNavView(GenericEngineering.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(GenericEngineering.this, SelectEngineering.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });
    }
    private void setLanguage(){
        TextViewSearchLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lagnuage.setEngineering(Engineering);
                lagnuage.setDialog();
            }
        });
    }
    private void ChoseEngineering(){
        if(title.getText().equals("Structural Engineering") || title.getText().equals("הנדסת בניין"))
            SetTags(allCourses.getChemical_Engineering());
        else if(title.getText().equals("Mechanical Engineering") || title.getText().equals("הנדסת מכונות"))
            SetTags(allCourses.getMechanical_Engineering());
        else if(title.getText().equals("Electrical Engineering") || title.getText().equals("הנדסת חשמל ואלקטרוניקה"))
            SetTags(allCourses.getElectrical_Engineering());
        else if(title.getText().equals("Software Engineering") || title.getText().equals("הנדסת תוכנה"))
            SetTags(allCourses.getSoftware_Engineering());
        else if(title.getText().equals("Industrial Engineering") || title.getText().equals("הנדסת תעשייה וניהול"))
            SetTags(allCourses.getIndustrial_Engineering());
        else if(title.getText().equals("Chemical Engineering") || title.getText().equals("הנדסת כימיה"))
            SetTags(allCourses.getChemical_Engineering());
        else if(title.getText().equals("Programming Computer ") || title.getText().equals("מדעי המחשב"))
            SetTags(allCourses.getPrograming_computer());
        else if(title.getText().equals("Pre Engineering") || title.getText().equals("מכינה"))
            SetTags(allCourses.getPre_Engineering());
    }
    private void setCourses(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference;
        if (getResources().getConfiguration().locale.getDisplayName().equals("Hebrew"))
            reference = database.getReference().child("Courses").child(getEngineeringName()).child("Hebrew");
        else
            reference = database.getReference().child("Courses").child(getEngineeringName()).child("English");
        reference.setValue(courses);
    }
    private String getEngineeringName(){
        if(Engineering.equals("Structural Engineering") || Engineering.equals("הנדסת בניין"))
            return "Structural Engineering";
        else if(Engineering.equals("Mechanical Engineering") || Engineering.equals("הנדסת מכונות"))
            return "Mechanical Engineering";
        else if(Engineering.equals("Electrical Engineering") || Engineering.equals("הנדסת חשמל ואלקטרוניקה"))
            return "Electrical Engineering";
        else if(Engineering.equals("Software Engineering") || Engineering.equals("הנדסת תוכנה"))
            return "Software Engineering";
        else if(Engineering.equals("Industrial Engineering") || Engineering.equals("הנדסת תעשייה וניהול"))
            return "Industrial Engineering";
        else if(Engineering.equals("Chemical Engineering") || Engineering.equals("הנדסת כימיה"))
            return "Chemical Engineering";
        else if(Engineering.equals("Programming Computer ") || Engineering.equals("מדעי המחשב"))
            return "Programming Engineering";
        else if(Engineering.equals("Pre Engineering") || Engineering.equals("מכינה"))
            return "Pre Engineering";
        return "Other";
    }
    private void SetTags(Course[] Courses){
        for(int i=0; i<Courses.length; i++)
            courses.add(Courses[i]);
        ShowTags(courses);
    }
    private void ShowTags(List<Course> selects){
        CourseView mySelects = new CourseView(this,selects,Engineering);
        mySelects.setUser(user);
        viewList.setLayoutManager(new GridLayoutManager(this,1));
        viewList.setAdapter(mySelects);
    }
    @Override
    public void onBackPressed() {
        intent = new Intent(GenericEngineering.this, SelectEngineering.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
}