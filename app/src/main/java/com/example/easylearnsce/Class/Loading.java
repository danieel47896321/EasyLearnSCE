package com.example.easylearnsce.Class;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import com.example.easylearnsce.R;

public class Loading {
    private ProgressDialog LoadingBar;
    public Loading(Context context){
        LoadingBar = new ProgressDialog(context,R.style.AppCompatAlertDialogStyle);
        SpannableString loading =  new SpannableString(context.getResources().getString(R.string.Loading));
        loading.setSpan(new RelativeSizeSpan(2f), 0, loading.length(), 0);
       // loading.setSpan(new ForegroundColorSpan(Color.BLACK), 0, loading.length(), 0);
        LoadingBar.setCancelable(false);
        LoadingBar.setMessage(loading);
        LoadingBar.show();
    }
    public void stop(){ LoadingBar.cancel(); }
}
