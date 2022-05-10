package com.example.easylearnsce.Client.User;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easylearnsce.Client.Adapters.TopicsAdapter;
import com.example.easylearnsce.Client.Adapters.YouTubeTopicChatAdapter;
import com.example.easylearnsce.Client.Class.Course;
import com.example.easylearnsce.Client.Class.Lecture;
import com.example.easylearnsce.Client.Class.PopUpMSG;
import com.example.easylearnsce.Client.Class.Topic;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.Class.UserMenuInfo;
import com.example.easylearnsce.Client.Class.UserNavigationView;
import com.example.easylearnsce.Client.Class.YouTubeMessage;
import com.example.easylearnsce.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class GenericYouTubePlayer extends YouTubeBaseActivity {
    private User user = new User();
    private TextView Title,StartTime, EndTime;
    private EditText TextSend, LectureTopic, Seconds, Hours, Minutes;
    private RecyclerView recyclerView;
    private ImageView BackIcon, MenuIcon, ButtonSend, Add;
    private RecyclerView recyclerViewChat;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private FloatingActionButton floatingActionButtonOpen;
    private ExtendedFloatingActionButton floatingActionButtonEditTopics, floatingActionButtonEditUrl;
    private Animation rotateOpen, rotateClose, toBottom, fromBottom;
    private Boolean isOpen = false;
    private Button ButtonCancel, ButtonAddVideo, Finish;
    private TextInputLayout TextInputLayoutLinkToVideo;
    private ArrayList<YouTubeMessage> youTubeMessages;
    private Context context;
    private Intent intent;
    private String Video = "";
    private String listType = "lectures";
    private int LectureNumber;
    private int messageID = 0;
    private String Type = "";
    private String TabPosition = 0+"";
    private String lectureName = "Lecture";
    private YouTubePlayerView youTubePlayerView;
    private com.google.android.youtube.player.YouTubePlayer mYouTubePlayer;
    private TabLayout tabLayout;
    private ArrayList<Topic> topics;
    private LinearLayout linearLayout;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private Lecture lecture;
    private Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_you_tube_player);
        init();
    }
    private void init(){
        setID();
        MenuItem();
        BackIcon();
        MenuIcon();
        NavigationView();
        setYouTubePlayer();
    }
    private void setID(){
        topics = new ArrayList<>();
        youTubeMessages = new ArrayList<>();
        intent = getIntent();
        user = (User)intent.getSerializableExtra("user");
        course = (Course)intent.getSerializableExtra("Course");
        lecture = (Lecture) intent.getSerializableExtra("Lecture");
        Type = (String) intent.getSerializableExtra("Type");
        Video = lecture.getUrl();
        floatingActionButtonOpen = findViewById(R.id.floatingActionButtonOpen);
        floatingActionButtonEditTopics = findViewById(R.id.floatingActionButtonEditTopics);
        floatingActionButtonEditUrl = findViewById(R.id.floatingActionButtonEditUrl);
        if(Type.equals("Lecture"))
            lectureName = getResources().getString(R.string.Lecture);
        else {
            lectureName = getResources().getString(R.string.Exercise);
            listType = "exercises";
        }
        LectureNumber = lecture.getNumber();
        TextSend = findViewById(R.id.TextSend);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        ButtonSend = findViewById(R.id.ButtonSend);
        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        linearLayout = findViewById(R.id.linearLayout);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        Title = findViewById(R.id.Title);
        Title.setText(user.getEngineering()+"\n"+user.getCourse()+"\n"+lectureName + " " + lecture.getNumber());
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuInfo(user, GenericYouTubePlayer.this);
        tabLayout = findViewById(R.id.tabLayout);
        if(user.getType().equals("Admin") || user.getType().equals("אדמין") || (user.getType().equals("Teacher") && user.getFullName().equals(course.getTeacherName())) || (user.getType().equals("מרצה") && user.getFullName().equals(course.getTeacherName())))
            setAddAndEdit();
    }
    private void setYouTubePlayer(){
        onInitializedListener = new com.google.android.youtube.player.YouTubePlayer.OnInitializedListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInitializationSuccess(com.google.android.youtube.player.YouTubePlayer.Provider provider, com.google.android.youtube.player.YouTubePlayer youTubePlayer, boolean b) {
                mYouTubePlayer = youTubePlayer;
                mYouTubePlayer.cueVideo(Video);
                setPager();
            }
            @Override
            public void onInitializationFailure(com.google.android.youtube.player.YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) { }
        };
        youTubePlayerView.initialize("AIzaSyCwgouOE-EGANd6yUk8NxgJCag7may6Iqc", onInitializedListener);
    }
    private void setPager(){
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Courses").child(getEngineeringName()).child(course.getId()).child(listType).child(lecture.getNumber()+"").child("topics");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topics.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Topic topic = dataSnapshot.getValue(Topic.class);
                    topics.add(topic);
                }
                tabLayout.removeAllTabs();
                for (int i = 0; i < topics.size(); i++) {
                    tabLayout.addTab(tabLayout.newTab(), i);
                    tabLayout.getTabAt(i).setText(topics.get(i).getTopic() + "\n" + topics.get(i).getStartTime() + " - " + topics.get(i).getEndTime());
                }
                context = getBaseContext();
                setSendMessage();
                setTabChat();
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        TabPosition = tab.getPosition() + "";
                        setTabChat();
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {}
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {}
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void setAddAndEdit(){
        floatingActionButtonOpen.setVisibility(View.VISIBLE);
        context = this;
        floatingActionButtonOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpen = !isOpen;
                rotateOpen = AnimationUtils.loadAnimation(context,R.anim.rotate_open);
                rotateClose = AnimationUtils.loadAnimation(context,R.anim.rotate_close);
                fromBottom = AnimationUtils.loadAnimation(context,R.anim.from_bottom);
                toBottom = AnimationUtils.loadAnimation(context,R.anim.to_bottom);
                if (isOpen) {
                    floatingActionButtonEditTopics.setVisibility(View.VISIBLE);
                    floatingActionButtonEditUrl.setVisibility(View.VISIBLE);
                    floatingActionButtonEditTopics.setAnimation(fromBottom);
                    floatingActionButtonEditUrl.setAnimation(fromBottom);
                    floatingActionButtonOpen.setAnimation(rotateOpen);
                    floatingActionButtonEditTopics.setClickable(true);
                    floatingActionButtonEditUrl.setClickable(true);
                    floatingActionButtonEditTopics.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                EditTopicsDialog();
                            }
                    });
                    floatingActionButtonEditUrl.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                AddVideoDialog();
                            }
                    });
                } else {
                    floatingActionButtonEditTopics.setVisibility(View.INVISIBLE);
                    floatingActionButtonEditUrl.setVisibility(View.INVISIBLE);
                    floatingActionButtonEditTopics.setAnimation(toBottom);
                    floatingActionButtonEditUrl.setAnimation(toBottom);
                    floatingActionButtonOpen.setAnimation(rotateClose);
                    floatingActionButtonEditTopics.setClickable(false);
                    floatingActionButtonEditUrl.setClickable(false);
                }
            }
        });
    }
    private void TimePickerDialog(TextView Time){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.time_picker_view,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        Seconds = dialogView.findViewById(R.id.Seconds);
        Minutes = dialogView.findViewById(R.id.Minutes);
        Hours = dialogView.findViewById(R.id.Hours);
        Finish = dialogView.findViewById(R.id.Finish);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        Finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Seconds.getText().toString().length() == 0)
                    Seconds.setText("00");
                else if(Seconds.getText().toString().length() == 1)
                    Seconds.setText("0" + Seconds.getText().toString());
                if(Minutes.getText().toString().length() == 0)
                    Minutes.setText("00");
                else if(Minutes.getText().toString().length() == 1)
                    Minutes.setText("0" + Minutes.getText().toString());
                if(Hours.getText().toString().length() == 0)
                    Hours.setText("00");
                else if(Hours.getText().toString().length() == 1)
                    Hours.setText("0" + Hours.getText().toString());
                Time.setText(Hours.getText().toString() + ":" + Minutes.getText().toString() + ":" + Seconds.getText().toString());
                alertDialog.cancel();
            }
        });
    }
    private void AddVideoDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_video,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        TextInputLayoutLinkToVideo = dialogView.findViewById(R.id.TextInputLayoutLinkToVideo);
        ButtonAddVideo = dialogView.findViewById(R.id.ButtonAddVideo);
        ButtonAddVideo.setText(this.getResources().getString(R.string.EditUrl));
        ButtonCancel = dialogView.findViewById(R.id.ButtonCancel);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        ButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { alertDialog.cancel(); }
        });
        ButtonAddVideo.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                if(TextInputLayoutLinkToVideo.getEditText().getText().toString().equals(""))
                    TextInputLayoutLinkToVideo.setHelperText(getResources().getString(R.string.Required));
                else
                    TextInputLayoutLinkToVideo.setHelperText("");
                if(!(TextInputLayoutLinkToVideo.getEditText().getText().toString().equals(""))){
                    alertDialog.cancel();
                    Video = TextInputLayoutLinkToVideo.getEditText().getText().toString();
                    mYouTubePlayer.cueVideo(Video);
                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Courses").child(getEngineeringName()).child(course.getId()).child(listType).child(lecture.getNumber()+"").child("url");
                    reference.setValue(Video);
                }
            }
        });
    }
    private void EditTopicsDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_edit_topics,null);
        builder.setCancelable(false);
        builder.setView(dialogView);
        LectureTopic = dialogView.findViewById(R.id.Topic);
        StartTime = dialogView.findViewById(R.id.StartTime);
        EndTime = dialogView.findViewById(R.id.EndTime);
        Add = dialogView.findViewById(R.id.Add);
        recyclerView = dialogView.findViewById(R.id.recyclerView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        alertDialog.show();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Courses").child(getEngineeringName()).child(course.getId()).child(listType).child(lecture.getNumber()+"").child("topics");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                topics.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Topic topic = dataSnapshot.getValue(Topic.class);
                    topics.add(topic);
                }
                TopicsAdapter topicsAdapter = new TopicsAdapter(dialogView.getContext(), topics, course.getId(), LectureNumber+"", listType, getEngineeringName());
                recyclerView.setLayoutManager(new GridLayoutManager(dialogView.getContext(), 1));
                recyclerView.setAdapter(topicsAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
        StartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog(StartTime);
            }
        });
        EndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog(EndTime);
            }
        });
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(LectureTopic.getText().length() > 0 && StartTime.getText().length() > 0 && EndTime.getText().length() > 0) {
                    int id = 0;
                    for(int i=0;i<topics.size();i++)
                        if(topics.get(i).getId().equals(id+""))
                            id++;
                    Topic topic = new Topic(LectureTopic.getText().toString(), StartTime.getText().toString(), EndTime.getText().toString(), id+"");
                    DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Courses").child(getEngineeringName()).child(course.getId()).child(listType).child(lecture.getNumber()+"").child("topics").child(id+"");
                    data.setValue(topic);
                }
            }
        });
    }
    private void setTabChat(){
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Courses").child(getEngineeringName()).child(course.getId()).child(listType).child(LectureNumber+"").child("topics").child(TabPosition).child("messages");
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                youTubeMessages.clear();
                messageID = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    YouTubeMessage youTubeMessage = dataSnapshot.getValue(YouTubeMessage.class);
                    youTubeMessages.add(youTubeMessage);
                    messageID++;
                }
                tabLayout.setScrollPosition(Integer.valueOf(TabPosition),0f,true);
                ArrayList<YouTubeMessage> reverse = new ArrayList<>();
                for(int i=0; i< youTubeMessages.size(); i++)
                    reverse.add(youTubeMessages.get(youTubeMessages.size()-i-1));
                YouTubeTopicChatAdapter youTubeTopicChatAdapter = new YouTubeTopicChatAdapter(context, reverse,user);
                recyclerViewChat.setLayoutManager(new GridLayoutManager(context,1));
                recyclerViewChat.setAdapter(youTubeTopicChatAdapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void setSendMessage(){
        ButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tabLayout.getTabCount() > 0) {
                    if (TextSend.getText().toString().length() > 0) {
                        String currentDateandTime = new SimpleDateFormat("HH:mm dd-MM-yyyy").format(new Date());
                        DatabaseReference message = FirebaseDatabase.getInstance().getReference().child("Courses").child(getEngineeringName()).child(course.getId()).child(listType).child(LectureNumber+"").child("topics").child(TabPosition).child("messages").child(messageID+"");
                        message.setValue(new YouTubeMessage(user.getFullName(), user.getImage(), TextSend.getText().toString(), currentDateandTime, user.getUid()));
                        TextSend.setText("");
                    }
                }
            }
        });
    }
    private void MenuItem(){
        Menu menu= navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.ItemCourse) , item1 = menu.findItem(R.id.ItemEngineering), item2 = menu.findItem(R.id.ItemLecture) ;
        item.setTitle(user.getCourse());
        item.setVisible(true);
        item1.setTitle(user.getEngineering());
        item1.setVisible(true);
        item2.setTitle(lectureName + " " + lecture.getNumber());
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
    private String getEngineeringName(){
        if(user.getEngineering().equals("Structural Tag") || user.getEngineering().equals("הנדסת בניין"))
            return "Structural Engineering";
        else if(user.getEngineering().equals("Mechanical Engineering") || user.getEngineering().equals("הנדסת מכונות"))
            return "Mechanical Engineering";
        else if(user.getEngineering().equals("Electrical Engineering") || user.getEngineering().equals("הנדסת חשמל ואלקטרוניקה"))
            return "Electrical Engineering";
        else if(user.getEngineering().equals("Software Engineering") || user.getEngineering().equals("הנדסת תוכנה"))
            return "Software Engineering";
        else if(user.getEngineering().equals("Industrial Engineering") || user.getEngineering().equals("הנדסת תעשייה וניהול"))
            return "Industrial Engineering";
        else if(user.getEngineering().equals("Chemical Engineering") || user.getEngineering().equals("הנדסת כימיה"))
            return "Chemical Engineering";
        else if(user.getEngineering().equals("Programming Computer ") || user.getEngineering().equals("מדעי המחשב"))
            return "Programming Computer";
        else if(user.getEngineering().equals("Pre Engineering") || user.getEngineering().equals("מכינה"))
            return "Pre Engineering";
        return "Other";
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
                new UserNavigationView(GenericYouTubePlayer.this, item.getItemId(), user, course);
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
        intent = new Intent(GenericYouTubePlayer.this, Destination);
        intent.putExtra("user", user);
        intent.putExtra("Course", course);
        startActivity(intent);
        finish();
    }
}
