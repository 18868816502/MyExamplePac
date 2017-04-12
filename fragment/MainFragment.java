package com.jonny.myexamplepac.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonny.myexamplepac.R;

public class MainFragment extends Fragment {
    private TextView tv;

    public MainFragment() {
    }

    /**
     * Fragment的传参方法
     * @param value
     * @return
     */
    public static MainFragment getInStance(String value){
        MainFragment p = new MainFragment();
        Bundle b = new Bundle();
        b.putString("value",value);
        p.setArguments(b);
        return p;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        tv= (TextView) view.findViewById(R.id.tv);
        return view;
    }

    public void changeTextViewValue(String value){
        tv.setText(value);
    }


}
