package com.example.emobi5.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class DepositActivity extends AppCompatActivity {
    TextView date_tv,time_tv;
    EditText amount_et;
    Button deposit;
    String date_string,time_string,amount_string;
    private ProgressDialog progressDialog;
    EditText reference_et;
    String trans_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);
        date_tv= (TextView) findViewById(R.id.date_tv);
        time_tv= (TextView) findViewById(R.id.time_tv);
        amount_et= (EditText) findViewById(R.id.amount_et);
        deposit= (Button) findViewById(R.id.deposit);
        reference_et= (EditText) findViewById(R.id.reference_et);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Deposit");
        getTransactionID("http://oldmaker.com/ecs/findtransid.php");
        Calendar now = Calendar.getInstance();
        date_tv.setText( now.get(Calendar.YEAR)+"-"+ now.get(Calendar.MONTH)+"-"+ now.get(Calendar.DAY_OF_MONTH));
        time_tv.setText( "Time:-"+now.get(Calendar.HOUR_OF_DAY)+":"+ now.get(Calendar.MINUTE)+":"+ now.get(Calendar.SECOND));

        deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amount_et.getText().toString().equals("")) {
                    Calendar now = Calendar.getInstance();
                    date_tv.setText(now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DAY_OF_MONTH));
                    time_tv.setText("Time:-"+now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND));

                    setDepositAPI("http://oldmaker.com/ecs/dodeoposite.php");

                }
            }
        });
    }

    public void getTransactionID(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("sunil",response);
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.optString("success");
                            if(success.equals("true")){
                                JSONObject jsonObject1=jsonObject.optJSONObject("result");
                                trans_id=jsonObject1.optString("transaction_id");
                            }
                            else{
                                Toast.makeText(getApplicationContext(),"you can't do deposit at this time",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(progressDialog!=null){
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sunil", "" + error);
                        if(progressDialog!=null){
                            progressDialog.dismiss();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                Map<String,String> params = new HashMap<String, String>();
                Calendar now=Calendar.getInstance();
                String date=now.get(Calendar.YEAR)+"-"+ now.get(Calendar.MONTH)+"-"+ now.get(Calendar.DAY_OF_MONTH);
                String time=now.get(Calendar.HOUR_OF_DAY)+":"+ now.get(Calendar.MINUTE)+":"+ now.get(Calendar.SECOND);
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                params.put("balance_datetime", formattedDate);
                params.put("balance_id", String.valueOf(Integer.parseInt(trans_id)+1));
                params.put("account_user_id", sp.getString("account_user_id",""));

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog= new ProgressDialog(DepositActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
    public void setDepositAPI(String url){
        date_string=date_tv.getText().toString();
        time_string=time_tv.getText().toString();
        amount_string=amount_et.getText().toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("sunil",response);
                            parseDepositData(response);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if(progressDialog!=null){
                            progressDialog.dismiss();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sunil", "" + error);
                        if(progressDialog!=null){
                            progressDialog.dismiss();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                Map<String,String> params = new HashMap<String, String>();
//                Log.d("sunil","trans_id:-"+trans_id);
                params.put("transaction_id", "45");
                Log.d("sunil","trans:-"+trans_id);
                params.put("balance_perticular", "");
                params.put("account_user_id_sender", sp.getString("account_user_id",""));
                Log.d("sunil","account:-"+sp.getString("account_user_id",""));
                params.put("account_user_id_receiver", "null");

                String waller=sp.getString("account_user_wallet","");
                Log.d("sunil","wallet:-"+waller);
                double waller_value=Double.parseDouble(waller);

                Log.d("sunil","waller:-"+waller);

                double left_waller=waller_value+Double.parseDouble(amount_et.getText().toString());
                Log.d("sunil","left_wallet:-"+left_waller);
                params.put("balance_left_amount", left_waller+"");

                params.put("balance_amount", amount_et.getText().toString());
                Log.d("sunil","amount:-"+amount_et.getText().toString());
                amount_string=left_waller+"";
                params.put("balance_status", "show");
//                date_string=date_tv.getText().toString();
//                time_string=time_tv.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                params.put("balance_datetime",formattedDate);
                Log.d("sunil","date:-"+formattedDate);
                params.put("account_user_id", sp.getString("account_user_id",""));
                params.put("balance_passed_by", "");
                params.put("balance_ref", reference_et.getText().toString());
                Log.d("sunil","reference:-"+reference_et.getText().toString());
                params.put("account_user_wallet", left_waller+"");
                params.put("balance_id","");

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog= new ProgressDialog(DepositActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
    public void parseDepositData(String response){
        try {
            JSONObject jsonObject = new JSONObject(response);
            String success = jsonObject.optString("success");
            if(success.equals("true")){
                JSONObject obj=jsonObject.getJSONObject("result");
                String balance_id=obj.optString("balance_id");
                String transaction_id=obj.optString("transaction_id");
                String balance_perticular=obj.optString("balance_perticular");
                String account_user_id_sender=obj.optString("account_user_id_sender");
                String account_user_id_receiver=obj.optString("account_user_id_receiver");
                String balance_left_amount=obj.optString("balance_left_amount");
                String balance_amount=obj.optString("balance_amount");
                String balance_status=obj.optString("balance_status");
                String balance_datetime=obj.optString("balance_datetime");
                String account_user_id=obj.optString("account_user_id");
                String balance_passed_by=obj.optString("balance_passed_by");
                String balance_ref=obj.optString("balance_ref");
                showDialog(balance_datetime,balance_amount,balance_left_amount);
                SharedPreferences sp=getSharedPreferences("Fiyoore.txt",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("account_user_wallet",amount_string);
                editor.commit();
            }
            else{
                Toast.makeText(DepositActivity.this,"Invalid PIN",Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
        }
    }
    public void showDialog(String date_time,String amount, String left_amount){
        final Dialog dialog1 = new Dialog(DepositActivity.this,android.R.style.Theme_Material_Dialog);
        dialog1.setContentView(R.layout.withdraw_dialog);
        dialog1.setTitle("Deposit Slip");
        dialog1.show();
        dialog1.setCancelable(false);
        TextView date_et= (TextView) dialog1.findViewById(R.id.date_et);
        TextView time_et= (TextView) dialog1.findViewById(R.id.time_et);
        TextView amount_et= (TextView) dialog1.findViewById(R.id.amount_et);
        TextView amount_et_left= (TextView) dialog1.findViewById(R.id.amount_et_left);
        TextView service_et= (TextView) dialog1.findViewById(R.id.service_et);
        TextView service= (TextView) dialog1.findViewById(R.id.service);

        try {
            String date = date_time.split(" ")[0];
            String time = date_time.split(" ")[1];
            date_et.setText(date);
            time_et.setText(time);
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
        }
        amount_et.setText(amount);
        amount_et_left.setText(left_amount);
        service_et.setVisibility(View.GONE);
        service.setVisibility(View.GONE);

        Button ok= (Button) dialog1.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent=new Intent(DepositActivity.this,HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent returnIntent = new Intent();
        returnIntent.putExtra("result","refresh");
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }
}
