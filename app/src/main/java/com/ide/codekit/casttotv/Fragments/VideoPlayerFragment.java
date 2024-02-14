package com.ide.codekit.casttotv.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ide.codekit.casttotv.Adapter.VideoAdapter;
import com.ide.codekit.casttotv.Extras.Utils;
import com.ide.codekit.casttotv.Model.DataModel;
import com.ide.codekit.casttotv.R;
import com.ide.codekit.casttotv.databinding.FragmentVideoPlayerBinding;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoPlayerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoPlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoPlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoPlayerFragment newInstance(String param1, String param2) {
        VideoPlayerFragment fragment = new VideoPlayerFragment();
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

    FragmentVideoPlayerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentVideoPlayerBinding.inflate(getLayoutInflater());

//        Utils.applyGradientOnTv(binding.titleTv, "#43E97B", "#38F9D7");

        binding.backBt.setOnClickListener(view -> {
            requireActivity().onBackPressed();
        });

        setVideoData();

        return binding.getRoot();
    }

    private void setVideoData() {
        List<DataModel> videoList;
        videoList = Utils.getAllVideoList(requireActivity());
        VideoAdapter adapter = new VideoAdapter(new VideoAdapter.DataClickListener() {
            @Override
            public void onDataClick(DataModel dataModel) {
//                startActivity(new Intent(DashboardActivity.this, PreviewActivity.class).putExtra("activityName", "DashboardActivity").putExtra("dataType", getString(R.string.charging) + " " + getString(R.string.animation)).putExtra("screenType", "lock").putExtra("dataUrl", url));
            }
        });
        adapter.setViewType(VideoAdapter.VIEW_GRID);
        binding.vpRv.setAdapter(adapter);
        if (videoList.size() > 9) {
            adapter.submitList(videoList.subList(0, 8));
        } else {
            adapter.submitList(videoList);
        }
    }
}