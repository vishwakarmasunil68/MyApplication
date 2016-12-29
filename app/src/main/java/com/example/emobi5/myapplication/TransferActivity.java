package com.example.emobi5.myapplication;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TransferActivity extends AppCompatActivity {

    EditText pin_et;
    Button ok,proceed;
    public static String reciever_id;
    public static String pin_no,reciever_wallet,user_account_no;
    private ProgressDialog progressDialog;
    LinearLayout account_ll,pin_ll;
    TextView acount_name,account_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer);
        pin_et= (EditText) findViewById(R.id.pin_et);
        pin_ll= (LinearLayout) findViewById(R.id.pin_ll);
        account_ll= (LinearLayout) findViewById(R.id.account_ll);
        acount_name= (TextView) findViewById(R.id.acount_name);
        account_no= (TextView) findViewById(R.id.account_no);
        ok= (Button) findViewById(R.id.ok);
        proceed= (Button) findViewById(R.id.proceed);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pin_et.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Enter User ID First",Toast.LENGTH_LONG).show();
                }
                else {
                    SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
                    if(sp.getString("account_user_email","").equals(pin_et.getText().toString())){
                        Toast.makeText(getApplicationContext(),"you cannot transfer into your own account",Toast.LENGTH_LONG).show();
                    }
                    else{
                        getSenderId("http://fiyoorepay.net/findtransferreciver.php");
                    }
                }
            }
        });
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TransferActivity.this,TransferApiActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getSenderId(String url){
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
                                    reciever_id=obj1.optString("account_user_id");
                                    pin_no=pin_et.getText().toString();
                                    reciever_wallet=obj1.optString("account_user_wallet");
                                    proceed.setVisibility(View.VISIBLE);
                                    ok.setVisibility(View.GONE);
                                    account_ll.setVisibility(View.VISIBLE);
                                    pin_ll.setVisibility(View.GONE);
                                    acount_name.setText(obj1.optString("account_user_name"));
                                    user_account_no=obj1.optString("account_user_num");
                                    account_no.setText(obj1.optString("account_user_num"));
    //                                showDialog();
                                }
                                else{
                                    Toast.makeText(TransferActivity.this,"Invalid User Id",Toast.LENGTH_LONG).show();
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
                    params.put("account_user_email", pin_et.getText().toString());
                    return params;
                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
            progressDialog= new ProgressDialog(TransferActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.show();
            progressDialog.setCancelable(false);
    }


    public void showDialog(){
        final Dialog dialog1 = new Dialog(TransferActivity.this,android.R.style.Theme_Material_Dialog);
        dialog1.setContentView(R.layout.withdraw_dialog);
        dialog1.setTitle("Withdrawl Slip");
        dialog1.show();
        dialog1.setCancelable(false);
        TextView date_et= (TextView) dialog1.findViewById(R.id.date_et);
        TextView time_et= (TextView) dialog1.findViewById(R.id.time_et);
        TextView amount_et= (TextView) dialog1.findViewById(R.id.amount_et);
        TextView amount_et_left= (TextView) dialog1.findViewById(R.id.amount_et_left);
        TextView service_et= (TextView) dialog1.findViewById(R.id.service_et);

//        date_et.setText(date_string);
//        time_et.setText(time_string);
//        amount_et.setText(amount_string);
//        amount_et_left.setText(amount_left_string);
//        service_et.setText(service_String);

        Button ok= (Button) dialog1.findViewById(R.id.ok);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.dismiss();
                finish();
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
