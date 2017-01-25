package com.example.seedcommando_9.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String string = bundle.getString(MyIntentService.FILEPATH);
                int resultCode = bundle.getInt(MyIntentService.RESULT);
                if (resultCode == RESULT_OK) {
                    Toast.makeText(MainActivity.this,"Download complete. Download URI: " + string,
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download done");
                } else {
                    Toast.makeText(MainActivity.this, "Download failed",
                            Toast.LENGTH_LONG).show();
                    textView.setText("Download failed");
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.t2);


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, new IntentFilter(
                MyIntentService.NOTIFICATION));
    }
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }

    public void click(View view) {

        Intent intent = new Intent(this, MyIntentService.class);
        // add infos for the service which file to download and where to store
        intent.putExtra(MyIntentService.FILENAME,"index.html");
        intent.putExtra(MyIntentService.URL,
                "http://www.vogella.com/index.html");
        startService(intent);
        textView.setText("Service started");
    }
}
