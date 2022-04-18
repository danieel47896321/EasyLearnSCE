package com.example.easylearnsce.Client.User;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.easylearnsce.Client.Adapters.TopicsAdapter;
import com.example.easylearnsce.Client.Adapters.YouTubeTopicChatAdapter;
import com.example.easylearnsce.Client.Class.Lecture;
import com.example.easylearnsce.Client.Class.Topic;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.Class.UserMenuInfo;
import com.example.easylearnsce.Client.Class.UserNavigationView;
import com.example.easylearnsce.Client.Class.YouTubeMessage;
import com.example.easylearnsce.R;
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

import java.util.ArrayList;

public class GenericYouTubePlayer extends YouTubeBaseActivity {
    private User user = new User();
    private TextView Title, EditTopics, EditURL, StartTime, EndTime;
    private EditText TextSend, LectureTopic, Seconds, Hours, Minutes;
    private RecyclerView recyclerView;
    private ImageView BackIcon, MenuIcon, ButtonSend, Add;
    private RecyclerView recyclerViewChat;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button ButtonCancel, ButtonAddVideo, Finish;
    private TextInputLayout TextInputLayoutLinkToVideo;
    private ArrayList<YouTubeMessage> youTubeMessages;
    private Context context;
    private Intent intent;
    private String Video = "";
    private String CourseID = "";
    private String LectureNumber = "";
    private String TabPosition = 0+"";
    private String Type = "";
    private YouTubePlayerView youTubePlayerView;
    private com.google.android.youtube.player.YouTubePlayer mYouTubePlayer;
    private TabLayout tabLayout;
    private ArrayList<Topic> topics;
    private LinearLayout linearLayout;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    private Lecture lecture;
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
        Video = (String)intent.getSerializableExtra("Video");
        CourseID = (String)intent.getSerializableExtra("CourseID");
        lecture = (Lecture) intent.getSerializableExtra("Lecture");
        int index = lecture.getLectureName().indexOf(' ');
        if(lecture.getLectureName().equals("Lecture"))
            Type = "Lectures";
        else
            Type = "Exercises";
        LectureNumber = lecture.getNumber();
        TextSend = findViewById(R.id.TextSend);
        recyclerViewChat = findViewById(R.id.recyclerViewChat);
        ButtonSend = findViewById(R.id.ButtonSend);
        youTubePlayerView = findViewById(R.id.youTubePlayerView);
        linearLayout = findViewById(R.id.linearLayout);
        EditTopics = findViewById(R.id.EditTopics);
        EditURL = findViewById(R.id.EditURL);
        MenuIcon = findViewById(R.id.MenuIcon);
        BackIcon = findViewById(R.id.BackIcon);
        Title = findViewById(R.id.Title);
        Title.setText(user.getEngineering()+"\n"+user.getCourse()+"\n"+user.getLecture());
        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        new UserMenuInfo(user, GenericYouTubePlayer.this);
        tabLayout = findViewById(R.id.tabLayout);
        if(user.getType().equals("Admin") || user.getType().equals("אדמין") || user.getType().equals("מרצה") || user.getType().equals("Teacher") )
            setAddAndEdit();
    }
    private void setAddAndEdit(){
        linearLayout.setVisibility(View.VISIBLE);
        EditTopics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditTopicsDialog();
            }
        });
        EditURL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddVideoDialog();
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
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    DatabaseReference mUserRef = firebaseDatabase.getReference().child(Type).child(getEngineeringName()).child(CourseID);
                    mUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            int counter = 1, index = 1 ;
                            for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                                Lecture lecture2 = dataSnapshot.getValue(Lecture.class);
                                if((lecture2.getLectureName() + " " + lecture2.getNumber()).equals(lecture.getLectureName() + " " + lecture.getNumber()))
                                    index = counter;
                                counter++;
                            }
                            Video = TextInputLayoutLinkToVideo.getEditText().getText().toString();
                            mYouTubePlayer.cueVideo(Video);
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference(Type).child(getEngineeringName()).child(CourseID).child(index+"").child("url");
                            reference1.setValue(Video);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) { }
                    });
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
        alertDialog.show();
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child(Type).child(getEngineeringName()).child(CourseID).child(LectureNumber);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lecture lecture1 = snapshot.getValue(Lecture.class);
                topics.clear();
                for(int i=0; i<lecture1.getTopics().size();i++)
                    topics.add(lecture1.getTopics().get(i));
                lecture.setTopics(lecture1.getTopics());
                TopicsAdapter topicsAdapter = new TopicsAdapter(dialogView.getContext(),topics,CourseID,LectureNumber,Type, getEngineeringName());
                recyclerView.setLayoutManager(new GridLayoutManager(dialogView.getContext(),1));
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
                    Topic topic = new Topic(LectureTopic.getText().toString(), StartTime.getText().toString(), EndTime.getText().toString());
                    lecture.AddTopic(topic);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Type).child(getEngineeringName()).child(CourseID).child(LectureNumber).child("topics");
                    databaseReference.setValue(lecture.getTopics());
                }
            }
        });
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
        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child(Type).child(getEngineeringName()).child(CourseID).child(LectureNumber);
        data.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Lecture lecture1 = snapshot.getValue(Lecture.class);
                topics.clear();
                for(int i=0; i<lecture1.getTopics().size();i++)
                    topics.add(lecture1.getTopics().get(i));
                lecture.setTopics(lecture1.getTopics());
                tabLayout.removeAllTabs();
                for (int i = 0; i < lecture.getTopics().size(); i++) {
                    tabLayout.addTab(tabLayout.newTab(), i);
                    tabLayout.getTabAt(i).setText(lecture.getTopics().get(i).getTopic() + "\n" + lecture.getTopics().get(i).getStartTime() + " - " + lecture.getTopics().get(i).getEndTime());
                }
                context = getBaseContext();
                setSendMessage();
                setTabChat();
                tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        TabPosition = tab.getPosition()+"";
                        setTabChat();
                    }
                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) { }
                    @Override
                    public void onTabReselected(TabLayout.Tab tab) { }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });
    }
    private void setTabChat(){
        DatabaseReference getData = FirebaseDatabase.getInstance().getReference().child(Type+" Chats").child(getEngineeringName()).child(CourseID).child(LectureNumber).child(TabPosition);
        getData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                youTubeMessages.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    YouTubeMessage youTubeMessage = dataSnapshot.getValue(YouTubeMessage.class);
                    youTubeMessages.add(youTubeMessage);
                }
                ArrayList<YouTubeMessage> reverse = new ArrayList<>();
                for(int i=0; i< youTubeMessages.size(); i++)
                    reverse.add(youTubeMessages.get(youTubeMessages.size()-i-1));
                YouTubeTopicChatAdapter youTubeTopicChatAdapter = new YouTubeTopicChatAdapter(context, reverse);
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
                if(TextSend.getText().toString().length() > 0){
                    youTubeMessages.add(new YouTubeMessage(user.getFullName(), user.getImage(), TextSend.getText().toString()));
                    DatabaseReference message = FirebaseDatabase.getInstance().getReference().child(Type+" Chats").child(getEngineeringName()).child(CourseID).child(LectureNumber).child(TabPosition);
                    message.setValue(youTubeMessages);
                    TextSend.setText("");
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
        String lectureName = "Lecture";
        if(lecture.getLectureName().equals("Lecture"))
            lectureName = getResources().getString(R.string.Lecture);
        else
            lectureName = getResources().getString(R.string.Exercise);
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
                new UserNavigationView(GenericYouTubePlayer.this, item.getItemId(), user);
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
        intent.putExtra("Course", user.getCourse());
        intent.putExtra("CourseID", CourseID);
        startActivity(intent);
        finish();
    }
}
