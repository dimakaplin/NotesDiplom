package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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


    private CalendarView calendar;
    SeekBar hoursCh;
    SeekBar minCh;

    TextView hours;
    TextView min;

    Calendar cal;

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

        calendar = v.findViewById(R.id.pick_date);
        calendar.setDate(time);

        hoursCh = v.findViewById(R.id.hours_ch);
        minCh = v.findViewById(R.id.min_ch);
        hours = v.findViewById(R.id.hours);
        min = v.findViewById(R.id.min);
        cal = new GregorianCalendar();
        cal.setTimeInMillis(time);
        String initHours = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
        String initMin = String.valueOf(cal.get(Calendar.MINUTE));

        hoursCh.setProgress(cal.get(Calendar.HOUR_OF_DAY));
        minCh.setProgress(cal.get(Calendar.MINUTE));

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




        v.findViewById(R.id.ok_btn).setOnClickListener(b-> {
            cal.setTimeInMillis(calendar.getDate());
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hours.getText().toString()));
            cal.set(Calendar.MINUTE, Integer.parseInt(min.getText().toString()));
            time = cal.getTimeInMillis();



            listener.getDate(time);

            getDialog().dismiss();
        });

        v.findViewById(R.id.no_btn).setOnClickListener(b-> {


            getDialog().cancel();
        });





        return v;
    }
}
