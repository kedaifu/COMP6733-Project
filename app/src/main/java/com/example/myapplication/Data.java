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

    private int labelOut;
    private int orientationOut;
    private int timelineOut;
    private String MAC;
    private int RSSI;

    public Data(){
        this.labelOut = -1;
        this.orientationOut = -1;
        this.timelineOut = -1;
        this.MAC = "";
        this.RSSI = 1;
    }

    public Data(int labelOut, int orientationOut, int timelineOut, String MAC, int RSSI){
        this.labelOut = labelOut;
        this.orientationOut = orientationOut;
        this.timelineOut = timelineOut;
        this.MAC = MAC;
        //RSSI = new ArrayList<Integer> () ;
        this.RSSI = RSSI;
    }

    public int getLabelOut() {
        return labelOut;
    }

    public void setLabelOut(int labelOut) {
        this.labelOut = labelOut;
    }

    public int getOrientationOut() {
        return orientationOut;
    }

    public void setOrientationOut(int orientationOut) {
        this.orientationOut = orientationOut;
    }

    public int getTimelineOut() {
        return timelineOut;
    }

    public void setTimelineOut(int timelineOut) {
        this.timelineOut = timelineOut;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public int getRSSI() {
        return RSSI;
    }

    public void setRSSI(int RSSI) {
        this.RSSI = RSSI;
    }


    public String toString(){
        return "[label = " + labelOut
                + ", orientation = " + orientationOut
                + ", timeline = " + timelineOut
                + ", MAC = " + MAC
                + ", RSSI = " + RSSI +"]";
    }
//    public void addRSSI(int rssi){
//        RSSI.add(rssi);
//    }

    //average
//    public float AvgRSSI(){
//        int sum = 0;
//        int size = RSSI.size();
//        for(Integer tmpI : RSSI){
//            sum += tmpI;
//        }
//
//        return sum/size;
//    }
//
//    //standard deviation
//    public float SDRSSI(){
//        float avg = this.AvgRSSI();
//        int sum = 0;
//        int size = RSSI.size();
//        for(Integer tmpI : RSSI){
//            sum +=  (tmpI-avg)*(tmpI-avg);
//        }
//
//        float variance = sum/size;
//
//        return (float) Math.sqrt(variance);
//    }


}
