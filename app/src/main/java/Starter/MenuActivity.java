package Starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myfirstandroidapplication.R;
import com.google.android.material.snackbar.Snackbar;

import java.io.OutputStream;

import FallingSky.HomeFallingSkyActivity;
import MathsQuiz.MathsQuizMainActivity;

public class MenuActivity extends AppCompatActivity {
    TextView txtName;
    Button btnSaveName;
    OutputStream outputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        txtName = findViewById(R.id.txtName);
        btnSaveName = findViewById(R.id.btnSaveName);

    }

    public void saveNamebtn(View view)
    {
        String name = txtName.getText().toString();
        if(!txtName.getText().equals("") || !(txtName.getText() == null)){
            try {
                outputStream = openFileOutput("PlayerName", Context.MODE_PRIVATE);

                outputStream.write(name.getBytes());
                outputStream.close();

            } catch (Exception ex) {
                // SnackBar to tell user that there is no text file
                Snackbar snackbar = Snackbar.make(findViewById(R.id.tv_score), R.string.WriteToFileError, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
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