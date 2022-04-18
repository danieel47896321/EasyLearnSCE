package com.example.easylearnsce.Client.Class;

import android.app.Activity;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.easylearnsce.R;
import com.google.android.material.navigation.NavigationView;


public class UserMenuInfo {
    public UserMenuInfo(User user, Context context){
        NavigationView UserNavigationView = ((Activity) context).findViewById(R.id.navigationView);
        View UserImage = UserNavigationView.getHeaderView(0).findViewById(R.id.UserImage);
        TextView UserFullName = UserNavigationView.getHeaderView(0).findViewById(R.id.UserFullName);
        TextView UserEmail = UserNavigationView.getHeaderView(0).findViewById(R.id.UserEmail);
        TextView UserType = UserNavigationView.getHeaderView(0).findViewById(R.id.UserType);
        UserType.setText(getUserType(user.getType(),context));
        UserFullName.setText(user.getFirstName()+" "+user.getLastName());
        UserEmail.setText(user.getEmail());
        if(!user.getImage().equals("Image")){
            Glide.with(context).asBitmap().load(user.getImage()).into(new CustomTarget<Bitmap>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) { UserImage.setBackground(new BitmapDrawable(context.getResources(), resource)); }
                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) { }
            });
        }
    }
    private String getUserType(String type, Context context){
        if(type.equals("Admin") || type.equals("אדמין"))
            return context.getResources().getString(R.string.Admin);
        else if(type.equals("Teacher") || type.equals("מרצה"))
            return context.getResources().getString(R.string.Teacher);
        else
            return context.getResources().getString(R.string.Student);
    }
}