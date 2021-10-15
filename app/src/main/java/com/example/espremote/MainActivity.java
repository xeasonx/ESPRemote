package com.example.espremote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        Map<String, String> preferences = getPreferences();
        String BSSID = preferences.getOrDefault("BSSID", "");
        if (!BSSID.equals("")) {
            // scan ESP device in network
        } else {
            // guide user to connect to ESP
            getSupportFragmentManager()
                    .beginTransaction()
                    .setReorderingAllowed(true)
                    .replace(R.id.fragment_device, FragmentDevice.newInstance())
                    .commit();
        }

    }

    public Map<String, String> getPreferences() {
        SharedPreferences preferences = getPreferences(Context.MODE_PRIVATE);
        Map<String, String> map = (Map<String, String>) preferences.getAll();
        return map;
    }

    public void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
    }
}