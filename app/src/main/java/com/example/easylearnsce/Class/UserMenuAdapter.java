package com.example.easylearnsce.Class;

import android.app.Activity;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class UserMenuAdapter {
    private Context context;
    public UserMenuAdapter(User user, Context context){
        this.context = context;
        NavigationView UserNavigationView = ((Activity) context).findViewById(R.id.UserNavigationView);
        View UserImage = UserNavigationView.getHeaderView(0).findViewById(R.id.UserImage);
        TextView UserFullName = UserNavigationView.getHeaderView(0).findViewById(R.id.user_fullname);
        TextView UserEmail = UserNavigationView.getHeaderView(0).findViewById(R.id.user_email);
        TextView UserType = UserNavigationView.getHeaderView(0).findViewById(R.id.user_type);
        UserType.setText(user.getType());
        UserFullName.setText(user.getFirstname()+" "+user.getLastname());
        UserEmail.setText(user.getEmail());
        if(user.getImage().equals("Image")){

        }
        else{
            Glide.with(context).asBitmap().load(user.getImage()).into(new CustomTarget<Bitmap>() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) { UserImage.setBackground(new BitmapDrawable(context.getResources(), resource)); }
                @Override
                public void onLoadCleared(@Nullable Drawable placeholder) { }
            });
        }
    }
}