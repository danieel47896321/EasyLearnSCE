package com.example.easylearnsce.Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.easylearnsce.Guest.About;
import com.example.easylearnsce.Guest.Contact;
import com.example.easylearnsce.Guest.CreateAccount;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.Guest.ResetPassword;
import com.example.easylearnsce.Guest.SignIn;
import com.example.easylearnsce.R;
import com.example.easylearnsce.User.ChangePassword;
import com.example.easylearnsce.User.EasyLearnChat;
import com.example.easylearnsce.User.Home;
import com.example.easylearnsce.User.Profile;
import com.example.easylearnsce.User.SelectEngineering;

public class GuestNavView {
    private Intent intent;
    public GuestNavView(Context context, int id){
        if (id == R.id.ItemEasyLearnSCE)
            intent = new Intent(context, EasyLearnSCE.class);
        else if(id == R.id.ItemSignIn)
            intent = new Intent(context, SignIn.class);
        else if(id == R.id.ItemCreateAccount)
            intent = new Intent(context, CreateAccount.class);
        else if(id == R.id.ItemResetPassword)
            intent = new Intent(context, ResetPassword.class);
        else if(id == R.id.ItemAbout)
            intent = new Intent(context, About.class);
        else if(id == R.id.ItemContact)
            intent = new Intent(context, Contact.class);
        context.startActivity(intent);
        ((Activity)context).finish();
    }
}
