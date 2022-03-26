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
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.ChangePassword;
import com.example.easylearnsce.User.EasyLearnChat;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.AllRequests;
import com.example.easylearnsce.User.Requests;
import com.example.easylearnsce.User.Profile;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomeSelectAdapter extends RecyclerView.Adapter<HomeSelectAdapter.MyViewHolder> {
    private Context context;
    private List<Tag> Select;
    private User user;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public HomeSelectAdapter(Context context, List<Tag> select) {
        this.context = context;
        this.Select = select;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TagName;
        ImageView TagPhoto;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TagName = itemView.findViewById(R.id.TagName);
            TagPhoto = itemView.findViewById(R.id.TagPhoto);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
    public void setUser(User user){ this.user = user; }
    public HomeSelectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tag_view,parent,false);
        return new HomeSelectAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull HomeSelectAdapter.MyViewHolder holder, int position) {
        holder.TagName.setText(Select.get(position).getTagName());
        holder.TagPhoto.setImageResource(Select.get(position).getPhoto());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                intent = new Intent(context, Home.class);
                //Home
                 if(holder.TagName.getText().equals(context.getResources().getString(R.string.SelectEngineering)))
                    intent = new Intent(context, SelectEngineering.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.EasyLearnChat)))
                    intent = new Intent(context, EasyLearnChat.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.Profile)))
                    intent = new Intent(context, Profile.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.ChangePassword)))
                    intent = new Intent(context, ChangePassword.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.Requests)))
                    intent = new Intent(context, Requests.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.AllRequests)))
                    intent = new Intent(context, AllRequests.class);
                if(holder.TagName.getText().equals(context.getResources().getString(R.string.SignOut))) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle(context.getResources().getString(R.string.SignOut)).setMessage(context.getResources().getString(R.string.AreYouSure)).setCancelable(true).setPositiveButton(context.getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(firebaseAuth.getCurrentUser() != null)
                                firebaseAuth.signOut();
                            intent = new Intent(context, EasyLearnSCE.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }).setNegativeButton(context.getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = context.getResources();
                            holder.constraintLayout.setBackground( ResourcesCompat.getDrawable(res, R.drawable.background, null));
                        }
                    }).show();
                    intent = new Intent(context, EasyLearnSCE.class);
                }
                else {
                    intent.putExtra("user", user);
                    context.startActivity(intent);
                    ((Activity) context).finish();
                }
            }
        });
    }
    public int getItemCount() { return Select.size(); }
}