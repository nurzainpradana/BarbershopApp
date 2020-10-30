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
import com.firmansyah.barbershop.view.signin.SignInAct;

public class SplashScreenAct extends AppCompatActivity {
    Animation appSplash;
    Animation btt;
    ImageView organizationLogo;
    ImageView mosque;
    ImageView muslimHandshake;
    TextView organizationName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        //load elemen
        organizationLogo = findViewById(R.id.organization_logo);
        organizationName = findViewById(R.id.organization_name);
        mosque = findViewById(R.id.background_mosque);
        muslimHandshake = findViewById(R.id.background_muslim_handshake);
        runAnimation();
    }

    private void runAnimation() {
        //Load animation
        appSplash = AnimationUtils.loadAnimation(this, R.anim.app_splash);
        btt = AnimationUtils.loadAnimation(this, R.anim.btt);

        //run animation
        organizationLogo.startAnimation(appSplash);
        organizationName.startAnimation(btt);

        mosque.setAnimation(appSplash);
        muslimHandshake.setAnimation(appSplash);

        Handler handler = new Handler();

        SharePref sharePref = new SharePref(getApplicationContext());
        String username = sharePref.getString(Const.USERNAME_KEY);
        if (username != null && !username.equals("")){
            handler.postDelayed(() -> {
                Intent gotomain = new Intent(SplashScreenAct.this, MainActivity.class);
                startActivity(gotomain);
                finish();
            }, 2000); //1000milisecon = 1 secon
        } else {
            handler.postDelayed(() -> {
                Intent gotosign = new Intent(SplashScreenAct.this, SignInAct.class);
                startActivity(gotosign);
                finish();
            }, 2000); //1000milisecon = 1 secon
        }
    }
}
