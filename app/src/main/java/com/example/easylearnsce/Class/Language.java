package com.example.easylearnsce.Class;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import androidx.annotation.RequiresApi;

import com.example.easylearnsce.Guest.Contact;
import com.example.easylearnsce.Guest.EasyLearnSCE;
import com.example.easylearnsce.R;
import java.util.Locale;
public class Language {
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public Language(Context context, String language){
        Locale locale;
        if(language.equals(context.getResources().getString(R.string.English)))
            locale = new Locale("en");
        else
            locale = new Locale("he");
        Locale.setDefault(locale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        context.startActivity(new Intent(context, context.getClass()));
        ((Activity)context).finish();
    }
}
