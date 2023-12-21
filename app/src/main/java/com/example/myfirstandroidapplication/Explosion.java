package com.example.myfirstandroidapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Explosion {
    Bitmap explosion[] = new Bitmap[4];

    int explosionFrame = 0;
    int explosionX, explosionY;

    public Explosion(Context context){
//        explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode1);
//        explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode2);
//        explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode3);
//        explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.explode4);

        Bitmap bmp;
        int width=150;
        int height=150;
        bmp =BitmapFactory.decodeResource(context.getResources(), R.drawable.explode1);//image is your image
        explosion[0] =Bitmap.createScaledBitmap(bmp, width,height, true);

        bmp =BitmapFactory.decodeResource(context.getResources(), R.drawable.explode2);//image is your image
        explosion[1] =Bitmap.createScaledBitmap(bmp, width,height, true);

        bmp =BitmapFactory.decodeResource(context.getResources(), R.drawable.explode3);//image is your image
        explosion[2] =Bitmap.createScaledBitmap(bmp, width,height, true);

        bmp =BitmapFactory.decodeResource(context.getResources(), R.drawable.explode4);//image is your image
        explosion[3] =Bitmap.createScaledBitmap(bmp, width,height, true);
    }

    public Bitmap getExplosion(int explosionFrame){
        return explosion[explosionFrame];
    }
}
