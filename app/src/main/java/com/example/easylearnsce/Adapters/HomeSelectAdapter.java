package com.example.easylearnsce.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.ChangePassword;
import com.example.easylearnsce.User.EasyLearnChat;
import com.example.easylearnsce.User.Home;
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
        TextView textView;
        ImageView imageView;
        ConstraintLayout cardView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.engineering_name);
            imageView = itemView.findViewById(R.id.engineering_pic);
            cardView = itemView.findViewById(R.id.engineeringview_id);
        }
    }
    public void setUser(User user){ this.user = user; }
    public HomeSelectAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.select_view,parent,false);
        return new HomeSelectAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull HomeSelectAdapter.MyViewHolder holder, int position) {
        holder.textView.setText(Select.get(position).getTagName());
        holder.imageView.setImageResource(Select.get(position).getPhoto());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                intent = new Intent(context, Home.class);
                //Home
                 if(holder.textView.getText().equals(context.getResources().getString(R.string.SelectEngineering)))
                    intent = new Intent(context, SelectEngineering.class);
                else if(holder.textView.getText().equals(context.getResources().getString(R.string.EasyLearnChat)))
                    intent = new Intent(context, EasyLearnChat.class);
                else if(holder.textView.getText().equals(context.getResources().getString(R.string.Profile)))
                    intent = new Intent(context, Profile.class);
                else if(holder.textView.getText().equals(context.getResources().getString(R.string.ChangePassword)))
                    intent = new Intent(context, ChangePassword.class);
                if(holder.textView.getText().equals(context.getResources().getString(R.string.SignOut))) {
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
                            holder.cardView.setBackgroundColor(context.getResources().getColor(R.color.sky));
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