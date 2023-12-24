package Starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.myfirstandroidapplication.R;

import FallingSky.HomeFallingSkyActivity;
import MathsQuiz.MathsQuizMainActivity;

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }


    public void navFallingGameHome(View view)
    {
        // Create an action
        Intent FallingSkyHomeScreen = new Intent(getApplicationContext(), HomeFallingSkyActivity.class);
        // Tell it to do it
        startActivity(FallingSkyHomeScreen);
    }

    public void navMathsQuizMain(View view)
    {
        // Create an action
        Intent MathsQuizMainScreen = new Intent(getApplicationContext(), MathsQuizMainActivity.class);
        // Tell it to do it
        startActivity(MathsQuizMainScreen);
    }

    public void navLeaderboard(View view)
    {
        // Create an action
        Intent LeaderboardScreen = new Intent(getApplicationContext(), LeaderboardActivity.class);
        // Tell it to do it
        startActivity(LeaderboardScreen);
    }

}