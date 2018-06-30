package io.condorlabs.skills_test.Base.App;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.condorlabs.skills_test.Utils.DialogsManager;


public class BaseActivity extends AppCompatActivity {



    protected OnBackPressedListener onBackPressedListener;

    public interface OnBackPressedListener {
        void doBack();
    }

    public void setOnBackPressedListener(OnBackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MyApp.setCurrentActivity(this);



    }
    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.doBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        onBackPressedListener = null;
        super.onDestroy();
    }


    @Override
    protected void onResume() {
        super.onResume();

        MyApp.setCurrentActivity(this);

    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}