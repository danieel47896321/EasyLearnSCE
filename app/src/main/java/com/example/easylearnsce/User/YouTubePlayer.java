package com.example.easylearnsce.User;

import androidx.annotation.NonNull;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;

public class YouTubePlayer extends YouTubeBaseActivity {
    private User user = new User();
    private TextView Title;
    private ImageView BackIcon, MenuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private RecyclerView recyclerView;
    private Intent intent;
    private String Video = "";
    private String CourseID = "";
    private YouTubePlayerView youTubePlayerView;
    private com.google.android.youtube.player.YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_you_tube_player);
        init();
    }
    private void init(){
        setID();
        setYouTubePlayer();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
    }
    private void setID(){
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        Video = (String)intent.getSerializableExtra("Video");
        CourseID = (String)intent.getSerializableExtra("CourseID");
        recyclerView = findViewById(R.id.recyclerView);
        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        Title = findViewById(R.id.Title);
        Title.setText(user.getEngineering()+"\n"+user.getCourse()+"\n"+user.getLecture());
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuInfo(user,YouTubePlayer.this);
    }
    private void setYouTubePlayer(){
        onInitializedListener = new com.google.android.youtube.player.YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer.Provider provider, com.google.android.youtube.player.YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(Video);
            }

            @Override
            public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) { }
        };
        youTubePlayerView.initialize("AIzaSyCwgouOE-EGANd6yUk8NxgJCag7may6Iqc", onInitializedListener);
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.ItemCourse) , item1 = menu.findItem(R.id.ItemEngineering), item2 = menu.findItem(R.id.ItemLecture) ;
        item.setTitle(user.getCourse());
        item.setVisible(true);
        item1.setTitle(user.getEngineering());
        item1.setVisible(true);
        item2.setTitle(user.getLecture());
        item2.setVisible(true);
        item2.setCheckable(false);
        item2.setChecked(true);
        item2.setEnabled(false);
        if(user.getEngineering().equals("Structural Engineering") || user.getEngineering().equals("הנדסת בניין"))
            item1.setIcon(R.drawable.structural);
        else if(user.getEngineering().equals("Mechanical Engineering") || user.getEngineering().equals("הנדסת מכונות"))
            item1.setIcon(R.drawable.mechanical);
        else if(user.getEngineering().equals("Electrical Engineering") || user.getEngineering().equals("הנדסת חשמל ואלקטרוניקה"))
            item1.setIcon(R.drawable.electrical);
        else if(user.getEngineering().equals("Software Engineering") || user.getEngineering().equals("הנדסת תוכנה"))
            item1.setIcon(R.drawable.software);
        else if(user.getEngineering().equals("Industrial Engineering") || user.getEngineering().equals("הנדסת תעשייה וניהול"))
            item1.setIcon(R.drawable.industrial);
        else if(user.getEngineering().equals("Chemical Engineering") || user.getEngineering().equals("הנדסת כימיה"))
            item1.setIcon(R.drawable.chemical);
        else if(user.getEngineering().equals("Programming Computer") || user.getEngineering().equals("מדעי המחשב"))
            item1.setIcon(R.drawable.software);
        else if(user.getEngineering().equals("Pre Engineering") || user.getEngineering().equals("מכינה"))
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
                new UserNavigationView(YouTubePlayer.this, item.getItemId(), user);
                return false;
            }
        });
    }
    private void BackIcon() {
        BackIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { StartActivity(GenericCourse.class); }
        });
    }
    @Override
    public void onBackPressed() { StartActivity(GenericCourse.class); }
    private void StartActivity(Class Destination){
        intent = new Intent(YouTubePlayer.this, Destination);
        intent.putExtra("user", user);
        intent.putExtra("Course", user.getCourse());
        intent.putExtra("CourseID", CourseID);
        startActivity(intent);
        finish();
    }
}
