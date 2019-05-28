package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class KeyCheck implements KeyStore {
    private SharedPreferences myNoteSharedPref;
    private final String PIN_CODE = "PIN_CODE";


    public KeyCheck(Context context) {
        myNoteSharedPref = context.getSharedPreferences("MyNote", MODE_PRIVATE);
    }

    public boolean hasPin() {
        return myNoteSharedPref.contains(PIN_CODE);
    }

    public boolean checkPin(String pin) {
        return pin.equals(myNoteSharedPref.getString(PIN_CODE, ""));

    }

    public void saveNew(String pin) {
        SharedPreferences.Editor myEditor = myNoteSharedPref.edit();
        myEditor.putString(PIN_CODE, pin);
        myEditor.apply();
    }
}
