package com.example.easylearnsce.Client.User;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.Class.UserMenuInfo;
import com.example.easylearnsce.Client.Class.UserNavigationView;
import com.example.easylearnsce.Client.Fragments.ExercisesFragment;
import com.example.easylearnsce.Client.Fragments.LecturesFragment;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class GenericCourse extends FragmentActivity {
    private User user = new User();
    private TextView Title;
    private ImageView BackIcon, MenuIcon;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter fragmentPager;
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private String Course, Engineering, CourseID;
    private Intent intent;
    private String[] titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_course);
        init();
    }
    public void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
    }
    private void setID(){
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        Course = (String)intent.getSerializableExtra("Course");
        CourseID = (String)intent.getSerializableExtra("CourseID");
        Engineering = user.getEngineering();
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        navigationView = findViewById(R.id.navigationView);
        Title = findViewById(R.id.Title);
        Title.setText(Engineering+"\n"+Course);
        user.setCourse(Course);
        drawerLayout = findViewById(R.id.drawerLayout);
        fragmentPager = new ViewPagerAdapter(GenericCourse.this,user,CourseID);
        viewPager2.setAdapter(fragmentPager);
        titles = new String[4];
        titles[0] = getResources().getString(R.string.Lectures);
        titles[1] = getResources().getString(R.string.Exercises);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();
        new UserMenuInfo(user,GenericCourse.this);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.ItemCourse) , item1 = menu.findItem(R.id.ItemEngineering) ;
        item.setTitle(Course);
        item1.setTitle(Engineering);
        item1.setVisible(true);
        item.setVisible(true);
        item.setCheckable(false);
        item.setChecked(true);
        item.setEnabled(false);
        if(Engineering.equals("Structural Engineering") || Engineering.equals("הנדסת בניין"))
            item1.setIcon(R.drawable.structural);
        else if(Engineering.equals("Mechanical Engineering") || Engineering.equals("הנדסת מכונות"))
            item1.setIcon(R.drawable.mechanical);
        else if(Engineering.equals("Electrical Engineering") || Engineering.equals("הנדסת חשמל ואלקטרוניקה"))
            item1.setIcon(R.drawable.electrical);
        else if(Engineering.equals("Software Engineering") || Engineering.equals("הנדסת תוכנה"))
            item1.setIcon(R.drawable.software);
        else if(Engineering.equals("Industrial Engineering") || Engineering.equals("הנדסת תעשייה וניהול"))
            item1.setIcon(R.drawable.industrial);
        else if(Engineering.equals("Chemical Engineering") || Engineering.equals("הנדסת כימיה"))
            item1.setIcon(R.drawable.chemical);
        else if(Engineering.equals("Programming Computer") || Engineering.equals("מדעי המחשב"))
            item1.setIcon(R.drawable.software);
        else if(Engineering.equals("Pre Engineering") || Engineering.equals("מכינה"))
            item1.setIcon(R.drawable.mehina);
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
                new UserNavigationView(GenericCourse.this, item.getItemId(), user);
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
    public void onBackPressed() {
        intent = new Intent(GenericCourse.this, GenericEngineering.class);
        intent.putExtra("user", user);
        intent.putExtra("title", Engineering);
        startActivity(intent);
        finish();
    }
    public static class ViewPagerAdapter extends FragmentStateAdapter {
        private String[] titles = {"Lectures", "Exercises"};
        private User user;
        private String CourseID;
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, User user, String CorseID) {
            super(fragmentActivity);
            this.user = user;
            this.CourseID = CorseID;
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    LecturesFragment lecturesFragment = new LecturesFragment();
                    lecturesFragment.setUser(user);
                    lecturesFragment.setCourseID(CourseID);
                    return lecturesFragment;
                case 1:
                    ExercisesFragment exercisesFragment = new ExercisesFragment();
                    exercisesFragment.setUser(user);
                    exercisesFragment.setCourseID(CourseID);
                    return exercisesFragment;
            }
            return new LecturesFragment();
        }
        @Override
        public int getItemCount() { return titles.length; }
    }
}