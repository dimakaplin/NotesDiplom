package com.example.notes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class PinActivity extends AppCompatActivity {

    private Intent intent;

    KeyStore keyStore;
    Button btn0;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    Button btn5;
    Button btn6;
    Button btn7;
    Button btn8;
    Button btn9;

    ImageButton clear;
    ImageButton clearAll;

    View circle1;
    View circle2;
    View circle3;
    View circle4;

    private String pin = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);
        init();
    }

    private void init() {
        keyStore = App.getKeystore();

        btn0 = findViewById(R.id.num_0);
        btn1 = findViewById(R.id.num_1);
        btn2 = findViewById(R.id.num_2);
        btn3 = findViewById(R.id.num_3);
        btn4 = findViewById(R.id.num_4);
        btn5 = findViewById(R.id.num_5);
        btn6 = findViewById(R.id.num_6);
        btn7 = findViewById(R.id.num_7);
        btn8 = findViewById(R.id.num_8);
        btn9 = findViewById(R.id.num_9);

        clear = findViewById(R.id.clear);
        clearAll = findViewById(R.id.clear_all);

        circle1  = findViewById(R.id.c1);
        circle2  = findViewById(R.id.c2);
        circle3  = findViewById(R.id.c3);
        circle4  = findViewById(R.id.c4);

        btn0.setOnClickListener(v-> numClick((Button) v));
        btn1.setOnClickListener(v-> numClick((Button) v));
        btn2.setOnClickListener(v-> numClick((Button) v));
        btn3.setOnClickListener(v-> numClick((Button) v));
        btn4.setOnClickListener(v-> numClick((Button) v));
        btn5.setOnClickListener(v-> numClick((Button) v));
        btn6.setOnClickListener(v-> numClick((Button) v));
        btn7.setOnClickListener(v-> numClick((Button) v));
        btn8.setOnClickListener(v-> numClick((Button) v));
        btn9.setOnClickListener(v-> numClick((Button) v));

        clear.setOnClickListener(v-> clear());
        clearAll.setOnClickListener(v-> clearAll());
    }

    private void numClick(Button btn) {
        if(pin.length() == 4 && keyStore.hasPin()) {
            if(keyStore.checkPin(pin)) {
                intent = new Intent(PinActivity.this, NotesList.class);
                startActivity(intent);
            } else {
                String message = getText(R.string.check_pin_failed).toString();
                Toast.makeText(PinActivity.this, message, Toast.LENGTH_LONG).show();
                clearAll();
            }
        } else if (pin.length() == 4 && !keyStore.hasPin()) {
            keyStore.saveNew(pin);
        } else {
            circleColorChange(false, pin.length());
            String num = btn.getText().toString();
            Log.d("NOTES", num);
            pin = pin + num;
        }
    }

    private void clearAll() {
        pin = "";
        circle1.setBackgroundResource(R.drawable.grey_circle);
        circle2.setBackgroundResource(R.drawable.grey_circle);
        circle3.setBackgroundResource(R.drawable.grey_circle);
        circle4.setBackgroundResource(R.drawable.grey_circle);
    }

    private void clear() {
        int pinLength = pin.length();
        pin = pin.substring(0, pin.length()-1);
        circleColorChange(true, pinLength);

    }

    private void circleColorChange(boolean delete, int pinLength) {
        switch(pinLength) {
            case 0:
                if(delete) {
                    circle1.setBackgroundResource(R.drawable.grey_circle);
                } else {
                   circle1.setBackgroundResource(R.drawable.red_circle);
                }
            break;
            case 1:
                if(delete) {
                    circle2.setBackgroundResource(R.drawable.grey_circle);
                } else {
                    circle2.setBackgroundResource(R.drawable.red_circle);
                }
                break;
            case 2:
                if(delete) {
                    circle3.setBackgroundResource(R.drawable.grey_circle);
                } else {
                    circle3.setBackgroundResource(R.drawable.red_circle);
                }
                break;
            case 3:
                if(delete) {
                    circle4.setBackgroundResource(R.drawable.grey_circle);
                } else {
                    circle4.setBackgroundResource(R.drawable.red_circle);
                }
                break;
                default:
                    break;
        }
    }


}
