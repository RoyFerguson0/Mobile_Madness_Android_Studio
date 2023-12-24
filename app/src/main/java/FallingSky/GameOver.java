package FallingSky;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstandroidapplication.R;

import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;

import Starter.LeaderboardActivity;
import Starter.MenuActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;

    FileOutputStream outputStream;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        tvPoints = findViewById(R.id.tvPoints);
        tvHighest = findViewById(R.id.tvHighest);
        ivNewHighest = findViewById(R.id.ivNewHighest);
        int points = getIntent().getExtras().getInt("points");
        tvPoints.setText("" + points);
        sharedPreferences = getSharedPreferences("my_pref", 0);
        int highest = sharedPreferences.getInt("highest", 0);
        if(points > highest){
            ivNewHighest.setVisibility(View.VISIBLE);
            highest = points;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("highest", highest);
            editor.commit();
        }
        tvHighest.setText("" + highest);

       // setFallingSkyHighScore(tvHighest);
        LeaderboardActivity test = new LeaderboardActivity();
        test.setFallingSkyHighScore(String.valueOf(highest));



    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, HomeFallingSkyActivity.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        Intent intent = new Intent(GameOver.this, MenuActivity.class);
        startActivity(intent);
        finish();
        finish();
    }
}
