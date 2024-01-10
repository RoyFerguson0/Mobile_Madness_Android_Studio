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

    // Setting the countdown timer that is taking 1 second away
    CountDownTimer timer = new CountDownTimer(30000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            secondsRemaining--;
            tv_timer.setText(Integer.toString(secondsRemaining) + "sec");
            prog_timer.setProgress(30 - secondsRemaining);
        }

        // When the Game is finished the last function to be called
        @Override
        public void onFinish() {
            // Setting answer buttons to enabled false so that you are unable to select them.
            btn_answer0.setEnabled(false);
            btn_answer1.setEnabled(false);
            btn_answer2.setEnabled(false);
            btn_answer3.setEnabled(false);

            // Display the message at the bottom of the screen with the number of answered correct vs the total number of questions asked.
            tv_bottommessage.setText("Time is up! " + g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1));

            // Stop the Media Playing
            music.stop();




            String[] stored = new String[3];
            int[] stored2 = new int[3];
            int score1 = 0, score2 = 0, score3 = 0;
            try{

                // Open the "MathsGameHighScore" textfile
                inStream = openFileInput("MathsGameHighScore");

                // Read all the data in using InputStreamReader and BufferedReader
                if(inStream != null) {
                    InputStreamReader inputReader = new InputStreamReader(inStream);
                    BufferedReader buffReader = new BufferedReader(inputReader);

                    String line = "";

                    int position = 0;

                    // Read each line in text file
                    while ((line = buffReader.readLine()) != null) {
                        // Store line in two arrays String and Int array.
                        stored[position] = line;
                        stored2[position] = Integer.parseInt(line);
                        position++;
                    }


                }
                    inStream.close();

            }catch(Exception ex){
            }

            // Sort the array in Ascending order
            // Save each array value to a int variable
            Arrays.sort(stored2);
            score1 = stored2[2];
            score2 = stored2[1];
            score3 = stored2[0];

            // Save the current score in game to int variable
            String highScore = tv_score.getText().toString();
            int intHighScore = Integer.parseInt(highScore);

            // if the array is null (No values)
            if(stored[0] == null || stored[1] == null || stored[2] == null){
                // Writting out to the Text file
                try {
                    outputStream = openFileOutput("MathsGameHighScore", Context.MODE_APPEND);
                    // You append the current score to the text file.
                    outputStream.write(highScore.getBytes());
                    outputStream.write("\n".getBytes());
                    outputStream.close();

                }catch (Exception ex){
                    // SnackBar to tell user that there is no text file
                    Snackbar snackbar = Snackbar.make(findViewById(R.id.tv_score), R.string.WriteToFileError, Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }else {
                // If the array has got 3 values then
                // you take the high score and compare it to the 3 scores in textfile
                if(intHighScore > score3){
                    score3 = intHighScore;
                }else if (intHighScore > score2){
                    score2 = intHighScore;
                }else if (intHighScore > score1){
                    score1 = intHighScore;
                }

                // Restoring the values in the array incase that value has changed due to maybe having new high score.
                stored2[0] = score1;
                stored2[1] = score2;
                stored2[2] = score3;
                // Sort the array from Ascending over.
                Arrays.sort(stored2);

                // Save the values to string variables
                String score4 = String.valueOf(stored2[2]);
                String score5 = String.valueOf(stored2[1]);
                String score6 = String.valueOf(stored2[0]);


                // Writting out to the Text file
                try {
                    outputStream = openFileOutput("MathsGameHighScore", Context.MODE_PRIVATE);

                    // Write the scores out to textfile so that the highest is being show first
                    // And the smallest is the last value.
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

            // Getting the number correct and the total questions asked
            // So you can display in the notification pop up
            // Store the number correct and the total questions to string variables
            String correct = String.valueOf(g.getNumberCorrect());
            String total = String.valueOf(g.getTotalQuestions() -1);

            // call writeScoreToFile - it is going to compare the correct vs total against what is stored in textfile.
            writeScoreToFile(correct, total);

            // Notification output with icon, content title and content text.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                String id = "_channel_01";
                int importance = NotificationManager.IMPORTANCE_LOW;
                NotificationChannel mChannel = new NotificationChannel(id, "notification", importance);
                mChannel.enableLights(true);

                Notification notification = new Notification.Builder(getApplicationContext(), id)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("Game Over")
                        .setContentText("Time is up! You Scored: " + correct + "/" + total)
                        .build();

                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (mNotificationManager != null) {
                    mNotificationManager.createNotificationChannel(mChannel);
                    mNotificationManager.notify(PRIMARY_FOREGROUND_NOTIF_SERVICE_ID, notification);
                }
            }

            final Handler handler = new Handler();

            // Delay that last four seconds - freezes the screen
            // then the start button and exit button become visiable again.
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    btn_start.setVisibility(View.VISIBLE);
                    btnExitfromMathsMain.setVisibility(View.VISIBLE);
                }
            }, 4000);


        } // End onFinish
    };


    // Going to compare the correct vs total against what is stored in textfile
    public void writeScoreToFile(String correct, String total){
        String[] stored = new String[2];
        int[] stored2 = new int[2];
        int score1 = 0, score2 = 0;

        int total2 = Integer.parseInt(total);
        int correct2 = Integer.parseInt(correct);

        try{
            // reading the textfile lines from MathsGameTotalScore
            inStream = openFileInput("MathsGameTotalScore");

            // Using a InputStreamReader and BufferedReader to read each line in textfile.
            if(inStream != null) {
                InputStreamReader inputReader = new InputStreamReader(inStream);
                BufferedReader buffReader = new BufferedReader(inputReader);

                String line = "";

                int position = 0;

                // Reading each line and storing value in string and int array
                while ((line = buffReader.readLine()) != null) {
                    stored[position] = line;
                    stored2[position] = Integer.parseInt(line);
                    position++;
                }

            }
            inStream.close();
        }catch(Exception ex){
        }

        // Store array values to int variables
        score1 = stored2[0];
        score2 = stored2[1];

        // if stored position 0 and 1 are null (no values) then
        if(stored[0] == null || stored[1] == null){
            // Writting out to the Text file if nothing has been added to it
            try {
                outputStream = openFileOutput("MathsGameTotalScore", Context.MODE_APPEND);

                // Writing the correct and total to textfile "MathsGameTotalScore"
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
            // Get the percent of the current correct vs total question
            // as well as the percent for the stored text value percent.
            double totalValue = (((double) score1 / score2) * 100);
            double currectTotalValue = (((double) correct2 / total2) * 100);

            // if the current percent is greater than the stored percentage value then
            if(currectTotalValue > totalValue) {

                // Writting out to the Text file
                try {
                    outputStream = openFileOutput("MathsGameTotalScore", Context.MODE_PRIVATE);

                    // Write the new number correct and total questions to text file.
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

            }else if(currectTotalValue == totalValue){
                // if the current percent and the stored percent are the same then
                // check if the value is bigger that what is being stored.
                if(correct2 > score1 && total2 > score2){
                    // Writting out to the Text file
                    try {
                        outputStream = openFileOutput("MathsGameTotalScore", Context.MODE_PRIVATE);
                        // write the new correct scores and total question to textfile
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
    } // end writeScoreToFile

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

        // When the start button (Go button) is pressed.
        View.OnClickListener startButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button start_button = (Button) v;

                // Make the start button and the exit button invisable
                start_button.setVisibility(View.INVISIBLE);
                btnExitfromMathsMain.setVisibility(View.INVISIBLE);
                // set the timer to 30 seconds
                secondsRemaining = 30;

                // Start playing the background music
                music = MediaPlayer.create (MathsQuizMainActivity.this, R.raw.mathsgamebackground);
                music.start();
                tv_score.setText("0");

                // Generate the Game
                g = new Game();

                // Start the question being asked and start the timer.
                nextTurn();
                timer.start();
            }
        }; // end start button onclicklistener

        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;

                try {
                    // get the value from the button selected
                    int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                    // call the function checkAnswer - will check the answer selected against correct answer.
                    g.checkAnswer(answerSelected);
                    // Setting the score
                    tv_score.setText(Integer.toString(g.getScore()));
                    // Get next question
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

    } // end onCreate

    private void nextTurn() {
        // create a new question.
        // set text on answer buttons.
        // enable answer buttons
        // start the timer

        // Call function makeNewQuestion
        g.makeNewQuestion();

        // putting the four value into array three wrong answer and the correct answer.
        int [] answer = g.getCurrentQuestion().getAnswerArray();

        btn_answer0.setText(Integer.toString(answer[0]));
        btn_answer1.setText(Integer.toString(answer[1]));
        btn_answer2.setText(Integer.toString(answer[2]));
        btn_answer3.setText(Integer.toString(answer[3]));

        btn_answer0.setEnabled(true);
        btn_answer1.setEnabled(true);
        btn_answer2.setEnabled(true);
        btn_answer3.setEnabled(true);

        // Setting the question by getting the questionPhase
        tv_questions.setText(g.getCurrentQuestion().getQuestionPhase());

        // Setting the number correct vs total questions asked.
        tv_bottommessage.setText(g.getNumberCorrect() + "/" + (g.getTotalQuestions() - 1) );
    }

    // function to move to another screen
    // when the exit button is pressed to go to Menu Activity screen.
    public void navMathsQuizExitToMenu(View view)
    {
        // Create an action to go to MenuActivity
        Intent MenuScreen = new Intent(getApplicationContext(), MenuActivity.class);
        // Tell it to do it
        startActivity(MenuScreen);
    }


}
