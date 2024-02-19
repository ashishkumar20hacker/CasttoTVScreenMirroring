package com.ide.codekit.casttotv.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ide.codekit.casttotv.Adapter.CastAdapter;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.Model.DataModel;
import com.ide.codekit.casttotv.databinding.FragmentCastToTvBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CastToTvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CastToTvFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CastToTvFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CastToTvFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CastToTvFragment newInstance(String param1, String param2) {
        CastToTvFragment fragment = new CastToTvFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FragmentCastToTvBinding binding;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCastToTvBinding.inflate(getLayoutInflater());

//        Utils.applyGradientOnTv(binding.titleTv, "#43E97B", "#38F9D7");

        binding.backBt.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });

        setupUI();

        return binding.getRoot();
    }

    private void setupUI() {
        binding.refreshBt.setOnClickListener(view -> {
            setAdapter();
            Toast.makeText(requireActivity(), "Refreshing data...", Toast.LENGTH_SHORT).show();
        });
        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            getWifiInfoSSID();
        }
    }

    private void getWifiInfoSSID() {
        WifiManager wifiManager = (WifiManager) requireActivity().getSystemService(Context.WIFI_SERVICE);
        if (wifiManager != null && wifiManager.isWifiEnabled()) {
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null) {
                String ssid = wifiInfo.getSSID();
                if (ssid != null && ssid.startsWith("\"") && ssid.endsWith("\"")) {
                    ssid = ssid.substring(1, ssid.length() - 1);
                }
                binding.connectToADeviceTv.setText("Connected to " + ssid);
            }
        } else {
            binding.connectToADeviceTv.setText("Connected to Mobile Data");
        }
    }

    private void setAdapter() {
        CastAdapter adapter = new CastAdapter();
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setItemViewCacheSize(10);
        Utils.getCasts(requireActivity()).observe(requireActivity(), new Observer<List<DataModel>>() {
            @Override
            public void onChanged(List<DataModel> dataModels) {
                if (dataModels.size() > 0) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    binding.loader.setVisibility(View.GONE);
                    return;
                } else {
                    binding.recyclerView.setVisibility(View.GONE);
                    binding.loader.setVisibility(View.VISIBLE);
                }
                adapter.submitList(dataModels);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        setAdapter();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getWifiInfoSSID();
        }
    }
}