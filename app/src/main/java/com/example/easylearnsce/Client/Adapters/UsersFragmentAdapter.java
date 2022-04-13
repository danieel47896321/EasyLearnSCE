package com.example.easylearnsce.Client.Adapters;
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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easylearnsce.Client.Class.User;
import com.example.easylearnsce.R;
import com.example.easylearnsce.Client.User.Message;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class UsersFragmentAdapter extends RecyclerView.Adapter<UsersFragmentAdapter.ViewHolder> {
    private Context context;
    private ArrayList<User> users;
    public UsersFragmentAdapter(Context context, ArrayList<User> users ){
        this.context = context;
        this.users = users;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_select_view, parent, false);
        return new UsersFragmentAdapter.ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        int age = getYears(new Date(Integer.valueOf(user.getYear()),Integer.valueOf(user.getMonth()),Integer.valueOf(user.getDay())));
        holder.UserName.setText(user.getFirstName()+" "+user.getLastName());
        holder.UserInfo.setText(user.getGender()+", "+age+", "+user.getCity());
        if(!user.getImage().equals("Image"))
            Glide.with(context).load(user.getImage()).into(holder.UserImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Message.class);
                intent.putExtra("user",user);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() { return users.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView UserName;
        public TextView UserInfo;
        public ImageView UserImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserName = itemView.findViewById(R.id.UserName);
            UserInfo = itemView.findViewById(R.id.UserInfo);
            UserImage = itemView.findViewById(R.id.UserImage);
        }
    }
    private int getYears(Date date){
        int years = Calendar.getInstance().get(Calendar.YEAR) - date.getYear();
        if(date.getMonth() >  Calendar.getInstance().get(Calendar.MONTH) ||
                (date.getMonth() ==  Calendar.getInstance().get(Calendar.MONTH) &&
                        date.getDate() > Calendar.getInstance().get(Calendar.DAY_OF_WEEK)))
            years -=1;
        return years;
    }
}
