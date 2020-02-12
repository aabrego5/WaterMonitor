package com.example.watermonitor;

import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<ValveStatus> valves;

    // Provide a reference to the views for each data item
    public class MyViewHolder extends RecyclerView.ViewHolder {
        // member variable for every view in a row
        public TextView valveName;
        public SeekBar valvePcntBar;

        public MyViewHolder(View itemView) {
            super(itemView);

            valveName = (TextView) itemView.findViewById(R.id.valve_name);
            valvePcntBar = (SeekBar) itemView.findViewById(R.id.valve_slider);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyAdapter(List<ValveStatus> valves) {
        this.valves = valves;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.valve_slider, parent, false);

        // Return a new holder instance
        MyViewHolder viewHolder = new MyViewHolder(contactView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // get element from valve list at this position
        ValveStatus valve = valves.get(position);

        // set the contents of the view with that element
        TextView textView = holder.valveName;
        textView.setText(valve.GetApplianceName());
        SeekBar seekBar = holder.valvePcntBar;
        seekBar.setProgress(valve.GetValvePercentage());
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return valves.size();
    }
}