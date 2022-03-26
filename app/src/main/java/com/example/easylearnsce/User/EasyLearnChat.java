package com.example.easylearnsce.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.UserMenuInfo;
import com.example.easylearnsce.Class.UserNavigationView;
import com.example.easylearnsce.Fragments.ChatsFragment;
import com.example.easylearnsce.Fragments.UsersFragment;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class EasyLearnChat extends AppCompatActivity {
    private User user = new User();
    private TextView Title;
    private ImageView BackIcon, MenuIcon;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPagerAdapter fragmentPager;
    private androidx.drawerlayout.widget.DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Intent intent;
    private String[] titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy_learn_chat);
        init();
    }
    private void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
    }
    private void setID(){
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        tabLayout = findViewById(R.id.tabLayout);
        viewPager2 = findViewById(R.id.viewPager);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        navigationView = findViewById(R.id.navigationView);
        Title = findViewById(R.id.Title);
        Title.setText(getResources().getString(R.string.EasyLearnChat));
        drawerLayout = findViewById(R.id.drawerLayout);
        fragmentPager = new ViewPagerAdapter(EasyLearnChat.this);
        viewPager2.setAdapter(fragmentPager);
        titles = new String[2];
        titles[0] = getResources().getString(R.string.Chats);
        titles[1] = getResources().getString(R.string.Users);
        new TabLayoutMediator(tabLayout,viewPager2,((tab, position) -> tab.setText(titles[position]))).attach();
        new UserMenuInfo(user,EasyLearnChat.this);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem menuItem = menu.findItem(R.id.ItemEasyLearnChat);
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
                new UserNavigationView(EasyLearnChat.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon(){
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(Home.class); }
        });
    }
    private void StartActivity(Class Destination){
        intent = new Intent(EasyLearnChat.this, Destination);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    public void onBackPressed() {
        intent = new Intent(EasyLearnChat.this, Home.class);
        intent.putExtra("user", user);
        startActivity(intent);
        finish();
    }
    public static class ViewPagerAdapter extends FragmentStateAdapter {
        private String[] titles = {"Chats","Users"};
        public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return  new ChatsFragment();
                case 1:
                    return new UsersFragment();
            }
            return new ChatsFragment();
        }
        @Override
        public int getItemCount() { return titles.length; }
    }
}