package com.example.easylearnsce.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.Lecture;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Class.YouTubeConfig;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.AllRequests;
import com.example.easylearnsce.User.ChangePassword;
import com.example.easylearnsce.User.EasyLearnChat;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Profile;
import com.example.easylearnsce.User.Requests;
import com.example.easylearnsce.User.SelectEngineering;
import com.example.easylearnsce.User.YouTubePlayer;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class LecturesFragmentAdapter extends RecyclerView.Adapter<LecturesFragmentAdapter.MyViewHolder>{
    private Context context;
    private List<Lecture> Select;
    private User user;
    private String CourseID;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public LecturesFragmentAdapter(Context context, List<Lecture> select, User user, String CourseID) {
        this.context = context;
        this.Select = select;
        this.user = user;
        this.CourseID = CourseID;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView LectureName;
        CardView cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LectureName = itemView.findViewById(R.id.LectureName);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
    public LecturesFragmentAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.row,parent,false);
        return new LecturesFragmentAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull LecturesFragmentAdapter.MyViewHolder holder, int position) {
        holder.LectureName.setText(Select.get(position).getLectureName());
        holder.LectureName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setLecture(Select.get(position).getLectureName());
                intent = new Intent(context, YouTubePlayer.class);
                intent.putExtra("user", user);
                intent.putExtra("Video", Select.get(position).getUrl());
                intent.putExtra("CourseID", CourseID);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }
    public int getItemCount() { return Select.size(); }
}
