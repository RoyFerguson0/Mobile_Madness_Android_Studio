package Starter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

import com.example.myfirstandroidapplication.R;

public class FacebookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);

        WebView myWebView = (WebView) findViewById(R.id.wvFacebookBrowser);
        myWebView.loadUrl("http://www.facebook.com");
    }
}