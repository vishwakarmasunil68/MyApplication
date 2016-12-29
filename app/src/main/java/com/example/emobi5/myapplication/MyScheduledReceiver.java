package com.example.emobi5.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by emobi5 on 5/19/2016.
 */
public class MyScheduledReceiver  extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent recievedIntent) {
        // TODO Auto-generated method stub
        Toast.makeText(context, "Session Timeout", Toast.LENGTH_LONG).show();
        Log.v("MyScheduledReceiver", "Intent Fired");

        Intent broadcastIntent = new Intent(context,LoginActivity.class);
        broadcastIntent.putExtra("finish",true);
        broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        broadcastIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(broadcastIntent);
        broadcastIntent.setAction("truiton.ACTION_FINISH");
        context.sendBroadcast(broadcastIntent);
    }
}
