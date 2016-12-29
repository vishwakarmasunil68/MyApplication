package com.example.emobi5.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by emobi5 on 6/23/2016.
 */


    public class LocationAdapter extends ArrayAdapter {

        Context context;
        SharedPreferences sp;
        ArrayList<String> stringArrayList= new ArrayList<String>();;
        int vgreso;
//    String bitmapgallery=sp.getString("accepted", null);
//    stringArrayList = new ArrayList<String>();
//    stringArrayList.add(bitmapgallery);
//    Log.e("selected", stringArrayList.toString());


        public LocationAdapter(Context context, int resource) {

            super(context, resource);
            this.context=context;
            this.vgreso=resource;
        }

        @Override
        public int getCount() {

            return StmtActivity.contactDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return  StmtActivity.contactDetails.get(position);

        }


        @Override
        public View getView(final int position, final View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View itemView = inflater.inflate(vgreso, parent, false);
//        final ProductBooen p = rgetProduct(position);
            TextView textcontac = (TextView) itemView.findViewById(R.id.textcontact);
            TextView textnumber = (TextView) itemView.findViewById(R.id.textnumber);
            TextView textamount = (TextView) itemView.findViewById(R.id.textamount);
            TextView textamounttype = (TextView) itemView.findViewById(R.id.textamounttype);
            TextView textservicefees= (TextView) itemView.findViewById(R.id.textservicefees);

            String trans_type=StmtActivity.contactDetails.get(position).getDataAdd().toString();
            Log.d("suntype",position+":-"+trans_type);
            Log.d("suntype",position+":-"+StmtActivity.contactDetails.get(position).getAccount_user_id_sender());
            Log.d("suntype",position+":-"+StmtActivity.contactDetails.get(position).getAccount_user_id());

            if((trans_type.equals("TRANSFER")||trans_type.equals("WITHDRAWAL"))
                    &&(StmtActivity.contactDetails.get(position).getAccount_user_id_sender().equals(StmtActivity.contactDetails.get(position).getAccount_user_id()))){
                textcontac.setText(StmtActivity.contactDetails.get(position).getRefer().toString());
//                textcontac.setText("From  "+StmtActivity.contactDetails.get(position).getRefer().toString());
                Log.d("msg","refer:-"+StmtActivity.contactDetails.get(position).getRefer().toString());
                final String number=StmtActivity.contactDetails.get(position).getNumber().toString();
                textnumber.setText(StmtActivity.contactDetails.get(position).getNumber().toString());
                textamount.setText("-"+StmtActivity.contactDetails.get(position).getAppname().toString());
                textamounttype.setText(StmtActivity.contactDetails.get(position).getDataAdd().toString());
                textservicefees.setText("-"+StmtActivity.contactDetails.get(position).getService_fees());
            }
            else{
                textcontac.setText(StmtActivity.contactDetails.get(position).getRefer().toString());
                Log.d("msg","refer:-"+StmtActivity.contactDetails.get(position).getRefer().toString());
                final String number=StmtActivity.contactDetails.get(position).getNumber().toString();
                textnumber.setText(StmtActivity.contactDetails.get(position).getNumber().toString());
                textamount.setText(StmtActivity.contactDetails.get(position).getAppname().toString());
                textamounttype.setText(StmtActivity.contactDetails.get(position).getDataAdd().toString());
                textservicefees.setText(StmtActivity.contactDetails.get(position).getService_fees());
            }


//            textstrimage.setImageBitmap(StmtActivity.contactDetails.get(position).getImage());
            /*cbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    cbox.setChecked(true);
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    StmtActivity.contactDetails.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    if (isChecked) {


                        String positionstring = StmtActivity.contactDetails.get(position).getNumber().toString();
                        String namestring = StmtActivity.contactDetails.get(position).getName().toString();
//                    stringArrayList = new ArrayList<String>();
                        stringArrayList.add(positionstring);
                        Log.e("selected", stringArrayList.toString());
                        Toast.makeText(context,
                                stringArrayList.toString(), Toast.LENGTH_LONG).show();
//                    SharedPreferences settings = getSharedPreferences("locked", Context.MODE_PRIVATE);
//                    final SharedPreferences.Editor editor = settings.edit();
//                    boolean isAccepted = settings.getBoolean("locked", false);
                        sp = context.getSharedPreferences("pref", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("stringaccepted", stringArrayList.toString());
                        editor.commit();
*//*Intent msglocationintent = new Intent(context, LocationActivity.class);
                msglocationintent.putExtra("phone_no", number);
                msglocationintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(msglocationintent);*//*
                *//*for (int i=0;i<ContactPages.contactDetails.size();i++){
               InfoApps selectedcb=ContactPages.contactDetails.get(i);
                    if(selectedcb.isSelected()){
                        ArrayList<String> selectedcontacts=selectedcb.setSelected(ContactPages.contactDetails.get(i));
                    }
                }

                Toast.makeText(context,
//                        responseText, Toast.LENGTH_LONG).show();*//*


//                    Toast.makeText(context, bitmapgallery, Toast.LENGTH_LONG).show();
                   *//* if (sp.equals("Login")) {
                        Toast.makeText(context, bitmapgallery, Toast.LENGTH_LONG).show();
                    }*//*

                    }
                }
            });
            cbox.setTag(position);*/

//        }

//                for (int j=0;j<ContactPages.contactDetails.size();j++){
                /*cbox.setTag(position);
                cbox.setChecked(p.box);*/


                    /*for (int i=0;stringArrayList.size();i++){

                    }
                    String selcetedcb=ContactPages.contactDetails.get(i).toString();*/


//                }
                /*Intent msglocationintent = new Intent(context, LocationActivity.class);
                msglocationintent.putExtra("phone_no", number);
                msglocationintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                *//*for (int i=0;i<ContactPages.contactDetails.size();i++){
               InfoApps selectedcb=ContactPages.contactDetails.get(i);
                    if(selectedcb.isSelected()){
                        ArrayList<String> selectedcontacts=selectedcb.setSelected(ContactPages.contactDetails.get(i));
                    }
                }

                Toast.makeText(context,
//                        responseText, Toast.LENGTH_LONG).show();

            }*//*
                context.startActivity(msglocationintent);*/

//        imgviw.setImageBitmap(ContactPages.contactDetails.get(position).getAppbmp());
            return itemView;
        }

     /*ProductBooen getProduct(int position) {
         return ((ProductBooen) getItem(position));
    }
    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getProduct((Integer) buttonView.getTag()).box = isChecked;
        }
    };*/
    }

