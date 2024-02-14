package com.ide.codekit.casttotv.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.FragmentCastToTvBinding;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCastToTvBinding.inflate(getLayoutInflater());

//        Utils.applyGradientOnTv(binding.titleTv, "#43E97B", "#38F9D7");

        binding.backBt.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });

        return binding.getRoot();
    }
}