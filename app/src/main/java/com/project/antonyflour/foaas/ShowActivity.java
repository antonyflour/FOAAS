package com.project.antonyflour.foaas;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import AnUnknownMiner.JFOAAS.Fuck;
import AnUnknownMiner.JFOAAS.JFOAAS;
import AnUnknownMiner.JFOAAS.Language;

public class ShowActivity extends AppCompatActivity {

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

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
        textViewFuck.setVisibility(View.INVISIBLE);
        String[] strs = {from, to, type, lang};
        if(isOnline()) {
            FuckAsyncTask fat = new FuckAsyncTask();
            fat.execute(strs);
        }
        else{
            textViewFuck.setText("No Internet Connection, Dickhead!");
            textViewFuck.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.show_activity_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                verifyStoragePermissions(this);
                Bitmap bitmap = takeScreenshot();
                bitmap = getResizedBitmap(bitmap, 200, 200);
                Uri uri = saveBitmap(bitmap);
                if(uri==null){
                    Toast.makeText(this,"Impossibile condividere",Toast.LENGTH_SHORT).show();
                    return false;
                }
                else {
                    File myImage = new File(uri.toString()); // introduce the new File
                    Uri myUri= Uri.fromFile(myImage);
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, myUri);
                    sendIntent.setType("image/jpeg");
                    startActivity(sendIntent);
                    return true;
                }
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * Take a screenshot
     * @return the bitmap screenshot
     */
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        return rootView.getDrawingCache();
    }

    /**
     * Save bitmap in storage directory as a image (.png or .jpg)
     * @param bitmap
     * @return the absolute path of the image
     */
    public Uri saveBitmap(Bitmap bitmap) {
        File imagePath = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "/screenshot.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return Uri.parse(imagePath.getAbsolutePath());
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
            return null;
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
            return null;
        }
    }

    /**
     * Resize the bitmap taking only the central part of the image
     * @param bm
     * @param newWidth
     * @param newHeight
     * @return the resized bitmap
     */
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        /*int x = bm.getWidth()/5;
        int y = bm.getHeight()/4;
        Bitmap cropped = Bitmap.createBitmap(bm, x, y, bm.getWidth() - 2*x, bm.getHeight() - 2*y);
        */
        return bm;
    }

    /**
     * Verify if the activity has got the storage permission
     * @param activity
     */
    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    /**
     * Check the Internet connection
     * @return true if the system is online
     */
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    class FuckAsyncTask extends AsyncTask<String,Void,String> {

        private ProgressDialog dialog = new ProgressDialog(ShowActivity.this);


        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();
        }

        protected String doInBackground(String... params) {
            JFOAAS j = new JFOAAS(params[0],params[1]);

            //check the type of the fuck
            switch(params[2]){
                case "OFF":
                    j.withFuck(Fuck.OFF);
                    break;
                case "YOU":
                    j.withFuck(Fuck.YOU);
                    break;
                case "SHUTUP":
                    j.withFuck(Fuck.SHUTUP);
                    break;
            }
            //check the language
            switch(params[3]){
                case "Italiano":
                    j.withLanguage(Language.ITALIAN);
                    break;
                case "English":
                    j.withLanguage(Language.ENGLISH);
                    break;
            }

            String fuck = j.build();
            return fuck;
        }


        protected void onPostExecute(final String fuck){


            ShowActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(fuck.isEmpty()) {
                        textViewFuck.setText(R.string.string_server_down);
                    }
                    textViewFuck.setText(fuck);
                    textViewFuck.setVisibility(View.VISIBLE);
                }
            });

            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    }
}
