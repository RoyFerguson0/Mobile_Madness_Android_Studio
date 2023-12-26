package MathsQuiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfirstandroidapplication.R;
import com.google.android.material.snackbar.Snackbar;
//import com.example.myfirstandroidapplication.databinding.ActivityMathsQuizMainBinding;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import FallingSky.GameOver;
import Starter.AppData;
import Starter.LeaderboardActivity;
import Starter.MenuActivity;

public class MathsQuizMainActivity extends AppCompatActivity {

    Button btn_start, btnExitfromMathsMain, btn_answer0, btn_answer1, btn_answer2, btn_answer3;
    TextView tv_score, tv_questions, tv_timer, tv_bottommessage;
    ProgressBar prog_timer;

    FileOutputStream outputStream;
    InputStream inStream;

    Game g = new Game();

    int secondsRemaining = 30;

    public static final int PRIMARY_FOREGROUND_NOTIF_SERVICE_ID = 1001;

    MediaPlayer music;

    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining--;
            tv_timer.setText(Integer.toString(secondsRemaining) + "sec");
            prog_timer.setProgress(30 - secondsRemaining);
        }

        @Override
        public void onFinish() {
            btn_answer0.setEnabled(false);
            btn_answer1.setEnabled(false);
            btn_answer2.setEnabled(false);
            btn_answer3.setEnabled(false);
            tv_bottommessage.setText("Time is up! " + g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));
            music.stop();

            String one = String.valueOf(g.getNumberCorrect());
            String two = String.valueOf(g.getTotalQuestions() - 1);


            LeaderboardActivity test = new LeaderboardActivity();
            String value = String.valueOf(g.getNumberCorrect());
            test.setMathsQuizHighScore(value);

            AppData.LeaderBoard.setMathsQuizHighScore(value);
            System.out.println(AppData.LeaderBoard.getMathsQuizHighScore());
            String MathsQuizHighScore = AppData.LeaderBoard.getMathsQuizHighScore();
            System.out.println("Testing Array output");

            String[] stored = new String[3];
            int[] stored2 = new int[3];
            int score1 = 0, score2 = 0, score3 = 0;
            try{
                inStream = openFileInput("MathsGameHighScore");

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

                    System.out.println(score1 + " test2 " + score2 + " test3 " + score3);
            }catch(Exception ex){
            }

            score1 = stored2[0];
            score2 = stored2[1];
            score3 = stored2[2];
            String highScore = tv_score.getText().toString();
            int intHighScore = Integer.parseInt(highScore);


            if(stored[0] == null || stored[1] == null || stored[2] == null){
                // Writting out to the Text file
                try {
                    outputStream = openFileOutput("MathsGameHighScore", Context.MODE_APPEND);

                    outputStream.write(highScore.getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.close();

                }catch (Exception ex){
                    // SnackBar to tell user that there is no text file
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.tv_score), R.string.WriteToFileError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }else {
                Arrays.sort(stored2);

                if(intHighScore > score1){
                    score1 = intHighScore;
                }else if (intHighScore > score2){
                    score2 = intHighScore;
                }else if (intHighScore > score3){
                    score3 = intHighScore;
                }

                String score4 = String.valueOf(score1);
                String score5 = String.valueOf(score2);
                String score6 = String.valueOf(score3);

                // Writting out to the Text file
                try {
                    outputStream = openFileOutput("MathsGameHighScore", Context.MODE_PRIVATE);

                    outputStream.write(score4.getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.write(score5.getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.write(score6.getBytes());
                    outputStream.close();

                } catch (Exception ex) {
                    // SnackBar to tell user that there is no text file
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.tv_score), R.string.WriteToFileError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }


            String correct = String.valueOf(g.getNumberCorrect());
            String total = String.valueOf(g.getTotalQuestions() -1);

            writeScoreToFile(correct, total);




            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                String id = "_channel_01";
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel mChannel = new NotificationChannel(id, "notification", importance);
                mChannel.enableLights(true);

                Notification notification = new Notification.Builder(getApplicationContext(), id)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Game Over")
                        .setContentText("Time is up! You Scored: " + one + "/" + two)
                        .build();

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(mChannel);
                    mNotificationManager.notify(PRIMARY_FOREGROUND_NOTIF_SERVICE_ID, notification);
                }
            }

            final Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_start.setVisibility(View.VISIBLE);
                    btnExitfromMathsMain.setVisibility(View.VISIBLE);
                }
            }, 4000);


        }
    };

