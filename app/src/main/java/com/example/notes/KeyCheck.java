package com.example.notes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static android.content.Context.MODE_PRIVATE;

public class KeyCheck implements KeyStore {
    private SharedPreferences myNoteSharedPref;
    private final String PIN_CODE = "PIN_CODE";
    private static final String PIN = "myappnamescores";


    public KeyCheck(Context context) {
        Log.d("NOTES", String.valueOf(myNoteSharedPref == null));
        myNoteSharedPref = context.getSharedPreferences(PIN, Activity.MODE_PRIVATE);
    }

    public boolean hasPin() {
        Log.d("n", String.valueOf(myNoteSharedPref == null));
       // return myNoteSharedPref == null;
        return myNoteSharedPref.contains(PIN_CODE);
    }

    public boolean checkPin(String pin) {
        Log.d("NOTES", myNoteSharedPref.getString(PIN_CODE, ""));
        return pin.equals(myNoteSharedPref.getString(PIN_CODE, ""));

    }

    public void saveNew(String pin) {
        SharedPreferences.Editor myEditor = myNoteSharedPref.edit();
        myEditor.putString(PIN_CODE, pin);
        myEditor.apply();
    }
}
