package com.example.notes;

import android.app.Application;

public class App extends Application {
    private static DataStorage dataStorage;
  //  private static Keystore keystore;
    @Override
    public void onCreate() {
        super.onCreate();

        /* Конкретная реализация выбирается только здесь.
           Изменением одной строчки здесь,
           мы заменяем реализацию во всем приложении!
        */

        dataStorage = new DataStorage(this);
       // passwordStorage = new SimpleKeystore(this);
    }
    // Возвращаем интерфейс, а не конкретную реализацию!
    public static DataStorage getDataStorage() {
        return dataStorage;
    }
/*    // Возвращаем интерфейс, а не конкретную реализацию!
    public static Keystore getKeystore() {
        return keystore;
    }*/
}
