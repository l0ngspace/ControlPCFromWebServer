package com.controlpc;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private Button savebtn;
    private EditText edttext;

    public static final long NOTIFY_INTERVAL = 1 * 1000;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edttext = (EditText)findViewById(R.id.edttext);
        savebtn = (Button) findViewById(R.id.savebtn);

        Check_init(1);

        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Check_init(0);
            }
        });

        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
        mTimer.scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, NOTIFY_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {
        @Override
        public void run() {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    //code...
                    if(edttext.getText().toString().matches(""))
                        savebtn.setEnabled(false);
                    else
                        savebtn.setEnabled(true);
                }
            });
        }
    }

    private void Check_init(int ck){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        String savedString = sharedPref.getString("stringValue","nothing");
        if(savedString.equals("nothing")){
            if(ck == 0) {
                SharedPreferences sharedPref1 = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor editor = sharedPref1.edit();
                editor.putString("stringValue", edttext.getText().toString());
                editor.commit();
                //Start Another Activity
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                finish();
            }
        }else {
            //Start Another Activity
            startActivity(new Intent(MainActivity.this, MainActivity2.class));
            finish();
        }
    }
}