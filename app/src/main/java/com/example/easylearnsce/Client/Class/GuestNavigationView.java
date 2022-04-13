package com.example.easylearnsce.Client.Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.easylearnsce.Client.Guest.About;
import com.example.easylearnsce.Client.Guest.Contact;
import com.example.easylearnsce.Client.Guest.CreateAccount;
import com.example.easylearnsce.Client.Guest.EasyLearnSCE;
import com.example.easylearnsce.Client.Guest.ResetPassword;
import com.example.easylearnsce.Client.Guest.SignIn;
import com.example.easylearnsce.R;

public class GuestNavigationView {
    private Intent intent;
    public GuestNavigationView(Context context, int id){
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
