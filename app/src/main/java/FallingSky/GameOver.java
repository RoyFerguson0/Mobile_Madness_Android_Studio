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
import com.google.android.material.snackbar.Snackbar;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import Starter.LeaderboardActivity;
import Starter.MenuActivity;

public class GameOver extends AppCompatActivity {

    TextView tvPoints;
    TextView tvHighest;
    SharedPreferences sharedPreferences;
    ImageView ivNewHighest;

    FileOutputStream outputStream;
    InputStream inStream;

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

        String Highest = tvHighest.getText().toString();
        writeScoreToFile(Highest);

    }

    public void writeScoreToFile(String Highest){
        String[] stored = new String[1];
        int[] stored2 = new int[1];
        int score1 = 0;

        int total2 = Integer.parseInt(Highest);

        try{
            inStream = openFileInput("FallingSkyHighScore");

            if(inStream != null) {
                InputStreamReader inputReader = new InputStreamReader(inStream);
                BufferedReader buffReader = new BufferedReader(inputReader);


                String line = "";

                int position = 0;
                while ((line = buffReader.readLine()) != null) {
                    stored[position] = line;
                    stored2[position] = Integer.parseInt(line);
                    position++;
                }

            }
            inStream.close();
        }catch(Exception ex){
        }

        score1 = stored2[0];


        if(stored[0] == null){
            // Writting out to the Text file if nothing has been added to it
            try {
                outputStream = openFileOutput("FallingSkyHighScore", Context.MODE_APPEND);

                outputStream.write(Highest.getBytes());
                outputStream.close();

            }catch (Exception ex){
            }
        }else {


            int higheststr = Integer.parseInt(Highest);


            if(higheststr > score1) {

                // Writting out to the Text file
                try {
                    outputStream = openFileOutput("FallingSkyHighScore", Context.MODE_PRIVATE);

                    outputStream.write(Highest.getBytes());
                    outputStream.close();

                } catch (Exception ex) {
                    // SnackBar to tell user that there is no text file
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.tv_score), R.string.WriteToFileError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        }
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
