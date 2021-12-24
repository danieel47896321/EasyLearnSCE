package com.example.easylearnsce.Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.ChangePassword;
import com.example.easylearnsce.User.EasyLearnChat;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Profile;
import com.example.easylearnsce.User.SelectEngineering;
import com.google.firebase.auth.FirebaseAuth;

public class UserNavView {
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public UserNavView(Context context, int id, User user){
        if(id == R.id.ItemHome)
            StartActivity(context, Home.class, user);
        else if(id == R.id.ItemSelectEngineering)
            StartActivity(context, SelectEngineering.class, user);
        else if(id == R.id.ItemEasyLearnChat)
            StartActivity(context, EasyLearnChat.class, user);
        else if(id == R.id.ItemProfile)
            StartActivity(context, Profile.class, user);
        else if(id == R.id.ItemChangePassword)
            StartActivity(context, ChangePassword.class, user);
        else if(id == R.id.ItemSignOut) {
            if(firebaseAuth.getCurrentUser() != null)
                firebaseAuth.signOut();
            context.startActivity(new Intent(context, EasyLearnSCE.class));
            ((Activity)context).finish();
        }
    }
    private void StartActivity(Context context, Class Destination, User user){
        intent = new Intent(context, Destination);
        intent.putExtra("user", user);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
