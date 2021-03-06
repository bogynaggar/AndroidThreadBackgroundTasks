package com.mzdhr.androidthreadbackgroundtasks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.mzdhr.androidthreadbackgroundtasks.broadcast.MyFirstReceiver;
import com.mzdhr.androidthreadbackgroundtasks.broadcast.MyLocalReceiver;

public class Main7Activity extends AppCompatActivity {
    private static final String TAG = "Main7Activity";

    // Dynamic Broadcast Receiver
    private MyFirstReceiver mMyFirstReceiver;

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    // Local Broadcast
    private LocalBroadcastManager mLocalBroadcastManager;
    private BroadcastReceiver mResultReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int result = intent.getIntExtra("number", 0);
            Log.d(TAG, "onReceive: result ->" + result);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Init Broadcast for using it as dynamic one
        mMyFirstReceiver = new MyFirstReceiver();

        // Init Local Broadcast Manager
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                // Send broadcast
                Intent intent = new Intent(Main7Activity.this, MyFirstReceiver.class);
                sendBroadcast(intent);

                // Send broadcast by Action
                Intent intent2 = new Intent("my.custom.action.name");
                sendBroadcast(intent2);

                // Send to Our MyLocalReceiver Broadcast
                Intent intent3 = new Intent(Main7Activity.this, MyLocalReceiver.class);
                intent.putExtra("number", 2);
                sendBroadcast(intent3);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        registerReceiver(mMyFirstReceiver, intentFilter);

        // Local Broadcast
        IntentFilter intentFilter2 = new IntentFilter("my.result.intent");
        mLocalBroadcastManager.registerReceiver(mResultReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mMyFirstReceiver);

        // Local Broadcast
        mLocalBroadcastManager.unregisterReceiver(mResultReceiver);
    }
}
