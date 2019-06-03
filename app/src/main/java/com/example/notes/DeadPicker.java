package com.example.notes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class DeadPicker extends DialogFragment {

    private long time;
    private DateListener listener;


    private CalendarView calendarView;
    SeekBar hoursCh;
    SeekBar minCh;

    TextView hours;
    TextView min;

    Calendar calendar;

    public interface DateListener {
        public void getDate(long time);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        listener = (DateListener) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        time = getArguments().getLong("time");


        View v = inflater.inflate(R.layout.dead_line_picker, container, false);

        calendarView = v.findViewById(R.id.pick_date);
        calendarView.setDate(time);

        hoursCh = v.findViewById(R.id.hours_ch);
        minCh = v.findViewById(R.id.min_ch);
        hours = v.findViewById(R.id.hours);
        min = v.findViewById(R.id.min);
        calendar = new GregorianCalendar();
        calendar.setTimeInMillis(time);
        String initHours = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        String initMin = String.valueOf(calendar.get(Calendar.MINUTE));

        hoursCh.setProgress(calendar.get(Calendar.HOUR_OF_DAY));
        minCh.setProgress(calendar.get(Calendar.MINUTE));

        hours.setText(initHours.length() > 1 ? initHours : "0" + initHours);
        min.setText(initMin.length() > 1 ? initMin : "0" + initMin);

        hoursCh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String hour = String.valueOf(progress);
                hours.setText(hour.length() > 1 ? hour : "0" + hour);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        minCh.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                String hour = String.valueOf(progress);
                min.setText(hour.length() > 1 ? hour : "0" + hour);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        calendarView.setOnDateChangeListener((CalendarView calendarView, int i, int i1, int i2) -> {
            Log.d("NOTES", String.valueOf(i1));
            calendar.set(i, i1, i2);
        });

        v.findViewById(R.id.ok_btn).setOnClickListener(b -> {
            Log.d("NOTES", new Date(calendarView.getDate()).toString());
            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours.getText().toString()));
            calendar.set(Calendar.MINUTE, Integer.parseInt(min.getText().toString()));
            time = calendar.getTimeInMillis();


            listener.getDate(time);

            getDialog().dismiss();
        });

        v.findViewById(R.id.no_btn).setOnClickListener(b -> {


            getDialog().cancel();
        });


        return v;
    }
}
