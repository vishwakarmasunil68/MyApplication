package com.example.emobi5.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by emobi5 on 5/4/2016.
 */
public class StmtActivity extends AppCompatActivity{
    Button okay;
    TextView content,totalblnccheck,textpin,textservice;
    RelativeLayout rlayout;
    LinearLayout linear;
    EditText fname, email, login, pass;
    public static ListView contactList;
    String Name, Email, Login, Pass;
    public static String blnc_id,trnsdtime,trnsamount,trnsamounttype;
    JSONArray jsonArray;
    private static final String REGISTER_URL = "http://oldmaker.com/fiyoore/ecs/hooks/getBalanceInfoHook.php";

//    public static final String KEY_USERNAME = "method";
//    public static final String KEY_USER = "account_user_id";
    public static final String KEY_PASSWORD = "account_user_pin_no";
    public static ArrayList<InfoApps> contactDetails;
    static  String savedph_no;
    public static ArrayList<String> mArrayList1 = new ArrayList<String>();
    LocationAdapter contactAdapter;
    InfoApps Detailapp;
    String user_name;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stmnt);
        contactDetails=new ArrayList<InfoApps>();
        contactList=(ListView)findViewById(R.id.contactlist);
        pass = (EditText) findViewById(R.id.matchpin);
        okay=(Button)findViewById(R.id.btn_ok);
        content=(TextView)findViewById(R.id.textView);
        textpin=(TextView)findViewById(R.id.textpin);
        totalblnccheck=(TextView)findViewById(R.id.totalblnccheck);
        rlayout=(RelativeLayout)findViewById(R.id.rlayout);
        linear=(LinearLayout)findViewById(R.id.linear);
        textservice= (TextView) findViewById(R.id.textservice);
        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v == okay) {

                        registerUser();

                }

            }
        });
        SharedPreferences sp=getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
        user_name=sp.getString("account_user_name","");
    }
    private void registerUser(){
        final String pinpassword = pass.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://fiyoorepay.net/userstament.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("sunil",response);
                        ParseJson(response);

                        if(progressDialog!=null){
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(StmtActivity.this,error.toString(),Toast.LENGTH_LONG).show();
                        Log.w("Postdat", "" + error);
                        if(progressDialog!=null){
                            progressDialog.dismiss();
                        }
                    }
                }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(KEY_PASSWORD,pinpassword);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        progressDialog= new ProgressDialog(StmtActivity.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);
    }

    public void ParseJson(String result){
        try {
            contactDetails.clear();
            JSONObject jsonObject = new JSONObject(result);

            String success=jsonObject.optString("success");
            if(success.equals("true")) {
                Log.d("sunil", jsonObject.toString());
                String success1 = jsonObject.optString("success");
                totalblnccheck.setText(jsonObject.optString("account_user_wallet"));
                if (success1.equals("true")) {
                    JSONArray jsonArray = jsonObject.optJSONArray("result");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Log.d("sunil", jsonObject1.toString());
                        String balance_id = jsonObject1.optString("balance_id");
                        Log.d("sunil", "balance_id" + balance_id);
                        String transaction_id = jsonObject1.optString("transaction_id");
                        Log.d("sunil", "transaction_id" + transaction_id);
                        String balance_perticular = jsonObject1.optString("balance_perticular");
                        Log.d("sunil", "balance_perticular" + balance_perticular);
                        String account_user_id_sender = jsonObject1.optString("account_user_id_sender");
                        Log.d("sunil", "account_user_id_sender" + account_user_id_sender);
                        String account_user_id_receiver = jsonObject1.optString("account_user_id_receiver");
                        Log.d("sunil", "account_user_id_receiver" + account_user_id_receiver);
                        String balance_left_amount = jsonObject1.optString("balance_left_amount");
                        Log.d("sunil", "balance_left_amount" + balance_left_amount);
                        String balance_amount = jsonObject1.optString("balance_amount");
                        Log.d("sunil", "balance_amount" + balance_amount);
                        String balance_status = jsonObject1.optString("balance_status");
                        Log.d("sunil", "balance_status" + balance_status);
                        String balance_datetime = jsonObject1.optString("balance_datetime");
                        Log.d("sunil", "balance_datetime" + balance_datetime);
                        String account_user_id = jsonObject1.optString("account_user_id");
                        Log.d("sunil", "account_user_id" + account_user_id);
                        String balance_passed_by = jsonObject1.optString("balance_passed_by");
                        Log.d("sunil", "balance_passed_by" + balance_passed_by);
                        String balance_ref = jsonObject1.optString("balance_ref");
                        Log.d("sunil", "balance_ref" + balance_ref);
                        String account_user_name = jsonObject1.optString("account_user_name");
                        Log.d("sunil", "account_user_name" + account_user_name);
                        String account_user_email = jsonObject1.optString("account_user_email");
                        Log.d("sunil", "account_user_email" + account_user_email);
                        String account_user_password = jsonObject1.optString("account_user_password");
                        Log.d("sunil", "account_user_password" + account_user_password);
                        String account_user_cell = jsonObject1.optString("account_user_cell");
                        Log.d("sunil", "account_user_cell" + account_user_cell);
                        String account_user_tell = jsonObject1.optString("account_user_tell");
                        Log.d("sunil", "account_user_tell" + account_user_tell);
                        String account_user_address = jsonObject1.optString("account_user_address");
                        Log.d("sunil", "account_user_address" + account_user_address);
                        String account_user_wallet = jsonObject1.optString("account_user_wallet");
                        Log.d("sunil", "account_user_wallet" + account_user_wallet);
                        String account_user_status = jsonObject1.optString("account_user_status");
                        Log.d("sunil", "account_user_status" + account_user_status);
                        String account_user_passport = jsonObject1.optString("account_user_passport");
                        Log.d("sunil", "account_user_passport" + account_user_passport);
                        String account_user_expire_date = jsonObject1.optString("account_user_expire_date");
                        Log.d("sunil", "account_user_expire_date" + account_user_expire_date);
                        String account_user_credit_limit = jsonObject1.optString("account_user_credit_limit");
                        Log.d("sunil", "account_user_credit_limit" + account_user_credit_limit);
                        String account_user_terms_days = jsonObject1.optString("account_user_terms_days");
                        Log.d("sunil", "account_user_terms_days" + account_user_terms_days);
                        String account_user_pin_no = jsonObject1.optString("account_user_pin_no");
                        Log.d("sunil", "account_user_pin_no" + account_user_pin_no);
                        String account_user_num = jsonObject1.optString("account_user_num");
                        Log.d("sunil", "account_user_num" + account_user_num);
                        String account_extra_email = jsonObject1.optString("account_extra_email");
                        Log.d("sunil", "account_extra_email" + account_extra_email);
                        String account_extra_details = jsonObject1.optString("account_extra_details");
                        Log.d("sunil", "account_extra_details" + account_extra_details);
                        String account_service_charge = jsonObject1.optString("account_service_charge");
                        Log.d("sunil", "account_service_charge" + account_service_charge);
                        String trans_type = jsonObject1.optString("trans_type");
                        String service_fees=jsonObject1.optString("service_fees");
                        Detailapp = new InfoApps();
                        Detailapp.setName(user_name);
                        Detailapp.setNumber(balance_datetime);
                        Detailapp.setAppname(balance_amount);
                        Detailapp.setDataAdd(trans_type);
                        Detailapp.setRefer(balance_ref);
                        Detailapp.setTrans_type(transaction_id);
                        Detailapp.setAccount_user_id(account_user_id);
                        Detailapp.setAccount_user_id_sender(account_user_id_sender);
                        Detailapp.setService_fees(service_fees);

                        contactDetails.add(Detailapp);
                    }
                    pass.setVisibility(View.GONE);
                    okay.setVisibility(View.GONE);
                    content.setVisibility(View.GONE);
                    textpin.setVisibility(View.GONE);
                    contactList.setVisibility(View.VISIBLE);
//                    SharedPreferences sp = getSharedPreferences("Fiyoore.txt", Context.MODE_PRIVATE);
//                    totalblnccheck.setText(sp.getString("account_user_wallet", "0.00"));
                    rlayout.setVisibility(View.VISIBLE);
                    linear.setVisibility(View.VISIBLE);
                    contactAdapter = new LocationAdapter(getApplicationContext(), R.layout.contactlistadap);
                    contactList.setAdapter(contactAdapter);
                } else {
                    if (success.equals("false")) {
                        Toast.makeText(StmtActivity.this, "Invalid Pin", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        catch (Exception e){
            Log.d("sunil",e.toString());
        }
    }
}
