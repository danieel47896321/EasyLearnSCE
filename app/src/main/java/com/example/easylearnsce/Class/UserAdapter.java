package com.example.easylearnsce.Class;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.Message;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private Context context;
    private List<User> users;
    public UserAdapter(Context context, List<User> users ){
        this.context = context;
        this.users = users;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_select_view, parent, false);
        return new UserAdapter.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = users.get(position);
        holder.FragmentUserName.setText(user.getFirstName()+" "+user.getLastName());
        //holder.FragmentUserInfo.setText(user.getGender()+", "+user.getAge()+", "+user.getCity());
        if(!user.getImage().equals("Image"))
            Glide.with(context).load(user.getImage()).into(holder.FragmentUserImage);
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
        public TextView FragmentUserName;
        public TextView FragmentUserInfo;
        public ImageView FragmentUserImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FragmentUserName = itemView.findViewById(R.id.FragmentUserName);
            FragmentUserInfo = itemView.findViewById(R.id.FragmentUserInfo);
            FragmentUserImage = itemView.findViewById(R.id.FragmentUserImage);
        }
    }
}
