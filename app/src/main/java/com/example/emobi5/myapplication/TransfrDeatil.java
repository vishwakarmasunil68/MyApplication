package com.example.emobi5.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 5/6/2016.
 */
public class TransfrDeatil extends AppCompatActivity {
    private static final String REGISTER_URL = "http://oldmaker.com/ecs/hooks/getBalanceInfoHook.php";
    EditText matchpin;
    public static final String KEY_USERNAME = "method";
    public static final String KEY_USER = "account_user_id";
    public static final String KEY_PASSWORD = "account_user_num";
    TextView avail,total;
    Button Pay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trnsfrdtl);
        matchpin=(EditText)findViewById(R.id.matchpin);
        Pay=(Button)findViewById(R.id.Pay_btn);
        avail=(TextView)findViewById(R.id.availblc);
        total=(TextView)findViewById(R.id.totalblc);

        SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor=sp.edit();
        avail.setText(sp.getString("account_user_wallet","0.00"));
        total.setText(sp.getString("account_user_wallet","0.00"));
        Pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
                /*Intent intent=new Intent(TransfrDeatil.this,ActivityVerify.class);
               startActivity(intent);*/
            }
        });
    }
    private void registerUser(){

        final String password = matchpin.getText().toString().trim();
//        final String ema = email.getText().toString().trim();
//        final String user = login.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(TrnsferActivity.this, response, Toast.LENGTH_LONG).show();
                        Log.e("Postdat", "" + response);
                        try {
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data1!", Toast.LENGTH_LONG).show();

                            JSONObject objresponse = new JSONObject(response);
                            //					Toast.makeText(getApplicationContext(), "Could not retreive Data2!", Toast.LENGTH_LONG).show();

                            String success = objresponse.getString("isSuccess");
                            String success_msg = objresponse.getString("success_msg");

                            if (success.equalsIgnoreCase("true") || success_msg.equalsIgnoreCase("true")) {
                                /*Intent intent=new Intent(TrnsferActivity.this,HomeActivity.class);
                                startActivity(intent);*/
                                Log.e("Postdat", "" + response);
                                JSONArray jsonArray = objresponse.getJSONArray("result");


                                //Log.i("News Data", jsonArray.toString());

//                    JSONArray cast = jsonArray.getJSONArray("result");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    /*name = jsonObject.getString("account_user_name");
                                    UserName.setVisibility(View.VISIBLE);
                                    UserName.setText(name);
                                    UserName.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            Intent detailtrnsfr=new Intent(TrnsferActivity.this,TransfrDeatil.class);
                                            startActivity(detailtrnsfr);
                                        }
                                    });*/
//                                    Log.e("account_user_id",name);
//                                    Double user_long = jsonObject.getDouble("user_long");
//                                    Double user_lat = jsonObject.getDouble("user_lat");
//                                    UserType = "UserType: " + jsonObject.getString("usertype");
                                    /*Intent intent1 = new Intent(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent1);*/
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.w("Postdat", "" + error);
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,"depositebalance");
                params.put(KEY_USER,"84");
                params.put("account_user_id_receiver ","84");
                  params.put("balance_amount","100000");
                params.put("balance_perticular","hello");
                params.put("account_user_id_sender ",password);
                params.put("transaction_id","345");
                params.put("balance_left_amount","1000");
                params.put("balance_status","hello");
                params.put("balance_datetime ",password);
                params.put("balance_ref","345");
                params.put("balance_passed_by","1000");
//                    Toast.makeText(UserStatement.this, username +"success", Toast.LENGTH_LONG).show();
//                Toast.makeText(UserStatement.this, ema +"success", Toast.LENGTH_LONG).show();
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
