package com.project.antonyflour.foaas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowActivity extends AppCompatActivity {

    TextView textViewFuck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        String from = getIntent().getStringExtra("from");
        String to = getIntent().getStringExtra("to");
        String type = getIntent().getStringExtra("type");
        String lang = getIntent().getStringExtra("lang");
        textViewFuck = (TextView) findViewById(R.id.textViewFuck);
        textViewFuck.setText(from+" "+to+" "+type+" "+lang);
    }
}
