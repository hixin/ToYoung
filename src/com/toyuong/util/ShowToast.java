package com.toyuong.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by yeran on 2015/5/19.
 */
public class ShowToast {
    public void longshow(Context mcontext,String msg){
        Toast.makeText(mcontext,msg,Toast.LENGTH_LONG).show();
    }
    public void shortshow(Context mcontext,String msg){
        Toast.makeText(mcontext,msg,Toast.LENGTH_SHORT).show();
    }

    public void topshowToast(Context mcontext,String msg){
        Toast tst = Toast.makeText(mcontext, msg, Toast.LENGTH_SHORT);
        tst.setGravity(Gravity.CENTER | Gravity.TOP, 0, 240);
        tst.show();
    }
}
