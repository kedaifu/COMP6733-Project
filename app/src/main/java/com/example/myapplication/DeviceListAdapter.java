package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DeviceListAdapter extends ArrayAdapter<Device> {

    Activity activity;
    private int resourceId;
    ArrayList<Device> devices;

    public DeviceListAdapter(Activity activity, int deviceViewResourceId, ArrayList<Device> objects){
        super(activity.getApplicationContext(),deviceViewResourceId,objects);
        this.activity = activity;
        resourceId = deviceViewResourceId;
        devices = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        Device tmpD = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            LayoutInflater inflater =
                    (LayoutInflater) activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(resourceId, parent, false);
        }
        String tmpName = tmpD.getName();
        String tmpAddress = tmpD .getAddress();
        int tmpRSSI = tmpD.getRSSI();


        TextView tvRSSI = (TextView) convertView.findViewById(R.id.tv_rssi);

        tvRSSI.setText("RSSI: " + Integer.toString(tmpRSSI));
//
        TextView tvAddr = (TextView) convertView.findViewById(R.id.tv_rssi);
        tvAddr = (TextView) convertView.findViewById(R.id.tv_macaddr);
        if (tmpAddress != null && tmpAddress.length() > 0) {
            tvAddr.setText("MAC: "+tmpD.getAddress());
        }
        else {
            tvAddr.setText("MAC: ");
        }

//        Toast.makeText(this.activity, tmpD.getRSSI() + "yoyo", Toast.LENGTH_SHORT).show();
        return convertView;
    }
}
