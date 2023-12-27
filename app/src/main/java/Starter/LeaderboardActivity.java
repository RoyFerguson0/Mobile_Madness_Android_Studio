package Starter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.myfirstandroidapplication.R;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class LeaderboardActivity extends AppCompatActivity {
    TextView lblFallingSkyHighScore, lblMathsHighScoreOne, lblMathsHighScoreTwo, lblMathsHighScoreThree, lblTotalQuestionsRight, lblPlayerName;
    String[] stored = new String[]{"0","0","0","0","0","0","0"};
    String score;
    InputStream inStream;
    OutputStream outputStream;

    public void getScores(String file) {
        stored = new String[]{"0", "0", "0", "0", "0", "0", "0"};
        try{
            inStream = openFileInput(file);

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


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);

        lblFallingSkyHighScore = findViewById(R.id.lblFallingSkyHighScore);
        lblMathsHighScoreOne = findViewById(R.id.lblMathsHighScoreOne);
        lblMathsHighScoreTwo = findViewById(R.id.lblMathsHighScoreTwo);
        lblMathsHighScoreThree = findViewById(R.id.lblMathsTotalPoints);
        lblTotalQuestionsRight = findViewById(R.id.lblTotalQuestionsRight);
        lblPlayerName = findViewById(R.id.lblPlayerName);

        getScores("FallingSkyHighScore");
        lblFallingSkyHighScore.setText(stored[0]);
        getScores("MathsGameHighScore");
        lblMathsHighScoreOne.setText(stored[0]);
        lblMathsHighScoreTwo.setText(stored[1]);
        lblMathsHighScoreThree.setText(stored[2]);
        getScores("MathsGameTotalScore");
        lblTotalQuestionsRight.setText(stored[0] + "/" + stored[1]);

        getScores("PlayerName");
        if(stored[0].equals("0") || stored[0] == null) {
            lblPlayerName.setText("User: Anonymous");
        }else{
            lblPlayerName.setText("User: " + stored[0]);
        }
    }

    public void navLeaderboardExitToMenu(View view)
    {
        // Create an action
        Intent MenuScreen = new Intent(getApplicationContext(), MenuActivity.class);
        // Tell it to do it
        startActivity(MenuScreen);
    }


}