package com.bishal.facex;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ProgressBar progress;
    private ProgressBar Progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Progress = (ProgressBar) findViewById(R.id.progressBar);
        DiagnosticsTask diag = new DiagnosticsTask();
        diag.execute();
    }

    private class DiagnosticsTask extends AsyncTask<String, String, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            return null;
        }

        //Show spinner
        protected void onPreExecute() {
            //dialog.setMessage("Loading corresponding destinations...");
            //dialog.show();
            Progress.setVisibility(View.VISIBLE);
            Progress.showContextMenu();
            Log.e("AsyncStatus", "spinner shown");


            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                    finish();
                }
            }, 2500);
        }
    }
}