public void writeScoreToFile(String correct, String total){
    String[] stored = new String[2];
    int[] stored2 = new int[2];
    int score1 = 0, score2 = 0;

    int total2 = Integer.parseInt(total);
    int correct2 = Integer.parseInt(correct);

    try{
        inStream = openFileInput("MathsGameTotalScore");

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
    score2 = stored2[1];


    if(stored[0] == null || stored[1] == null){
        // Writting out to the Text file if nothing has been added to it
        try {
            outputStream = openFileOutput("MathsGameTotalScore", Context.MODE_APPEND);

            outputStream.write(correct.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.write(total.getBytes());
            outputStream.write("\n".getBytes());
            outputStream.close();

        }catch (Exception ex){
            // SnackBar to tell user that there is no text file
            Snackbar snackbar = Snackbar.make(findViewById(R.id.tv_score), R.string.WriteToFileError, Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }else {


        double totalValue = (((double) score1 / score2) * 100);
        double currectTotalValue = (((double) correct2 / total2) * 100);
        System.out.println("Correct Vs Total");
        System.out.println(totalValue);
        System.out.println(currectTotalValue);
        System.out.println(score1);
        System.out.println(score2);

        if(currectTotalValue > totalValue) {

            // Writting out to the Text file
            try {
                outputStream = openFileOutput("MathsGameTotalScore", Context.MODE_PRIVATE);

                outputStream.write(correct.getBytes());
                outputStream.write("\n".getBytes());
                outputStream.write(total.getBytes());
                outputStream.write("\n".getBytes());
                outputStream.close();

            } catch (Exception ex) {
                // SnackBar to tell user that there is no text file
                Snackbar snackbar = Snackbar.make(findViewById(R.id.tv_score), R.string.WriteToFileError, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maths_quiz_main);


        btn_start = findViewById(R.id.btn_start);
        btnExitfromMathsMain = findViewById(R.id.btnExitfromMathsMain);
        btn_answer0 =  findViewById(R.id.btn_answer0);
        btn_answer1 = findViewById(R.id.btn_answer1);
        btn_answer2 = findViewById(R.id.btn_answer2);
        btn_answer3 = findViewById(R.id.btn_answer3);

        tv_score = findViewById(R.id.tv_score);
        tv_bottommessage = findViewById(R.id.tv_bottommessage);
        tv_questions = findViewById(R.id.tv_questions);
        tv_timer = findViewById(R.id.tv_timer);

        prog_timer = findViewById(R.id.prog_timer);

        tv_timer.setText("0Sec");
        tv_questions.setText("");
        tv_bottommessage.setText("Press Go");
        tv_score.setText("0pts");
        prog_timer.setProgress(0);

        View.OnClickListener startButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_button = (Button) v;

                start_button.setVisibility(View.INVISIBLE);
                btnExitfromMathsMain.setVisibility(View.INVISIBLE);
                secondsRemaining = 30;

                music = MediaPlayer.create (MathsQuizMainActivity.this, R.raw.mathsgamebackground);
                music.start();
                tv_score.setText("0");
                g = new Game();
                nextTurn();
                timer.start();
            }
        };

        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;

                try {
                    int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                    g.checkAnswer(answerSelected);
                    tv_score.setText(Integer.toString(g.getScore()));
                    nextTurn();
                }catch(Exception ex){

                    // SnackBar to tell user to press the go button to begin game
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.btn_answer0), R.string.PressGo, Snackbar.LENGTH_LONG);
                    snackbar.show();

                }

            }
        };

        btn_start.setOnClickListener(startButtonClickListener);

        btn_answer0.setOnClickListener(answerButtonClickListener);
        btn_answer1.setOnClickListener(answerButtonClickListener);
        btn_answer2.setOnClickListener(answerButtonClickListener);
        btn_answer3.setOnClickListener(answerButtonClickListener);
    }

    private void nextTurn() {
        // create a new question.
        // set text on answer buttons.
        // enable answer buttons
        // start the timer

        g.makeNewQuestion();
        int [] answer = g.getCurrentQuestion().getAnswerArray();

        btn_answer0.setText(Integer.toString(answer[0]));
        btn_answer1.setText(Integer.toString(answer[1]));
        btn_answer2.setText(Integer.toString(answer[2]));
        btn_answer3.setText(Integer.toString(answer[3]));

        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);

        tv_questions.setText(g.getCurrentQuestion().getQuestionPhase());

        tv_bottommessage.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1) );
    }

    public void navMathsQuizExitToMenu(View view)
    {
        // Create an action
        Intent MenuScreen = new Intent(getApplicationContext(), MenuActivity.class);
        // Tell it to do it
        startActivity(MenuScreen);
    }


}
