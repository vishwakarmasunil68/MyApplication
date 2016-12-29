package com.example.emobi5.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import java.util.Calendar;

/**
 * Created by emobi5 on 5/4/2016.
 */
public class HomeActivity extends AppCompatActivity {
    private int previousSelectedPosition = -1;
    GridView grid;
    ShapeDrawable shapedrawable;
    Button blnc, tnsfr, stamnt, lgout;
    String[] web = {
            "BALANCE",
            "TRANSFER",
            "STATEMENT",
            "LOGOUT"

    };

    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);


     /*    blnc=(Button)findViewById(R.id.btn_bal_);
        tnsfr=(Button)findViewById(R.id.btn_tfr);
        stamnt=(Button)findViewById(R.id.btn_stmnt);
       lgout=(Button)findViewById(R.id.btn_logout);
*/
        shapedrawable = new ShapeDrawable();
        shapedrawable.getPaint().setColor(Color.MAGENTA);
//        shapedrawable.getPaint().setStrokeWidth(2);
        shapedrawable.getPaint().setStyle(Paint.Style.FILL);

//        lgout.setBackground(shapedrawable);
        CustomGrid adapter = new CustomGrid(HomeActivity.this, web);
        grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(adapter);
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                View previousSelectedView = (View) grid.getChildAt(previousSelectedPosition);

                // If there is a previous selected view exists
                if (previousSelectedPosition != -1) {
                    // Set the last selected View to deselect
                    previousSelectedView.setSelected(false);

                    // Set the last selected View background color as deselected item
                    previousSelectedView.setBackgroundColor(Color.parseColor("#FFFF4F25"));

                    // Set the last selected View text color as deselected item
//                    previousSelectedView.setTextColor(Color.DKGRAY);
                }

                // Set the current selected view position as previousSelectedPosition
                previousSelectedPosition = position;


//                grid.setItemChecked(position, true);
                switch (position){
                    case 0:
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawablebckground));
                        grid.setItemChecked(position, true);
                        Intent intent1=new Intent(HomeActivity.this,BalanceDetail.class);
                        startActivity(intent1);

                        break;
                    case 1:
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawablebckground));
                        Intent intent2=new Intent(HomeActivity.this,TransferActivity.class);
                        startActivity(intent2);
                        break;
                    case 2:
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawablebckground));
                        Intent intent3=new Intent(HomeActivity.this,StmtActivity.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent=new Intent(HomeActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finishAffinity();
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawablebckground));
                        break;
                    default:
                        break;
                }
                /*switch (view){
                    case 0:
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawablebckground));
                        break;
                    case 1:
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawablebckground));
                        break;
                    case 2:
                        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.drawablebckground));
                        break;
                    case 3:
                        break;
                    default:
                        break;
                }*/


            }
        });
        Intent myIntent = new Intent(getBaseContext(),
                MyScheduledReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0,
                myIntent, 0);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 60);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);
        Log.v("Second Activity", "Alarm Scheduled");
    }
}

  /*  public void onClick(View v) {
   Toast.makeText(getApplicationContext(), "You Clicked at " +web[position] , Toast.LENGTH_LONG).show();
        switch (v.getId()){
            case R.id.btn_bal_:
                blnc.setBackground(shapedrawable);
                *//*final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");
                final PackageManager pm = getPackageManager();
                final List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                ResolveInfo best = null;
                for (final ResolveInfo info : matches)
                    if (info.activityInfo.packageName.endsWith(".gm") ||
                            info.activityInfo.name.toLowerCase().contains("gmail")) best = info;
                if (best != null)
                    intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);
                startActivity(intent);*//*
                Intent intent1=new Intent(HomeActivity.this,BalanceDetail.class);
                startActivity(intent1);

                break;
            case R.id.btn_tfr:
                tnsfr.setBackground(shapedrawable);
                Intent intent2=new Intent(HomeActivity.this,TrnsferActivity.class);
                startActivity(intent2);

                break;
            case R.id.btn_stmnt:
                stamnt.setBackground(shapedrawable);
                Intent intent3=new Intent(HomeActivity.this,StmtActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_logout:
                lgout.setBackground(shapedrawable);
                break;
            default:
                break;
        }
    }*/
//}

