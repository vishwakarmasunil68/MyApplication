package com.example.emobi5.myapplication;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
 * Created by emobi5 on 5/4/2016.
 */
public class BalanceDetail extends AppCompatActivity {
    Button Ok;


    EditText pin, email, login, pass;
    public static String password;
    private static final String REGISTER_URL = "http://fiyoorepay.net/hooks/getBalanceInfoHook.php";

    public static final String KEY_USERNAME = "method";
    public static final String KEY_USER = "account_user_id";
    public static final String KEY_PASSWORD = "account_user_pin_no";
    BroadcastReceiver myReceiver;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.blnc);
        Ok=(Button)findViewById(R.id.btn_ok);
        pin = (EditText) findViewById(R.id.edittext_pin);
           Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == Ok) {
                    registerUser();
                }

            }
        });

    }
    private void registerUser(){

          password = pin.getText().toString().trim();
//        final String ema = email.getText().toString().trim();
//        final String user = login.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseJson(response);
                        if(progressDialog!=null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BalanceDetail.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                        if(progressDialog!=null) {
                            progressDialog.dismiss();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_USERNAME,"doMatchPin");
                params.put(KEY_PASSWORD,password);
                SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                params.put(KEY_USER, sp.getString("account_user_id",""));
                Log.e("sunil","password:-"+password+"\n"+"name:-"+sp.getString("account_user_id",""));
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog= new ProgressDialog(BalanceDetail.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
    public void parseJson(String json){
        try{
            JSONObject jsonObject=new JSONObject(json);
            String isSuccess=jsonObject.optString("isSuccess");
            String success_msg=jsonObject.optString("success_msg");
            Log.d("sunil",jsonObject.toString());
            if(isSuccess.equalsIgnoreCase("true")&&success_msg.equals("Success!")){
                JSONArray jsonArray=jsonObject.optJSONArray("result");
                if(jsonArray.length()>0) {
                    String account_user_id=jsonArray.getJSONObject(0).optString("account_user_id");
                    String account_service_charge=jsonArray.getJSONObject(0).optString("account_service_charge");
                    String account_user_name=jsonArray.getJSONObject(0).optString("account_user_name");
                    String account_user_email=jsonArray.getJSONObject(0).optString("account_user_email");
                    String account_user_password=jsonArray.getJSONObject(0).optString("account_user_password");
                    String account_user_cell=jsonArray.getJSONObject(0).optString("account_user_cell");
                    String account_user_tell=jsonArray.getJSONObject(0).optString("account_user_tell");
                    String account_user_address=jsonArray.getJSONObject(0).optString("account_user_address");
                    String account_user_wallet=jsonArray.getJSONObject(0).optString("account_user_wallet");
                    String account_user_status=jsonArray.getJSONObject(0).optString("account_user_status");
                    String account_user_passport=jsonArray.getJSONObject(0).optString("account_user_passport");
                    String account_user_expire_date=jsonArray.getJSONObject(0).optString("account_user_expire_date");
                    String account_user_credit_limit=jsonArray.getJSONObject(0).optString("account_user_credit_limit");
                    String account_user_terms_days=jsonArray.getJSONObject(0).optString("account_user_terms_days");
                    String account_user_num=jsonArray.getJSONObject(0).optString("account_user_num");

                    Intent balanceintent=new Intent(BalanceDetail.this,BalanceActi.class);
                    startActivity(balanceintent);
//                    Log.e("sunil","data"+account_user_id);
//                    Log.e("sunil","data"+account_service_charge);
//                    Log.e("sunil","data"+account_user_name);
//                    Log.e("sunil","data"+account_user_email);
//                    Log.e("sunil","data"+account_user_password);
//                    Log.e("sunil","data"+account_user_cell);
//                    Log.e("sunil","data"+account_user_address);
//                    Log.e("sunil","data"+account_user_wallet);
//                    Log.e("sunil","data"+account_user_status);
//                    Log.e("sunil","data"+account_user_passport);
//                    Log.e("sunil","data"+account_user_expire_date);
//                    Log.e("sunil","data"+account_user_credit_limit);
//                    Log.e("sunil","data"+account_user_terms_days);
//                    Log.e("sunil","data"+account_user_num);

                }
            }
            else{
                if(isSuccess.equals("false")){
                    Toast.makeText(BalanceDetail.this,"Failed",Toast.LENGTH_LONG).show();
                }
            }
        }
         catch (Exception e){
             Log.d("sunil",e.toString());
         }
    }


}
