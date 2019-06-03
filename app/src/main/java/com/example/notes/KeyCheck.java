package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class KeyCheck implements KeyStore {
    private SharedPreferences myNoteSharedPref;
    private final String PIN_CODE = "PIN_CODE";
    private static final String PIN = "PIN_APP";

    private String masterKeyAlias;


    public KeyCheck(Context context) throws GeneralSecurityException, IOException {
        masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        Log.d("NOTES", String.valueOf(myNoteSharedPref == null));
        myNoteSharedPref = EncryptedSharedPreferences.create(
                PIN,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
    }

    public boolean hasPin() {
        Log.d("n", String.valueOf(myNoteSharedPref == null));
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
