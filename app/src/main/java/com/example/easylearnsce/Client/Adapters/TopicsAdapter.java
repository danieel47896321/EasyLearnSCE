package com.example.easylearnsce.Client.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easylearnsce.Client.Class.Topic;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.User.Message;
import com.example.easylearnsce.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TopicsAdapter extends RecyclerView.Adapter<TopicsAdapter.ViewHolder> {
    private Context context;
    private String CourseID, LectureNumber, Type, Engineering;
    private ArrayList<Topic> topics;
    public TopicsAdapter(Context context, ArrayList<Topic> topics, String CourseID, String LectureNumber, String Type, String Engineering){
        this.context = context;
        this.topics = topics;
        this.CourseID = CourseID;
        this.LectureNumber = LectureNumber;
        this.Type = Type;
        this.Engineering = Engineering;
    }
    @NonNull
    @Override
    public TopicsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.topic_view, parent, false);
        return new TopicsAdapter.ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull TopicsAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Topic topic = topics.get(position);
        holder.Topic.setText(topic.getTopic());
        holder.Time.setText(topic.getEndTime() + " - " + topic.getStartTime());
        holder.RemoveTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("Courses").child(Engineering).child(CourseID).child(Type).child(LectureNumber).child("topics").child(position+"");
                data.setValue(null);
            }
        });
    }
    @Override
    public int getItemCount() { return topics.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView Topic;
        public TextView Time;
        public ImageView RemoveTopic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Topic = itemView.findViewById(R.id.Topic);
            Time = itemView.findViewById(R.id.Time);
            RemoveTopic = itemView.findViewById(R.id.RemoveTopic);
        }
    }
}