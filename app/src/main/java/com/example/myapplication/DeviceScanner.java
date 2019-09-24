package com.example.myapplication;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

public class DeviceScanner{

    private final int REQUEST_ENABLE_BT = 1; // int greater than 0

    private MainActivity mMA;

    private BluetoothAdapter bluetoothAdapter;
    private  boolean mScanning;
    private Handler handler;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;

    private int signalStrength;

    private int test = 0;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    //how long scanner, signal threshold, don't pick the random
    public DeviceScanner(MainActivity mainActivity, int signalStrength){
        mMA = mainActivity;
        handler = new Handler();
        this.signalStrength = signalStrength;



        final BluetoothManager bluetoothManager =
                (BluetoothManager) mMA.getSystemService(Context.BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();


        // Ensures Bluetooth is available on the device and it is enabled. If not,
        // displays a dialog requesting user permission to enable Bluetooth.
        if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            mMA.startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void scanLeDevice(final boolean enable) {

        if (enable) {
            // Stops scanning after a pre-defined scan period.
            handler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    mScanning = false;
                    bluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            bluetoothAdapter.startLeScan(mLeScanCallback);

        } else {
            mScanning = false;
            bluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void start() {

        scanLeDevice(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void stop() {
        scanLeDevice(false);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    // Device scan callback.
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    final int new_rssi = rssi;
                    if (rssi > signalStrength) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                mMA.addDevice(device, new_rssi);
                            }
                        });
                    }


                }
            };



}
