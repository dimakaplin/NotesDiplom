package com.example.notes;

public interface KeyStore {
    boolean hasPin();
    boolean checkPin(String pin);
    void saveNew(String pin);
}
