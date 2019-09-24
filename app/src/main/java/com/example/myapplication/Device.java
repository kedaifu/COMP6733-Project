package com.example.myapplication;
/*
 * A Single BLE device
 *
 *
 * */
import android.bluetooth.BluetoothDevice;

public class Device {
    private BluetoothDevice BTD;
    private int RSSI;

    public Device(BluetoothDevice BTD){
        this.BTD = BTD;
    }

    public  String getAddress(){
        return  BTD.getAddress();
    }

    public  String getName(){
        return  BTD.getName();
    }

    public int getRSSI(){
        return this.RSSI;
    }
    public  void setRSSI(int RSSI){
        this.RSSI = RSSI;
    }
}
