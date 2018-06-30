package io.condorlabs.skills_test.Implementation.Splash.Controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.condorlabs.skills_test.Implementation.Movie.Controller.MovieListActivity;
import io.condorlabs.skills_test.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DELAY = 2000;

    private final Handler mHandler   = new Handler();
    private final Launcher mLauncher = new Launcher();


    @Override
    protected void onStart() {
        super.onStart();

        mHandler.postDelayed(mLauncher, SPLASH_DELAY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(mLauncher);
        super.onStop();
    }

    private void launch() {
        if (!isFinishing()) {
            finish();
            startActivity(new Intent(getApplicationContext(), MovieListActivity.class));
        }
    }

    private class Launcher implements Runnable {
        @Override
        public void run() {
            launch();
        }
    }
}
