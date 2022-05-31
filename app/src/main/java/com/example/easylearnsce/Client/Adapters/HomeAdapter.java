package com.example.easylearnsce.Client.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Client.Class.Tag;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.Client.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.Client.User.ChangePassword;
import com.example.easylearnsce.Client.User.EasyLearnChat;
import com.example.easylearnsce.Client.User.Home;
import com.example.easylearnsce.Client.User.AllRequests;
import com.example.easylearnsce.Client.User.Requests;
import com.example.easylearnsce.Client.User.Profile;
import com.example.easylearnsce.Client.User.SelectEngineering;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    private List<Tag> tags;
    private User user;
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public HomeAdapter(Context context, List<Tag> tags, User user) {
        this.context = context;
        this.tags = tags;
        this.user = user;
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TagName;
        ImageView TagImage;
        ConstraintLayout constraintLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TagName = itemView.findViewById(R.id.TagName);
            TagImage = itemView.findViewById(R.id.TagImage);
            constraintLayout = itemView.findViewById(R.id.constraintLayout);
        }
    }
    public HomeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tag_view,parent,false);
        return new HomeAdapter.MyViewHolder(view);
    }
    @SuppressLint("ResourceType")
    public void onBindViewHolder(@NonNull HomeAdapter.MyViewHolder holder, int position) {
        holder.TagName.setText(tags.get(position).getTagName());
        holder.TagImage.setImageResource(tags.get(position).getPhoto());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(context, Home.class);
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
                            GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken( context.getString(R.string.default_web_client_id)).requestEmail().build();
                            GoogleSignInClient googleClient = GoogleSignIn.getClient((Activity) context, options);
                            googleClient.signOut();
                            intent = new Intent(context, EasyLearnSCE.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        }
                    }).setNegativeButton(context.getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Resources res = context.getResources();
                            holder.constraintLayout.setBackground( ResourcesCompat.getDrawable(res, R.color.white, null));
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
    public int getItemCount() { return tags.size(); }
}