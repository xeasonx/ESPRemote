package com.example.espremote;

import android.net.wifi.ScanResult;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class APListAdapter extends ListAdapter<ScanResult, APListAdapter.ViewHolder> {
    public APListAdapter() {
        super(DIFF_CALLBACK);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.ap_list_item);
        }

        public void bindTo(ScanResult scanResult) {
            textView.setText(scanResult.SSID);
        }
    }

    public static final DiffUtil.ItemCallback<ScanResult> DIFF_CALLBACK = new DiffUtil.ItemCallback<ScanResult>() {
        @Override
        public boolean areItemsTheSame(@NonNull ScanResult oldItem, @NonNull ScanResult newItem) {
            return newItem.BSSID.equals(oldItem.BSSID);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ScanResult oldItem, @NonNull ScanResult newItem) {
            return newItem.BSSID.equals(oldItem.BSSID);
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ap_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindTo(getItem(position));
    }
}
