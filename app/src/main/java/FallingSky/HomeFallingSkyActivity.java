package FallingSky;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.myfirstandroidapplication.R;

import Starter.MenuActivity;

public class HomeFallingSkyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_falling_sky);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public void startGame(View view){
        GameView gameView = new GameView(this);
        setContentView(gameView);
    }

    public void navFallingSkyExitToMenu(View view)
    {
        // Create an action
        Intent MenuScreen = new Intent(getApplicationContext(), MenuActivity.class);
        // Tell it to do it
        startActivity(MenuScreen);
    }

}