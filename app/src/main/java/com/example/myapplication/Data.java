package com.example.myapplication;
/**
 *
 * Data structure stored to SD card
 *
 *  current attribute:
 *      orientation of antenna
 *      location x,y,z
 *      MAC address
 *      RSSI List (take average and standard deviation for further research)
 *
 * */
import java.util.ArrayList;

public class Data {

    private float x;
    private float y;
    private float z;
    private String MAC;
    private float orientation;
    private ArrayList<Integer> RSSI;

    public Data(float x, float y, float z, String MAC, float orientation){
        this.x = x;
        this.y = y;
        this.z = z;
        this.MAC = MAC;
        this.orientation = orientation;
        RSSI = new ArrayList<Integer> () ;
    }


    public void addRSSI(int rssi){
        RSSI.add(rssi);
    }

    //average
    public float AvgRSSI(){
        int sum = 0;
        int size = RSSI.size();
        for(Integer tmpI : RSSI){
            sum += tmpI;
        }

        return sum/size;
    }

    //standard deviation
    public float SDRSSI(){
        float avg = this.AvgRSSI();
        int sum = 0;
        int size = RSSI.size();
        for(Integer tmpI : RSSI){
            sum +=  (tmpI-avg)*(tmpI-avg);
        }

        float variance = sum/size;

        return (float) Math.sqrt(variance);
    }


}
