package com.ide.codekit.casttotv.Extras;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;

import java.util.ArrayList;
import java.util.List;

public class NearbyDeviceScanner {
    private final Context context;
    private List<NsdServiceInfo> discoveredDevices;
    private NsdManager.DiscoveryListener discoveryListener;
    private NsdManager nsdManager;

    public interface DeviceScanListener {
        void onDeviceFound(NsdServiceInfo nsdServiceInfo);
        void onDeviceLost(NsdServiceInfo nsdServiceInfo);
        void onScanFailed(String str);
        void onScanStarted();
        void onScanStopped();
    }

    public NearbyDeviceScanner(Context context) {
        this.context = context.getApplicationContext();
    }

    public void startScan(final DeviceScanListener deviceScanListener) {
        discoveredDevices = new ArrayList<>();
        nsdManager = (NsdManager) context.getSystemService(Context.NSD_SERVICE);
        if (nsdManager == null) {
            deviceScanListener.onScanFailed("NSD service not available");
            return;
        }

        discoveryListener = new MyDiscoveryListener(deviceScanListener);
        nsdManager.discoverServices("_googlecast._tcp.", NsdManager.PROTOCOL_DNS_SD, discoveryListener);
    }

    public void stopScan() {
        if (nsdManager != null && discoveryListener != null) {
            nsdManager.stopServiceDiscovery(discoveryListener);
            nsdManager = null;
            discoveryListener = null;
        }
    }

    private class MyDiscoveryListener implements NsdManager.DiscoveryListener {
        private final DeviceScanListener deviceScanListener;

        public MyDiscoveryListener(DeviceScanListener deviceScanListener) {
            this.deviceScanListener = deviceScanListener;
        }

        @Override
        public void onStartDiscoveryFailed(String serviceType, int errorCode) {
            deviceScanListener.onScanFailed("Start discovery failed: " + errorCode);
        }

        @Override
        public void onStopDiscoveryFailed(String serviceType, int errorCode) {
            deviceScanListener.onScanFailed("Stop discovery failed: " + errorCode);
        }

        @Override
        public void onDiscoveryStarted(String serviceType) {
            deviceScanListener.onScanStarted();
        }

        @Override
        public void onDiscoveryStopped(String serviceType) {
            deviceScanListener.onScanStopped();
        }

        @Override
        public void onServiceFound(NsdServiceInfo serviceInfo) {
            if (serviceInfo.getServiceType().equals("_googlecast._tcp.")) {
                discoveredDevices.add(serviceInfo);
                deviceScanListener.onDeviceFound(serviceInfo);
            }
        }

        @Override
        public void onServiceLost(NsdServiceInfo serviceInfo) {
            discoveredDevices.remove(serviceInfo);
            deviceScanListener.onDeviceLost(serviceInfo);
        }
    }
}
