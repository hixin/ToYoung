package com.toyoung.ge.customwidge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by yeran on 2015/5/18.
 *
 * 圆形ImageView控件
 */
public class CustomRoundView extends ImageView {
    Paint paint;

    public CustomRoundView(Context context) {
        super(context);
    }

    public CustomRoundView(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public CustomRoundView(Context paramContext, AttributeSet paramAttributeSet, int paramInt) {
        super(paramContext, paramAttributeSet, paramInt);
    }

    protected void onDraw(Canvas canvas){
       Drawable drawable = getDrawable();
        if(drawable==null)
            return;
        if(getWidth()==0||getHeight()==0)
            return;
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();
        Bitmap bitmap1 = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        int w = getWidth(),h=getHeight();
        RoundConnerBitmap rb = new RoundConnerBitmap();
        canvas.drawBitmap(rb.toRoundBitmap(bitmap1,w), 0, 0, null);
    }


}
