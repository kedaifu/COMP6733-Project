package com.example.myapplication;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    private ArrayList<Device> devices;
    private HashMap<String, Device> mMap;
    private DeviceListAdapter deviceListViewAdapter;

    private Button searchBtn;

    private DeviceScanner mScanner;

    private int position;

    private Date currentTime;

    private ArrayList<Data> output; // TODO

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // label for position
        position = 0;


        //check to determine whether BLE is supported on the device.
        BLEChecking();

        //initialize listview adapter and add key listeners for each element
        initListViewAdapter();

        //initialize button and add click events
        initBtn();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        Log.d("TAG", "onDestroy");
    }
    @Override
    protected void onPause() {

        super.onPause();
        Log.d("TAG", "onPause");
    }
    @Override
    protected void onRestart() {

        super.onRestart();
        Log.d("TAG", "onRestart");
    }
    @Override
    protected void onResume() {

        super.onResume();
        Log.d("TAG", "onResume");
    }
    @Override
    protected void onStart() {

        super.onStart();
        Log.d("TAG", "onStart");

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onStop() {

        super.onStop();
        stopScan();
        Log.d("TAG", "onStop");
    }


    public void BLEChecking(){
        // Use this check to determine whether BLE is supported on the device. Then
        // you can selectively disable BLE-related features.
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported ... ", Toast.LENGTH_SHORT).show();
            finish();
        }else{
            Toast.makeText(this, "BLE is supported!", Toast.LENGTH_SHORT).show();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void initListViewAdapter(){
        mScanner = new DeviceScanner(this, -75);
        devices = new ArrayList<>();
        mMap = new HashMap<>();
        deviceListViewAdapter = new DeviceListAdapter(this, R.layout.item,devices);

        listView =(ListView) findViewById(R.id.ble_list);
        listView.setAdapter(deviceListViewAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TODO: add actions
            }
        });
    }

    public void initBtn(){
        searchBtn = (Button) findViewById(R.id.search_btn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void onClick(View v) {
                position += 1;
                devices.clear();
                mMap.clear();
                mScanner.start();
                listView.setAdapter(deviceListViewAdapter);
            }
        });
    }


    public void addDevice(BluetoothDevice device, int new_rssi) {
        String address = device.getAddress();

        if (!mMap.containsKey(address)) {
            Device btleDevice = new Device(device);
            btleDevice.setRSSI(new_rssi);

            mMap.put(address, btleDevice);
            devices.add(btleDevice);
        }
        else {
            mMap.get(address).setRSSI(new_rssi);
        }

        deviceListViewAdapter.notifyDataSetChanged();
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    //stop scanning
    public void stopScan(){
        mScanner.stop();
    }

    // https://developer.android.com/reference/android/support/v4/content/FileProvider
    public void export(View view){


        //generate data
        StringBuilder data = new StringBuilder();
        data.append("Time,Distance");
        for(int i = 0; i<5; i++){
            data.append("\n"+String.valueOf(i)+","+String.valueOf(i*i));
        }

        try{
            //saving the file into device
            FileOutputStream out = openFileOutput("data.csv", Context.MODE_PRIVATE);
            out.write((data.toString()).getBytes());
            System.out.println(data);
            out.close();

            //add exporting permission
            Context context = getApplicationContext();
            File filelocation = new File(getFilesDir(), "data.csv");
            Uri path = FileProvider.getUriForFile(context, "com.example.myapplication.fileprovider", filelocation);
            Intent fileIntent = new Intent(Intent.ACTION_SEND);
            fileIntent.setType("text/csv");
            fileIntent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            fileIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            fileIntent.putExtra(Intent.EXTRA_STREAM, path);
            startActivity(Intent.createChooser(fileIntent, "Send csv via email"));
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }
}
