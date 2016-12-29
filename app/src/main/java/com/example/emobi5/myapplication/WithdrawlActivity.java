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
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class WithdrawlActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener{
    Button submit;
    EditText amount_et;
    TextView service_tv,date_tv,time_tv;
    String amount_string,service_String,date_string,time_string,amount_left_string;
    double left_waller,user_account_amount;
    private ProgressDialog progressDialog;
    EditText reference_et;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawl);
        submit= (Button) findViewById(R.id.withdrawl);
        date_tv= (TextView) findViewById(R.id.date_et);
        amount_et= (EditText) findViewById(R.id.amount_et);
        time_tv= (TextView) findViewById(R.id.time_et);
        service_tv= (TextView) findViewById(R.id.service_tv);
        reference_et= (EditText) findViewById(R.id.reference_et);
//        date_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar now = Calendar.getInstance();
//                DatePickerDialog dpd = DatePickerDialog.newInstance(
//                        WithdrawlActivity.this,
//                        now.get(Calendar.YEAR),
//                        now.get(Calendar.MONTH),
//                        now.get(Calendar.DAY_OF_MONTH)
//                );
//                dpd.showYearPickerFirst(true);
//                dpd.setTitle("Choose Date");
//                dpd.show(WithdrawlActivity.this.getFragmentManager(), "Datepickerdialog");
//            }
//        });
//        time_tv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar now = Calendar.getInstance();
//                TimePickerDialog tpd=TimePickerDialog.newInstance(WithdrawlActivity.this, now.get(Calendar.HOUR_OF_DAY),
//                        now.get(Calendar.MINUTE),
//                        false);
//                tpd.setTitle("Choose Time");
//                tpd.show(getFragmentManager(), "Timepickerdialog");
//            }
//        });
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Withdrawl");

        SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
        try{
            user_account_amount=Double.parseDouble(sp.getString("account_user_wallet","0.00"));
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
            user_account_amount=0.00;
        }
        Log.d("sunil",user_account_amount+"");

        Calendar now = Calendar.getInstance();
        date_tv.setText( now.get(Calendar.YEAR)+"-"+ now.get(Calendar.MONTH)+"-"+ now.get(Calendar.DAY_OF_MONTH));
        time_tv.setText( now.get(Calendar.HOUR_OF_DAY)+":"+ now.get(Calendar.MINUTE)+":"+ now.get(Calendar.SECOND));

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!amount_et.getText().toString().equals("")) {
                    double amount;
                    try{
                        amount=Double.parseDouble(amount_et.getText().toString());
                    }
                    catch (Exception e){
                        amount=0.00;
                    }
                    if(amount>user_account_amount){
                        Toast.makeText(WithdrawlActivity.this,"Enter amount must be less than wallet account",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Calendar now = Calendar.getInstance();
                        date_tv.setText(now.get(Calendar.YEAR) + "-" + now.get(Calendar.MONTH) + "-" + now.get(Calendar.DAY_OF_MONTH));
                        time_tv.setText(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND));

                        setWithdrawlAPI("http://oldmaker.com/ecs/dowithdraw.php");
                    }
                }
                else{
                    Toast.makeText(WithdrawlActivity.this,"Enter amount First",Toast.LENGTH_SHORT).show();
                }
            }
        });
        amount_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("sunil","ontextchanged");
                String amount=amount_et.getText().toString();
                try{
                    int amt=Integer.parseInt(amount);
                    SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                    String servicecharge=sp.getString("account_service_charge","");
                    Log.d("sunil",sp.getString("account_service_charge",""));
                    if(!servicecharge.equals("")){
                        double service_fees=((Double.parseDouble(servicecharge))*amt)/100;
                        service_tv.setText(service_fees+"");
                        Log.d("sunil",service_fees+"");
                    }

                }
                catch (Exception e){
                    Log.d("sunil",e.toString());
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void setWithdrawlAPI(String url){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("sunil",response);

                            JSONObject obj=new JSONObject(response);
                            String success=obj.optString("success");
                            if(success.equals("true")){
                                JSONObject obj1=obj.optJSONObject("result");


//                                amount_left_string=obj.optString("account_user_wallet");
                                amount_left_string=left_waller+"";
                                service_String=service_tv.getText().toString();
                                amount_string=amount_et.getText().toString();

                                SharedPreferences sp=getSharedPreferences("Fiyoore.txt",Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor=sp.edit();
                                editor.putString("account_user_wallet",amount_left_string);
                                editor.commit();
                                showDialog();
                            }
                            else{
                                Toast.makeText(WithdrawlActivity.this,"Invalid Pin",Toast.LENGTH_LONG).show();
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
                SharedPreferences sp=getSharedPreferences("Fiyoore.txt",Context.MODE_PRIVATE);
                Map<String,String> params = new HashMap<String, String>();
                params.put("transaction_id", "");
                params.put("balance_perticular", "");
                params.put("account_user_id_sender", sp.getString("account_user_id",""));
                params.put("account_user_id_receiver", "null");

                String waller=sp.getString("account_user_wallet","");
                Log.d("sunil","wallet:-"+waller);
                double waller_value=Double.parseDouble(waller);

                left_waller=waller_value-Double.parseDouble(amount_et.getText().toString())-Double.parseDouble(service_tv.getText().toString());
                DecimalFormat df = new DecimalFormat("#.00");
                String format=df.format(left_waller);

                params.put("balance_left_amount", format);

                params.put("balance_amount", amount_et.getText().toString());
                params.put("balance_status", "show");
                date_string=date_tv.getText().toString();
                time_string=time_tv.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String formattedDate = df1.format(c.getTime());
                params.put("balance_datetime",formattedDate);
                params.put("account_user_id", sp.getString("account_user_id",""));
                params.put("balance_passed_by", "");
                params.put("balance_ref", reference_et.getText().toString());
                params.put("account_user_wallet", format);
                params.put("balance_id", "");

                return params;
            }

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog= new ProgressDialog(WithdrawlActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }
    public void showDialog(){
        final Dialog dialog1 = new Dialog(WithdrawlActivity.this,android.R.style.Theme_Material_Dialog);
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
                Intent intent=new Intent(WithdrawlActivity.this,HomeActivity.class);
                startActivity(intent);
                finishAffinity();
            }
        });
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date=year+"/"+(++monthOfYear)+"/"+dayOfMonth;
        date_tv.setText(date);
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        String time=hourOfDay+":"+minute;
        time_tv.setText(time+":30");
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
