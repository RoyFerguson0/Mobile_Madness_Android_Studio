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
//import com.example.myfirstandroidapplication.databinding.ActivityMathsQuizMainBinding;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import FallingSky.GameOver;
import Starter.AppData;
import Starter.LeaderboardActivity;
import Starter.MenuActivity;

public class MathsQuizMainActivity extends AppCompatActivity {

    Button btn_start, btnExitfromMathsMain, btn_answer0, btn_answer1, btn_answer2, btn_answer3;
    TextView tv_score, tv_questions, tv_timer, tv_bottommessage;
    ProgressBar prog_timer;

    FileOutputStream outputStream;

    Game g = new Game();

    int secondsRemaining = 30;

//    private ActivityMathsQuizMainBinding binding;
    public static final String NOTIFICATION_CHANNEL_ID = "4655";

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

            String one = String.valueOf(g.getNumberCorrect());
            String two = String.valueOf(g.getTotalQuestions() - 1);
//            try{
//                outputStream = openFileOutput("GameHighScores", Context.MODE_PRIVATE);
//                String highScore = String.valueOf(g.getNumberCorrect());
//                String totalQuestions = String.valueOf(g.getTotalQuestions() - 1);
//
//
//
//                outputStream.write(highScore.getBytes());
//                outputStream.write("\n".getBytes());
//                outputStream.write(totalQuestions.getBytes());
//                outputStream.close();
//
//            }catch (Exception ex){
//
//            }




            LeaderboardActivity test = new LeaderboardActivity();
            String value = String.valueOf(g.getNumberCorrect());
            test.setMathsQuizHighScore(value);

            AppData.LeaderBoard.setMathsQuizHighScore(value);
            System.out.println(AppData.LeaderBoard.getMathsQuizHighScore());
            String MathsQuizHighScore = AppData.LeaderBoard.getMathsQuizHighScore();

//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage("phoneNo", null, "sms message", null, null);
//


//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MathsQuizMainActivity.this)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setContentTitle("textTitle")
//                    .setContentText("textContent")
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MathsQuizMainActivity.this);
////
//// notificationId is a unique int for each notification that you must define.
//            notificationManager.notify(1, mBuilder.build());




//            final NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400})
//                    .setSound(null)
//                    .setContent(contentView)
//                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                    .setTicker(sTimer)
//                    .setContentIntent(timerListIntent)
//                    .setAutoCancel(false);


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








//            Notification notification = new Notification.Builder(MathsQuizMainActivity.this)
//                    .setContentTitle("New Message")
//                    .setContentText("You've received new messages.")
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .build();
//
//            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MathsQuizMainActivity.this);
//            // notificationId is a unique int for each notification that you must define.
//                        notificationManager.notify(1, notification);
//            try {
//                outputStream = openFileOutput("GameHighScores", Context.MODE_PRIVATE);
//
//
////            outputStream.write(FallingSkyHighScore.getBytes());
////            outputStream.write("\n".getBytes());
//                outputStream.write(MathsQuizHighScore.getBytes());
//                outputStream.write("\n".getBytes());
////            outputStream.write(MathsQuizTotalQuestions.getBytes());
////            outputStream.write("\n".getBytes());
////            outputStream.write(MathsQuizTotalPoints.getBytes());
////            outputStream.write("\n".getBytes());
////            outputStream.write(MathsQuizHighScore2.getBytes());
////            outputStream.write("\n".getBytes());
////            outputStream.write(MathsQuizTotalQuestions2.getBytes());
////            outputStream.write("\n".getBytes());
////            outputStream.write(MathsQuizTotalPoints2.getBytes());
//
//                outputStream.close();
//
//                Log.i("High Score", "Operator is :::::::: " + MathsQuizHighScore);
//                // Notification Wrote to file
//
//            }catch (Exception ex){
//                //    lblGrade.setText("Error writing to file!!!");
//                Log.i("ERROR", "NOT IN TRY CATCH");
//            }
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




    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "foxandroidReminderChannel";
            String description = "Channel For Alarm Manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("foxandroid", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }




    public static final int PRIMARY_FOREGROUND_NOTIF_SERVICE_ID = 1001;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        binding = ActivityMathsQuizMainBinding.inflate(getLayoutInflater());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
//                String id = "_channel_01";
//                int importance = NotificationManager.IMPORTANCE_LOW;
//                NotificationChannel mChannel = new NotificationChannel(id, "notification", importance);
//                mChannel.enableLights(true);
//
//                Notification notification = new Notification.Builder(getApplicationContext(), id)
//                        .setSmallIcon(R.mipmap.ic_launcher)
//                        .setContentTitle("My chat")
//                        .setContentText("Listening for incoming messages")
//                        .build();
//
//                NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                if (mNotificationManager != null) {
//                    mNotificationManager.createNotificationChannel(mChannel);
//                    mNotificationManager.notify(PRIMARY_FOREGROUND_NOTIF_SERVICE_ID, notification);
//                }
//            }


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

//                createNotificationChannel();

                g = new Game();
                nextTurn();
                timer.start();

//                createNotificationChannel();

            }
        };

        View.OnClickListener answerButtonClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button buttonClicked = (Button) v;

                int answerSelected = Integer.parseInt(buttonClicked.getText().toString());

                g.checkAnswer(answerSelected);
                tv_score.setText(Integer.toString(g.getScore()));
                nextTurn();

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
