package com.example.easylearnsce.Client.Class;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.easylearnsce.Client.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.Client.User.GenericEngineering;
import com.example.easylearnsce.Client.User.AllRequests;
import com.example.easylearnsce.Client.User.ChangePassword;
import com.example.easylearnsce.Client.User.EasyLearnChat;
import com.example.easylearnsce.Client.User.Home;
import com.example.easylearnsce.Client.User.Requests;
import com.example.easylearnsce.Client.User.Profile;
import com.example.easylearnsce.Client.User.SelectEngineering;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class UserNavigationView {
    private Intent intent;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    public UserNavigationView(Context context, int id, User user){
        if(id == R.id.ItemHome)
            StartActivity(context, Home.class, user);
        else if(id == R.id.ItemSelectEngineering)
            StartActivity(context, SelectEngineering.class, user);
        else if(id == R.id.ItemEngineering)
            EngineeringStartActivity(context, GenericEngineering.class, user);
        else if(id == R.id.ItemEasyLearnChat)
            StartActivity(context, EasyLearnChat.class, user);
        else if(id == R.id.ItemRequests)
            StartActivity(context, Requests.class, user);
        else if(id == R.id.ItemAllRequests)
            StartActivity(context, AllRequests.class, user);
        else if(id == R.id.ItemProfile)
            StartActivity(context, Profile.class, user);
        else if(id == R.id.ItemChangePassword)
            StartActivity(context, ChangePassword.class, user);
        else if(id == R.id.ItemSignOut) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.SignOut)).setMessage(context.getResources().getString(R.string.AreYouSure)).setCancelable(true).setPositiveButton(context.getResources().getString(R.string.Yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(firebaseAuth.getCurrentUser() != null)
                        firebaseAuth.signOut();
                    GoogleSignInOptions options = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(context.getString(R.string.default_web_client_id)).requestEmail().build();
                    GoogleSignInClient googleClient = GoogleSignIn.getClient(context, options);
                    googleClient.signOut();
                    context.startActivity(new Intent(context, EasyLearnSCE.class));
                    ((Activity)context).finish();
                }
            }).setNegativeButton(context.getResources().getString(R.string.No), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) { }
            }).show();
        }
    }
    private void StartActivity(Context context, Class Destination, User user){
        intent = new Intent(context, Destination);
        intent.putExtra("user", user);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
    private void EngineeringStartActivity(Context context, Class Destination, User user){
        intent = new Intent(context, Destination);
        intent.putExtra("user", user);
        intent.putExtra("title", user.getEngineering());
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
