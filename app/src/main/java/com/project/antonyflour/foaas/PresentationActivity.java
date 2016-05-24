package com.project.antonyflour.foaas;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class PresentationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presentation);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent i = new Intent(PresentationActivity.this, MainActivity.class);
                startActivity(i);
                PresentationActivity.this.finish();
            }
        }, 2000);
    }
}
