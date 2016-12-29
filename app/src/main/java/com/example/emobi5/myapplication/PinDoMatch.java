package com.example.emobi5.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 6/21/2016.
 */
public class PinDoMatch extends AppCompatActivity {
    private static final String REGISTER_URL = "http://oldmaker.com/ecs/hooks/getBalanceInfoHook.php";

    public static final String KEY_USERNAME = "method";
    public static final String KEY_USER = "account_user_id";
    public static final String KEY_USER_BALANCE = "balance_perticular";
    public static final String KEY_USER_SENDER = "account_user_id_sender";
    public static final String KEY_USER_BALANCELEFT = "balance_left_amount";
    public static final String KEY_USER_BALANCEAMOUNT= "balance_amount";
    public static final String KEY_USER_RECEIEVER = "account_user_id_receiver ";

    Button btn_history;
    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stmntdetail);
        btn_history=(Button)findViewById(R.id.btn_history);
        layout=(RelativeLayout)findViewById(R.id.rlayout);
        btn_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    private void registerUser(){
//        final String password = pass.getText().toString().trim();
//        final String ema = email.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("Postdat", "" + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(PinDoMatch.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,"doBalanceTransfer");
                SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                params.put(KEY_USER, sp.getString("account_user_id",""));
//                params.put(KEY_USER, LoginActivity.name);
                params.put(KEY_USER_BALANCE,"");
                params.put(KEY_USER_SENDER, "");
                params.put(KEY_USER_BALANCELEFT,"");
                params.put(KEY_USER_BALANCEAMOUNT,"");
                params.put(KEY_USER_RECEIEVER,"");
//                Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
