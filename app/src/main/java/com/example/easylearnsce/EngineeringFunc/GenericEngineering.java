package com.example.easylearnsce.EngineeringFunc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.easylearnsce.Adapters.GenericEngineeringAdapter;
import com.example.easylearnsce.Class.RemoveCourseDialog;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuInfo;
import com.example.easylearnsce.Class.UserNavigationView;
import com.example.easylearnsce.R;
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
    private TextView Title, User_search;
    private User user = new User();
    private String Engineering = "Tag";
    private RecyclerView viewList;
    private NavigationView navigationView;
    private ArrayList<Course> courses;
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
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
        addCourse();
        removeCourse();
        setTags();
    }
    public void setID(){
        intent = getIntent();
        courses = new ArrayList<>();
        Title = findViewById(R.id.Title);
        addCourse = findViewById(R.id.addCourse);
        removeCourse = findViewById(R.id.removeCourse);
        User_search = findViewById(R.id.User_search);
        viewList = findViewById(R.id.CoursesRV);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        navigationView = findViewById(R.id.navigationView);
        Engineering = (String)intent.getSerializableExtra("title");
        Title.setText(Engineering);
        user = (User)intent.getSerializableExtra("user");
        user.setEngineering(Title.getText().toString());
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuInfo(user,GenericEngineering.this);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.ItemEngineering);
        item.setTitle(Title.getText());
        item.setVisible(true);
        item.setCheckable(false);
        item.setChecked(true);
        item.setEnabled(false);
        if(Engineering.equals("Structural Engineering") || Engineering.equals("הנדסת בניין"))
            item.setIcon(R.drawable.structural);
        else if(Engineering.equals("Mechanical Engineering") || Engineering.equals("הנדסת מכונות"))
            item.setIcon(R.drawable.mechanical);
        else if(Engineering.equals("Electrical Engineering") || Engineering.equals("הנדסת חשמל ואלקטרוניקה"))
            item.setIcon(R.drawable.electrical);
        else if(Engineering.equals("Software Engineering") || Engineering.equals("הנדסת תוכנה"))
            item.setIcon(R.drawable.software);
        else if(Engineering.equals("Industrial Engineering") || Engineering.equals("הנדסת תעשייה וניהול"))
            item.setIcon(R.drawable.industrial);
        else if(Engineering.equals("Chemical Engineering") || Engineering.equals("הנדסת כימיה"))
            item.setIcon(R.drawable.chemical);
        else if(Engineering.equals("Programming Computer") || Engineering.equals("מדעי המחשב"))
            item.setIcon(R.drawable.software);
        else if(Engineering.equals("Pre Engineering") || Engineering.equals("מכינה"))
            item.setIcon(R.drawable.mehina);
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
                    RemoveCourseDialog addCourseDialog = new RemoveCourseDialog();
                    addCourseDialog.show(getSupportFragmentManager(), "Remove Course");
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
    private void NavigationView(){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                new UserNavigationView(GenericEngineering.this, item.getItemId(), user);
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
    private String getEngineeringName(){
        if(Engineering.equals("Structural Tag") || Engineering.equals("הנדסת בניין"))
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
            return "Programming Computer";
        else if(Engineering.equals("Pre Engineering") || Engineering.equals("מכינה"))
            return "Pre Engineering";
        return "Other";
    }
    private void setTags(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Courses").child(getEngineeringName()).child(getEngineeringName());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                courses.clear();
                for(DataSnapshot data : snapshot.getChildren()){
                    Course course = data.getValue(Course.class);
                    courses.add(course);
                }
                ShowTags(courses);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void ShowTags(List<Course> selects){
        GenericEngineeringAdapter mySelects = new GenericEngineeringAdapter(this,selects,Engineering);
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