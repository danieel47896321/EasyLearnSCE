package com.example.easylearnsce.Client.Adapters;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.easylearnsce.Client.Class.YouTubeMessage;
import com.example.easylearnsce.Client.User.Profile;
import com.example.easylearnsce.R;
import java.util.ArrayList;

public class YouTubeTopicChatAdapter extends RecyclerView.Adapter<YouTubeTopicChatAdapter.ViewHolder> {
    private Context context;
    private ArrayList<YouTubeMessage> youTubeMessages;
    public YouTubeTopicChatAdapter(Context context, ArrayList<YouTubeMessage> youTubeMessages){
        this.context = context;
        this.youTubeMessages = youTubeMessages;
    }
    @NonNull
    @Override
    public YouTubeTopicChatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.youtube_topic_message_view, parent, false);
        return new YouTubeTopicChatAdapter.ViewHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull YouTubeTopicChatAdapter.ViewHolder holder, int position) {
        YouTubeMessage youTubeMessage = youTubeMessages.get(position);
        holder.FullName.setText(youTubeMessage.getFullName());
        holder.Message.setText(youTubeMessage.getMessage());
        if(!youTubeMessage.getImage().equals("Image"))
            Glide.with(context).load(youTubeMessage.getImage()).into(holder.ProfileImage);
        holder.ProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    @Override
    public int getItemCount() { return youTubeMessages.size(); }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView FullName;
        public TextView Message;
        public ImageView ProfileImage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            FullName = itemView.findViewById(R.id.FullName);
            Message = itemView.findViewById(R.id.Message);
            ProfileImage = itemView.findViewById(R.id.ProfileImage);
        }
    }
}