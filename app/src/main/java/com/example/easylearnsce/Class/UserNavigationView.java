package com.example.easylearnsce.Class;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import com.example.easylearnsce.EngineeringFunc.GenericEngineering;
import com.example.easylearnsce.User.ChangePassword;
import com.example.easylearnsce.User.EasyLearnChat;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Profile;
import com.example.easylearnsce.User.SelectEngineering;
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
        else if(id == R.id.StructuralEngineering)
            EngineeringStartActivity(context, GenericEngineering.class, user, context.getResources().getString(R.string.StructuralEngineering));
        else if(id == R.id.MechanicalEngineering)
            EngineeringStartActivity(context, GenericEngineering.class, user, context.getResources().getString(R.string.MechanicalEngineering));
        else if(id == R.id.ElectricalEngineering)
            EngineeringStartActivity(context, GenericEngineering.class, user, context.getResources().getString(R.string.ElectricalEngineering));
        else if(id == R.id.SoftwareEngineering)
            EngineeringStartActivity(context, GenericEngineering.class, user, context.getResources().getString(R.string.SoftwareEngineering));
        else if(id == R.id.IndustrialEngineering)
            EngineeringStartActivity(context, GenericEngineering.class, user, context.getResources().getString(R.string.IndustrialEngineering));
        else if(id == R.id.ChemicalEngineering)
            EngineeringStartActivity(context, GenericEngineering.class, user, context.getResources().getString(R.string.ChemicalEngineering));
        else if(id == R.id.ProgrammingComputer)
            EngineeringStartActivity(context, GenericEngineering.class, user, context.getResources().getString(R.string.ProgrammingComputer));
        else if(id == R.id.PreEngineering)
            EngineeringStartActivity(context, SelectEngineering.class, user, context.getResources().getString(R.string.PreEngineering));
        else if(id == R.id.ItemEasyLearnChat)
            StartActivity(context, EasyLearnChat.class, user);
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
    private void EngineeringStartActivity(Context context, Class Destination, User user, String Engineering){
        intent = new Intent(context, Destination);
        intent.putExtra("user", user);
        intent.putExtra("Tag", Engineering);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
