package Starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.myfirstandroidapplication.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import FallingSky.GameOver;

public class LeaderboardActivity extends AppCompatActivity {
    TextView lblFallingSkyHighScore, lblMathsQuizScore, lblMathsTotalPoints, lblMathsQuizTotalQuestions;
    String[] stored = {"0","0","0","0","0","0","0"};
    String score;
    InputStream inStream;
    OutputStream outputStream;

    public void getScores() {
        try{
            inStream = openFileInput("GameHighScores");

            if(inStream != null){
                // To read the file Character at a time (InputStreadReader)
                InputStreamReader inputReader = new InputStreamReader(inStream);
                // BufferedReader can read a line at a time
                BufferedReader buffReader = new BufferedReader(inputReader);

                String name = "", mark = "", line = "", fullData = "";

                int position = 0;

                while ((line = buffReader.readLine()) != null) {
                    // do something with the line you just read, e.g.
                    stored[position] = line;
                    position++;

                }

                inStream.close();

            }


        }catch (Exception ex){

        }
    }

    public String getFallingSkyHighScore() {
        return FallingSkyHighScore;
    }

    public void setFallingSkyHighScore(String fallingSkyHighScore) {
        FallingSkyHighScore = fallingSkyHighScore;
        //setScores();

    }

    public String getMathsQuizHighScore() {
        return MathsQuizHighScore;
    }

    public void setMathsQuizHighScore(String mathsQuizHighScore) {
        MathsQuizHighScore = mathsQuizHighScore;
        //setScores();
    }

    public String getMathsQuizPoints() {
        return MathsQuizPoints;
    }

    public void setMathsQuizPoints(String mathsQuizPoints) {
        MathsQuizPoints = mathsQuizPoints;
    }

    private String FallingSkyHighScore;

    private String MathsQuizHighScore;

    private String MathsQuizPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        lblFallingSkyHighScore = findViewById(R.id.lblFallingSkyHighScore);
        lblMathsQuizScore = findViewById(R.id.lblMathsQuizScore);
        lblMathsQuizTotalQuestions = findViewById(R.id.lblMathsQuizTotalQuestions);
        lblMathsTotalPoints = findViewById(R.id.lblMathsTotalPoints);

        getScores();
        lblFallingSkyHighScore.setText(stored[0]);
        lblMathsQuizScore.setText(stored[1]);
        lblMathsTotalPoints.setText(stored[2]);
        lblMathsQuizTotalQuestions.setText(stored[3]);
    }

    public void navLeaderboardExitToMenu(View view)
    {
        // Create an action
        Intent MenuScreen = new Intent(getApplicationContext(), MenuActivity.class);
        // Tell it to do it
        startActivity(MenuScreen);
    }


}