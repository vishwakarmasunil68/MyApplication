package com.example.emobi5.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by emobi5 on 5/6/2016.
 */
public class ActivityVerify extends AppCompatActivity {
    Context context;
    Button Okay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.verifylyutt);
        Okay = (Button) findViewById(R.id.btn_okay);
        Okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(ActivityVerify.this);
                dialog.setMessage("Do you want to pay");
                dialog.setPositiveButton(("Yes"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Toast.makeText(ActivityVerify.this,"Payment has done sucessfully",Toast.LENGTH_LONG).show();
                        // TODO Auto-generated method stub
                        /*Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        context.startActivity(myIntent);*/
                        //get gps
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        // TODO Auto-generated method stub

                    }
                });
                dialog.show();
            }

            });



}}




