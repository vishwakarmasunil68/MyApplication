package com.example.emobi5.myapplication;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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

public class TransferApiActivity extends AppCompatActivity {
    TextView date_tv,time_tv,balance_tv;
    EditText amount_et,reference_et;
    Button ok;
    String date_string,time_string,amount_string,amount_left_string,service_String;
    double user_account_amount,saved_user_amount,left_amount;
    double service_fees=0.00;
    ProgressDialog progressDialog;
    String sender_amount,reciever_amount_str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer_api);
        date_tv= (TextView) findViewById(R.id.date_tv);
        time_tv= (TextView) findViewById(R.id.time_tv);
        amount_et= (EditText) findViewById(R.id.amount_et);
        ok= (Button) findViewById(R.id.ok);
        balance_tv= (TextView) findViewById(R.id.balance_tv);
        reference_et= (EditText) findViewById(R.id.reference_et);

//        service_tv= (TextView) findViewById(R.id.service_tv);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Transfer");

        SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
        try{
            user_account_amount=Double.parseDouble(sp.getString("account_user_wallet","0.00"));
            balance_tv.setText(sp.getString("account_user_wallet","0.00"));
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
            user_account_amount=0.00;
        }
        Log.d("sunil",user_account_amount+"");
        amount_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                Log.d("sunil","ontextchanged");
                String amount=amount_et.getText().toString();
                try{
                    double amt=Double.parseDouble(amount);
//                    Log.d("sunil","amount:-"+amt);
                    SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                    String servicecharge=sp.getString("account_service_charge","");
                    Log.d("sunil","service fees:-"+sp.getString("account_service_charge",""));
                    if(!servicecharge.equals("")){
                        saved_user_amount=Double.parseDouble(servicecharge);
                        service_fees=(saved_user_amount*amt)/100;
                        Log.d("sunil","calculated service fees:-"+service_fees);
                        String amount_left=sp.getString("account_user_wallet","");
                        try{
                            double amnt=Double.parseDouble(amount_left);
                            double entered_amount=Double.parseDouble(s.toString());
                            Log.d("sunil","entered amount:-"+entered_amount);
                            Log.d("sunil","amnt left:-"+amnt);

                            if(service_fees==0.00){
                                if(entered_amount>amnt){
                                    Toast.makeText(getApplicationContext(),"amount cannot be greater than your balance amount",Toast.LENGTH_SHORT).show();
                                    ok.setVisibility(View.GONE);
                                }
                                else{
                                    ok.setVisibility(View.VISIBLE);
                                }
                            }
                            else{
                                if(entered_amount>=amnt){
                                    Toast.makeText(getApplicationContext(),"amount cannot be greater than your balance amount",Toast.LENGTH_SHORT).show();
                                    ok.setVisibility(View.GONE);
                                }
                                else{
                                    ok.setVisibility(View.VISIBLE);
                                }
                            }

                        }
                        catch (Exception e){
                            Log.d("sunil",e.toString());
                        }

//                        service_tv.setText(service_fees+"");
//                        Log.d("sunil",service_fees+"");
                    }
                    else{
                        Log.d("sunil","in else");
                    }

                }
                catch (Exception e){
//                    Log.d("sunil",e.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        Calendar now = Calendar.getInstance();
        date_string= now.get(Calendar.YEAR)+"-"+ now.get(Calendar.MONTH)+"-"+ now.get(Calendar.DAY_OF_MONTH);
        time_string= now.get(Calendar.HOUR_OF_DAY)+":"+ now.get(Calendar.MINUTE)+":"+ now.get(Calendar.SECOND);
        date_tv.setText(date_string);
        time_tv.setText(time_string);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amount_et.getText().toString().equals("")) {
                    if(user_account_amount<saved_user_amount){
                        Toast.makeText(getApplicationContext(),"amount cannot be greater than your account balance",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        validatePinDialog();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Please Enter Amount First",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void callServiceApi(String url, final String trans_id){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("sunil","response:-"+response);

                            JSONObject obj=new JSONObject(response);
                            String success=obj.optString("success");
                            if(success.equals("true")){
                                JSONObject obj1=obj.optJSONObject("result");
                                amount_string=amount_et.getText().toString();

                                SharedPreferences sp=getSharedPreferences("Fiyoore.txt",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putString("account_user_wallet",amount_left_string);
                                editor.commit();

                                showDialog();
                            }
                            else{
                                Toast.makeText(TransferApiActivity.this,"Invalid Pin",Toast.LENGTH_LONG).show();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("sunil", "error" + error);
//                        if(error.equals("com.android.volley.ServerError")){
                            amount_string=amount_et.getText().toString();

                            SharedPreferences sp=getSharedPreferences("Fiyoore.txt",Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=sp.edit();
                            editor.putString("account_user_wallet",amount_left_string);
                            editor.putString("account_user_wallet",sender_amount);
                        TransferActivity.reciever_wallet=reciever_amount_str;
                            editor.commit();

                            showDialog();



//                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                SharedPreferences sp=getSharedPreferences("Fiyoore.txt",Context.MODE_PRIVATE);
                Map<String,String> params = new HashMap<String, String>();
                params.put("transaction_id", trans_id);
                Log.d("sunil","transid:-"+trans_id);
                params.put("balance_perticular", "");
                params.put("account_user_id_sender", sp.getString("account_user_id",""));
                Log.d("sunil","account_user_id:-"+sp.getString("account_user_id",""));
                params.put("account_user_id_receiver", TransferActivity.reciever_id);
                Log.d("sunil","account_user_id_receiver:-"+TransferActivity.reciever_id);
                String waller=sp.getString("account_user_wallet","");
//                Log.d("sunil","wallet:-"+waller);
                double waller_value=Double.parseDouble(waller);

                double left_waller=waller_value-Double.parseDouble(amount_et.getText().toString())-service_fees;
                amount_left_string=left_waller+"";
                service_String=service_fees+"";
                params.put("balance_left_amount", left_waller+"");
                Log.d("sunil","left_Wallet:-"+left_waller+"");
                params.put("balance_amount", amount_et.getText().toString());
                Log.d("sunil","balance_amount:-"+amount_et.getText().toString());
                params.put("balance_status", "show");
//                date_string=date_tv.getText().toString();
//                time_string=time_tv.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df.format(c.getTime());
                params.put("balance_datetime",formattedDate);
                Log.d("sunil","balance_datetime:-"+formattedDate);
                params.put("account_user_id", sp.getString("account_user_id",""));
                Log.d("sunil","account_user_id:-"+ sp.getString("account_user_id",""));
                params.put("balance_passed_by", "");
                params.put("balance_ref", reference_et.getText().toString());
                Log.d("sunil","balance_ref:-"+ reference_et.getText().toString());
                params.put("account_user_wallet", left_waller+"");
                Log.d("sunil","account_user_wallet:-"+ left_waller+"");
                params.put("balance_id", "");
                params.put("account_user_num", TransferActivity.user_account_no);
                Log.d("sunil","account_user_num:-"+ TransferActivity.user_account_no);

                double reciever_amount=0.00;
                try {
                    reciever_amount=(Double.parseDouble(TransferActivity.reciever_wallet))+Double.parseDouble(amount_et.getText().toString().trim());
                }
                catch (Exception e){
                    reciever_amount=0.00;
                }
                Log.d("sunil","account_user_wallet_reciver:-"+reciever_amount);
                params.put("account_user_wallet_reciver",reciever_amount+"");
                Log.d("msg","service fees:-"+service_fees);
                Log.d("msg","service fees1:-"+service_String);
                params.put("sender_service_fees",service_String+"");
//                params.put("sender_service_fees","51");
                params.put("balance_left_amount_sender",left_waller+"");

                params.put("balance_left_amount_reciver",reciever_amount+"");
//                params.put("","0");
                    sender_amount=left_waller+"";
                reciever_amount_str=reciever_amount+"";
                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    public void showDialog(){
        final Dialog dialog1 = new Dialog(TransferApiActivity.this,android.R.style.Theme_Material_Dialog);
        dialog1.setContentView(R.layout.withdraw_dialog);
        dialog1.setTitle("Withdrawl Slip");
        dialog1.show();
        dialog1.setCancelable(false);
        TextView date_et= (TextView) dialog1.findViewById(R.id.date_et);
        TextView time_et= (TextView) dialog1.findViewById(R.id.time_et);
        TextView amount_et= (TextView) dialog1.findViewById(R.id.amount_et);
        TextView amount_et_left= (TextView) dialog1.findViewById(R.id.amount_et_left);
        TextView service_et= (TextView) dialog1.findViewById(R.id.service_et);

        date_et.setText(date_string);
        time_et.setText(time_string);
        amount_et.setText(amount_string);
        amount_et_left.setText(amount_left_string);
        service_et.setText(service_String);

        Button ok= (Button) dialog1.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Intent intent=new Intent(TransferApiActivity.this,HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    public void validatePinDialog(){
        final Dialog dialog1 = new Dialog(TransferApiActivity.this,android.R.style.Theme_Material_Dialog);
        dialog1.setContentView(R.layout.pin_dialog);
        dialog1.setTitle("Please put your pin number");
        dialog1.show();
        dialog1.setCancelable(false);
        final EditText pin_et= (EditText) dialog1.findViewById(R.id.pin_et);
        Button ok= (Button) dialog1.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(pin_et.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Please Enter your Pin",Toast.LENGTH_SHORT).show();
                }
                else {
                    String pin_no=pin_et.getText().toString();
                    SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                    if(pin_no.equals(sp.getString("account_user_pin_no",""))){
                        showConfirmDialog();
                    }
                    else{
                        Toast.makeText(TransferApiActivity.this,"wrong pin",Toast.LENGTH_SHORT).show();
                    }
                    dialog1.dismiss();

                }
            }
        });
    }

    public void showConfirmDialog(){
        final Dialog dialog1 = new Dialog(TransferApiActivity.this,android.R.style.Theme_Material_Dialog);
        dialog1.setContentView(R.layout.confirm_dialog);
        dialog1.setTitle("Do you want to pay?");
        dialog1.show();
        dialog1.setCancelable(false);

        Button yes= (Button) dialog1.findViewById(R.id.yes);
        Button no= (Button) dialog1.findViewById(R.id.no);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                Calendar now = Calendar.getInstance();
                date_string = now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DAY_OF_MONTH);
                time_string = now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND);
                date_tv.setText(date_string);
                time_tv.setText(time_string);

                getTransactionID("http://fiyoorepay.net/findtransid.php");
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
            }
        });
    }

    public void getTransactionID(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("sunil","trans_api_response:-"+response);
                            JSONObject jsonObject=new JSONObject(response);
                            String success=jsonObject.optString("success");
                            if(success.equals("true")){
                                JSONObject jsonObject1=jsonObject.optJSONObject("result");
//                                trans_id=jsonObject1.optString("transaction_id");
                                String id=jsonObject1.optString("transaction_id");
                                Log.d("sunil","tran_id:-"+id);
                                callServiceApi("http://fiyoorepay.net/transeramount.php",id);
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

                params.put("balance_datetime", date+" "+time);
                params.put("balance_id", "");
                params.put("account_user_id", sp.getString("account_user_id",""));

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog= new ProgressDialog(TransferApiActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
}
