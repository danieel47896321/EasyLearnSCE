package com.example.easylearnsce.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easylearnsce.Class.Tag;
import com.example.easylearnsce.Class.User;
import com.example.easylearnsce.Guest.About;
import com.example.easylearnsce.Guest.Contact;
import com.example.easylearnsce.Guest.CreateAccount;
import com.example.easylearnsce.Guest.ResetPassword;
import com.example.easylearnsce.Guest.SignIn;
import com.example.easylearnsce.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class EasyLearnAdapter extends RecyclerView.Adapter<EasyLearnAdapter.MyViewHolder> {
    private Context context;
    private List<Tag> Select;
    private User user;
    private Intent intent;
    public EasyLearnAdapter(Context context, List<Tag> select) {
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
    public EasyLearnAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tag_view,parent,false);
        return new EasyLearnAdapter.MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull EasyLearnAdapter.MyViewHolder holder, int position) {
        holder.TagName.setText(Select.get(position).getTagName());
        holder.TagPhoto.setImageResource(Select.get(position).getPhoto());
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.constraintLayout.setBackgroundColor(context.getResources().getColor(R.color.PickColor));
                if(holder.TagName.getText().equals(context.getResources().getString(R.string.ResetPassword)))
                    intent = new Intent(context, ResetPassword.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.SignIn)))
                    intent = new Intent(context, SignIn.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.CreateAccount)))
                    intent = new Intent(context, CreateAccount.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.Contact)))
                    intent = new Intent(context, Contact.class);
                else if(holder.TagName.getText().equals(context.getResources().getString(R.string.About)))
                    intent = new Intent(context, About.class);
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        });
    }
    public int getItemCount() { return Select.size(); }
}