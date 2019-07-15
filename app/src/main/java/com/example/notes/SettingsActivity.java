package com.example.notes;

import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private KeyStore keyStore;
    private ImageButton visibleControl;
    private Button saveBtn;

    private EditText pinEdit;
    private TextView errorText;

    private boolean visible;

    private final String ERROR_PIN = "пин слишком короткий";

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.back:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
    }

    private void init() {
        keyStore = App.getKeystore();
        visibleControl = findViewById(R.id.visible);
        saveBtn = findViewById(R.id.btn_save);
        pinEdit = findViewById(R.id.input_pin);
        errorText = findViewById(R.id.error_text);
        visible = false;

        saveBtn.setOnClickListener(v -> {
            String pin = pinEdit.getText().toString();
            if (pin.length() == 4) {
                errorText.setText("");
                keyStore.saveNew(pin);
                onBackPressed();
                // Toast.makeText(SettingsActivity.this, R.string.pin_message, Toast.LENGTH_SHORT).show();

            } else {
                errorText.setText(ERROR_PIN);
            }

        });

        visibleControl.setOnClickListener(v -> {
            visible = !visible;
            checkVisible(visible);
        });
    }

    private void checkVisible(boolean vis) {
        if (vis) {
            visibleControl.setImageResource(R.drawable.vis_on);
            pinEdit.setTransformationMethod(null);
        } else {
            visibleControl.setImageResource(R.drawable.vis_off);
            pinEdit.setTransformationMethod(new PasswordTransformationMethod());
        }
    }


}
