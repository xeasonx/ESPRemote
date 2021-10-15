package com.example.espremote;

import android.net.wifi.ScanResult;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class DataModel extends ViewModel {
    private MutableLiveData<List<ScanResult>> APList;

    public MutableLiveData<List<ScanResult>> getAPList() {
        if (APList == null) {
            APList = new MutableLiveData<List<ScanResult>>();
        }
        return APList;
    }
}
