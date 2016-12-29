package com.example.emobi5.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

/**
 * Created by emobi5 on 5/5/2016.
 */
public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] web;
       public CustomGrid(Context context, String[] web) {
           mContext=context;
           this.web=web;
    }

    @Override
    public int getCount() {
        return web.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

//            grid = new View(mContext);
            convertView = inflater.inflate(R.layout.grid_single, null);
            Button btn = (Button) convertView.findViewById(R.id.btn_bal);
//            ImageView imageView = (ImageView)convertView.findViewById(R.id.grid_image);
            btn.setText(web[position]);
//            imageView.setImageResource(Imageid[position]);
        }
//        else {
//            grid = (View) convertView;
//        }

        return convertView ;

    }
}
