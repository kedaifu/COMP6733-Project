package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class LocationDialog extends AppCompatDialogFragment{

    private EditText label;
    private EditText orientation;
    private EditText timeline;
    private LocationDialogListener listener;
    //data
    private int labelOut;
    private int orientationOut;
    private int timelineOut;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog,null);

        builder.setView(view)
                .setTitle("location")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //setLabelOut(Integer.parseInt(label.getText().toString()));
                        labelOut = Integer.parseInt(label.getText().toString());
                        orientationOut = Integer.parseInt(orientation.getText().toString());
                        timelineOut = Integer.parseInt(timeline.getText().toString());
                        listener.applyTexts(labelOut,orientationOut,timelineOut);
                    }
                });

        label = view.findViewById(R.id.location_label);
        orientation = view.findViewById(R.id.phone_orientation);
        timeline = view.findViewById(R.id.timeline);


        return builder.create();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (LocationDialogListener)context;
        } catch (ClassCastException e) {
            //e.printStackTrace();
            throw  new ClassCastException(context.toString() + "implement listener");
        }
    }

    public interface LocationDialogListener{
        void applyTexts(int l,int o, int t );
    }

}
