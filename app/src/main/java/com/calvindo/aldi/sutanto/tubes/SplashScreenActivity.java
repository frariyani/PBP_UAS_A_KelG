package com.calvindo.aldi.sutanto.tubes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    private static String SHARED_NAME = "SPLASH";
    private static String FIRST_TIME = "FIRST_TIME";

    Animation topAnimation, bottomAnimation;
    TextView title, slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash_screen);
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        title = findViewById(R.id.title_tv);
        slogan = findViewById(R.id.slogan_tv);

        title.setAnimation(topAnimation);
        slogan.setAnimation(bottomAnimation);

        if(!checkFirstTime(this)){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }, SPLASH_SCREEN);
        }else{
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }
    }

    public static boolean checkFirstTime(Context ctx){
        SharedPreferences settings = ctx.getSharedPreferences(SHARED_NAME, 0);
        Boolean first = settings.getBoolean(FIRST_TIME, false);
        if(!first){
            settings = ctx.getSharedPreferences(SHARED_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean(FIRST_TIME, true);
            editor.commit();
        }
        return first;
    }
}