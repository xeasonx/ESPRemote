package com.example.espremote;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

public class WifiScanner {
    private WifiManager wifiManager;
    private BroadcastReceiver receiver;
    private IntentFilter intentFilter;
    private Context context;
    private DataModel dataModel;

    public WifiScanner(Context context, WifiManager wifiManager, DataModel dataModel) {
        this.context = context;
        this.wifiManager = wifiManager;
        this.dataModel = dataModel;
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                boolean success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    scanSuccess();
                } else {
                    scanFailure();
                }
            }
        };
        intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(receiver, intentFilter);
    }

    public void scan() {
        boolean success = wifiManager.startScan();
        if (!success) {
            scanFailure();
        }
    }

    private void scanSuccess() {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        dataModel.getAPList().setValue(scanResults);
    }

    private void scanFailure() {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        dataModel.getAPList().setValue(scanResults);
    }
}
