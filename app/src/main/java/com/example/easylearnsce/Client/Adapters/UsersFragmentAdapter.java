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
        holder.UserName.setText(user.getFirstName() + " " + user.getLastName());
        holder.UserInfo.setText(getGender(user.getGender()) + ", " + age + ", "+ getCity(user.getCity()));
        if(!user.getImage().equals("Image"))
            Glide.with(context).load(user.getImage()).into(holder.UserImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.setBackgroundColor(context.getResources().getColor(R.color.grey));
                Intent intent = new Intent(context, Message.class);
                intent.putExtra("user",user);
                context.startActivity(intent);
            }
        });
    }
    private String getGender(String gender){
        if(gender.equals("Male") || gender.equals("זכר"))
            return context.getResources().getString(R.string.Male);
        else if(gender.equals("Female") || gender.equals("נקבה"))
            return context.getResources().getString(R.string.Female);
        return context.getResources().getString(R.string.Other);
    }
    private String getCity(String city){
        if(city.equals("Ashqelon") || city.equals("אשקלון"))
            return context.getResources().getString(R.string.Ashqelon);
        else if(city.equals("Beer Sheva") || city.equals("באר שבע"))
            return context.getResources().getString(R.string.BeerSheva);
        else if(city.equals("Bet Shean") || city.equals("בית שאן"))
            return context.getResources().getString(R.string.BetShean);
        else if(city.equals("Bet Shemesh") || city.equals("בית שמש"))
            return context.getResources().getString(R.string.BetShemesh);
        else if(city.equals("Bene Beraq") || city.equals("בני ברק"))
            return context.getResources().getString(R.string.BeneBeraq);
        else if(city.equals("Elat") || city.equals("אילת"))
            return context.getResources().getString(R.string.Elat);
        else if(city.equals("Givatayim") || city.equals("גבעתיים"))
            return context.getResources().getString(R.string.Givatayim);
        else if(city.equals("Ofaqim") || city.equals("אופקים"))
            return context.getResources().getString(R.string.Ofaqim);
        else if(city.equals("Petah Tiqwa") || city.equals("פתח תקווה"))
            return context.getResources().getString(R.string.PetahTiqwa);
        else if(city.equals("Tel Aviv") || city.equals("תל אביב"))
            return context.getResources().getString(R.string.TelAviv);
        else if(city.equals("Haifa") || city.equals("חיפה"))
            return context.getResources().getString(R.string.Haifa);
        else if(city.equals("Ashdod") || city.equals("אשדוד"))
            return context.getResources().getString(R.string.Ashdod);
        return context.getResources().getString(R.string.BeerSheva);
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
