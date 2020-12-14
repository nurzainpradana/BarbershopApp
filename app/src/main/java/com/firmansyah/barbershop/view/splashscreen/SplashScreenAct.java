package com.firmansyah.barbershop.view.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.firmansyah.barbershop.R;
import com.firmansyah.barbershop.util.Const;
import com.firmansyah.barbershop.util.SharePref;
import com.firmansyah.barbershop.view.main.MainActivity;

public class SplashScreenAct extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        runAnimation();
    }

    private void runAnimation() {

        Handler handler = new Handler();

        handler.postDelayed(() -> {
            Intent gotomain = new Intent(SplashScreenAct.this, MainActivity.class);
            startActivity(gotomain);
            finish();
        }, 2000); //1000milisecon = 1 secon
    }
}
