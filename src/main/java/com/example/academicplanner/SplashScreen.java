package com.example.academicplanner;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000;
    private TextView appTitleTextView;
    private TextView appTaglineTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        appTitleTextView = findViewById(R.id.app_title);
        appTaglineTextView = findViewById(R.id.app_tagline);

        ViewTreeObserver viewTreeObserver = appTitleTextView.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                appTitleTextView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                Animation slideUp = new TranslateAnimation(0, 0, appTitleTextView.getHeight(), 0);
                slideUp.setDuration(2000);

                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(slideUp);
                appTitleTextView.startAnimation(animationSet);
                appTaglineTextView.startAnimation(animationSet);
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

                finish();
            }
        }, SPLASH_DURATION);
    }
}
