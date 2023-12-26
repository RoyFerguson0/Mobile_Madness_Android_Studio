package Starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myfirstandroidapplication.R;

public class SplashActivity extends AppCompatActivity {
    Animation topAnim, bottomAnim;
    ImageView image;
    TextView logo, developer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // initiate progress bar
        final ProgressBar progBar = findViewById(R.id.progLoadSplash);

        // Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        image = findViewById(R.id.imgMobileMadnessLogo);
        logo = findViewById(R.id.lblCompanyLogo);
        developer = findViewById(R.id.lblDeveloperName);

        image.setAnimation(topAnim);
        logo.setAnimation(bottomAnim);
        developer.setAnimation(bottomAnim);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(SplashActivity.this, MenuActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);


    }
}