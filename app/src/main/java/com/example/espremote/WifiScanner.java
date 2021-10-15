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
        System.out.println("scan done");
        if (!success) {
            scanFailure();
        }
    }

    private void scanSuccess() {
        System.out.println("scan success");
        List<ScanResult> scanResults = wifiManager.getScanResults();
        printScanResult(scanResults);
        dataModel.getAPList().setValue(scanResults);
    }

    private void scanFailure() {
        System.out.println("scan failed");
        List<ScanResult> scanResults = wifiManager.getScanResults();
        printScanResult(scanResults);
        dataModel.getAPList().setValue(scanResults);
    }

    private void printScanResult(List<ScanResult> scanResults) {
        if (scanResults.size() > 0) {
            for (ScanResult scanResult : scanResults) {
                System.out.println(scanResult.SSID);
            }
        } else {
            System.out.println("No result");
        }

    }
}
