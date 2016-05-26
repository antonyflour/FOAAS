package com.project.antonyflour.foaas;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {

    EditText editTextFrom;
    EditText editTextTo;
    Spinner spinnerType;
    Spinner spinnerLang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextFrom = (EditText) findViewById(R.id.editTextFrom);
        editTextTo = (EditText) findViewById(R.id.editTextTo);
        spinnerType = (Spinner) findViewById(R.id.spinnerType);
        spinnerLang = (Spinner) findViewById(R.id.spinnerLang);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.buttonForward);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editTextFrom.getText().toString().isEmpty() || editTextTo.getText().toString().isEmpty()) {
                    Snackbar.make(view, getString(R.string.string_alert_button), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else{
                    Intent i = new Intent(MainActivity.this, ShowActivity.class);
                    i.putExtra("from", editTextFrom.getText().toString());
                    i.putExtra("to", editTextTo.getText().toString());
                    i.putExtra("type",spinnerType.getSelectedItem().toString());
                    i.putExtra("lang",spinnerLang.getSelectedItem().toString());
                    startActivity(i);
                }
            }
        });
    }

    public void clear(View v){
        int id = v.getId();
        switch (id){
            case R.id.editTextFrom:
                ((EditText)findViewById(id)).setText("");
                break;
            case R.id.editTextTo:
                ((EditText)findViewById(id)).setText("");
        }
    }
}
