package com.example.notes;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Random;

public class KeyCheck implements KeyStore {
    private SharedPreferences myNoteSharedPref;
    private final String PIN_CODE = "PIN_CODE";
    private final String KEY = "KEY";
    private final String SECURE_INFO = "NOTES_APP";

    private String masterKeyAlias;


    public KeyCheck(Context context) throws GeneralSecurityException, IOException {
        masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
        Log.d("NOTES", String.valueOf(myNoteSharedPref == null));
        myNoteSharedPref = EncryptedSharedPreferences.create(
                SECURE_INFO,
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        );
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

    public byte[] getKey() {
        if(myNoteSharedPref.contains(KEY)) {
            byte[] key = myNoteSharedPref.getString(KEY, "").getBytes(StandardCharsets.ISO_8859_1);
            Log.d("NOTES", new String(key, StandardCharsets.ISO_8859_1));
            return key;
        } else {
            Random rand = new Random(64);
            byte[] key = new byte[64];
            rand.nextBytes(key);
            SharedPreferences.Editor myEditor = myNoteSharedPref.edit();
            myEditor.putString(KEY, new String(key, StandardCharsets.ISO_8859_1));
            myEditor.apply();
            return key;
        }
    }
}
