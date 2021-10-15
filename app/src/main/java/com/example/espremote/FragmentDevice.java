package com.example.espremote;

import android.app.ActionBar;
import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentDevice#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentDevice extends Fragment {
    private ActionBar actionBar;
    private WifiScanner scanner;
    private DataModel dataModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private APListAdapter apListAdapter;
    private WifiManager wifiManager;
    private boolean isScanning = false;

    public FragmentDevice() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment fragment_device.
     */
    public static FragmentDevice newInstance() {
        FragmentDevice fragment = new FragmentDevice();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_device, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        actionBar = ((AppCompatActivity)getActivity()).getActionBar();
        dataModel = new ViewModelProvider(this).get(DataModel.class);
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.ap_list_recycler);
        apListAdapter = new APListAdapter();
        wifiManager = (WifiManager) getContext().getSystemService(Context.WIFI_SERVICE);
        scanner = new WifiScanner(getContext(), wifiManager, dataModel);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.scrollToPosition(0);
        recyclerView.setAdapter(apListAdapter);

        Observer<List<ScanResult>> observer = new Observer<List<ScanResult>>() {
            @Override
            public void onChanged(List<ScanResult> scanResults) {
                apListAdapter.submitList(scanResults);
                isScanning = false;
            }
        };
        dataModel.getAPList().observe(this, observer);
        isScanning = true;
        scanner.scan();
        
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                if (!isScanning) {
                    scanner.scan();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}