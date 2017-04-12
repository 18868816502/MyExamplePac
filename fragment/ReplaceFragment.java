package com.jonny.myexamplepac.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jonny.myexamplepac.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReplaceFragment extends Fragment {


    public ReplaceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_replace, container, false);
    }

}
