package com.controlpc;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity2 extends AppCompatActivity {

    private Button btn_shutdown;
    private Button btn_restart;
    private Button btn_standby;
    private Button btn_hibernate;
    private Button btn_delete;
    private Button btn_change;
    private Button btn_exit;

    private TextView tv_status;

    String URL = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        btn_shutdown = (Button)findViewById(R.id.btn_shutdown);
        btn_restart = (Button)findViewById(R.id.btn_restart);
        btn_standby = (Button)findViewById(R.id.btn_standby);
        btn_hibernate = (Button)findViewById(R.id.btn_hibernate);
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_change = (Button)findViewById(R.id.btn_change);
        btn_exit = (Button)findViewById(R.id.btn_exit);

        tv_status = (TextView)findViewById(R.id.tv_status);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String savedString = sharedPref.getString("stringValue","nothing");
        if(savedString != "nothing"){
            URL = savedString;
        }

        //Shutdown
        btn_shutdown = (Button)findViewById(R.id.btn_shutdown);
        btn_shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetR(URL + "SHUTDOWN", 1);
                SetNULL();
                tv_status.setText("Status: Send Request GET Success (Shutdown)");
            }
        });

        //Restart
        btn_restart = (Button)findViewById(R.id.btn_restart);
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetR(URL + "RESTART", 2);
                SetNULL();
                tv_status.setText("Status: Send Request GET Success (Restart)");
            }
        });

        //Standby
        btn_standby = (Button)findViewById(R.id.btn_standby);
        btn_standby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetR(URL + "STANDBY" , 3);
                SetNULL();
                tv_status.setText("Status: Send Request GET Success (Standby)");
            }
        });

        //Hibernate
        btn_hibernate = (Button)findViewById(R.id.btn_hibernate);
        btn_hibernate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetR(URL + "HIBERNATE", 4);
                SetNULL();
                tv_status.setText("Status: Send Request GET Success (Hibernate)");
            }
        });

        //Delete My Self
        btn_delete = (Button)findViewById(R.id.btn_delete);
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetR(URL + "DELETEMYSELF", 5);
                SetNULL();
                tv_status.setText("Status: Send Request GET Success (Delete Application)");
            }
        });

        //Change URL
        btn_change = (Button)findViewById(R.id.btn_change);
        btn_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this);
                builder.setTitle("Change URL");
				// Set up the input
                final EditText input = new EditText(MainActivity2.this);
                input.setText(URL);
				// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_DATETIME_VARIATION_NORMAL);
                builder.setView(input);
				// Set up the buttons
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       ChangeURLPref(input.getText().toString());
                       URL = input.getText().toString();
                        Toast.makeText(getApplicationContext(), "Saved URL: " + URL, Toast.LENGTH_LONG).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();
            }
        });

        //Exit App
        btn_exit = (Button)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void SetNULL(){
        new CountDownTimer(3000, 3000) {
            public void onTick(long millisUntilFinished) {
                Log.i("CountdownTimer ---->","seconds remaining: " + millisUntilFinished / 1000);
            }
            public void onFinish() {
                Log.i("CountdownTimer","Done!");
                GetR(URL + "NULL", 0);
            }
        }.start();
    }

    private void GetR(String ur, final int code){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = ur;
		// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.i("Volley Response ---->","Response is: NULL");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ERROR VOLLEY ----->","That didn't work! because: " + error.getMessage());
                if(code == 1){
                    tv_status.setText("Status: Error (Shutdown): " + error.getMessage());
                }else if(code == 2){
                    tv_status.setText("Status: Error (Restart): " + error.getMessage());
                }else if(code == 3){
                    tv_status.setText("Status: Error (Standby): " + error.getMessage());
                }else if(code == 4){
                    tv_status.setText("Status: Error (Hibernate): " + error.getMessage());
                }else if(code == 5){
                    tv_status.setText("Status: Error (Delete Application): " + error.getMessage());
                }
            }
        });
		// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void ChangeURLPref(String data){
        SharedPreferences sharedPref1 = PreferenceManager.getDefaultSharedPreferences(MainActivity2.this);
        SharedPreferences.Editor editor = sharedPref1.edit();
        editor.putString("stringValue", data);
        editor.commit();
    }
}