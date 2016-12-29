package com.example.emobi5.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by emobi5 on 5/6/2016.
 */
public class BalanceActi extends AppCompatActivity {
    TextView avail,total;

    Button deposit,withdrawl,transfer,logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blncenquiry);
        avail=(TextView)findViewById(R.id.availblc);
        total=(TextView)findViewById(R.id.totalblc);
        transfer= (Button) findViewById(R.id.transfer);

        deposit= (Button) findViewById(R.id.deposit);
        withdrawl= (Button) findViewById(R.id.withdrawl);

        logout= (Button) findViewById(R.id.logout);
        refresh();

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BalanceActi.this,DepositActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        withdrawl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BalanceActi.this,WithdrawlActivity.class);
                startActivityForResult(intent, 1);
//                setWithdrawlAPI("http://oldmaker.com/ecs/dowithdraw.php");
            }
        });
        transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BalanceActi.this,TransferActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BalanceActi.this,LoginActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }
    public void refresh(){
        SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
        avail.setText(sp.getString("account_user_wallet","0.00"));
        total.setText(sp.getString("account_user_wallet","0.00"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("sunil","called");
        refresh();
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                String result=data.getStringExtra("result");
                Log.d("sunil",result);
                refresh();
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }
}
