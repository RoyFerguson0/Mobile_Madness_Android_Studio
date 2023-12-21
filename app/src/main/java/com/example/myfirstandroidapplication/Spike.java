package com.example.myfirstandroidapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.Random;

public class Spike {
    // For holding spike images
    Bitmap spike[] = new Bitmap[3];

    // Declaring Varialbes
    int spikeFrame = 0;
    int spikeX, spikeY, spikeVelocity;
    Random random;

    public  Spike(Context context){
        // Initialise Spike images in the Array (Bitmap)
//        spike[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);
//        spike[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.dynamite);
//        spike[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.nuclearbomb);


        Bitmap bmp;
        int width=150;
        int height=150;
        bmp =BitmapFactory.decodeResource(context.getResources(), R.drawable.bomb);//image is your image
        spike[0] =Bitmap.createScaledBitmap(bmp, width,height, true);

        bmp =BitmapFactory.decodeResource(context.getResources(), R.drawable.dynamite);//image is your image
        spike[1] =Bitmap.createScaledBitmap(bmp, width,height, true);

        bmp =BitmapFactory.decodeResource(context.getResources(), R.drawable.nuclearbomb);//image is your image
        spike[2] =Bitmap.createScaledBitmap(bmp, width,height, true);


        // Intialising Random
        random = new Random();

        // Used to reset spikeX, spikeY and spikeVelocity
        resetPosition();
    }


    public Bitmap getSpike(int spikeFrame){
        return spike[spikeFrame];
    }

    public int getSpikeWidth(){
        return spike[0].getWidth();
    }

    public int getSpikeHeight(){
        return spike[0].getHeight();
    }
    public void resetPosition(){
        // Intialise spike X,Y,V with Randoms Spike
        spikeX = random.nextInt(GameView.dWidth - getSpikeWidth());
        spikeY = -200 + random.nextInt(600) * -1;
        spikeVelocity = 35 + random.nextInt(16);
    }
}
