package com.jonny.myexamplepac.fragment;


import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.jonny.myexamplepac.R;


public class MenuFragment extends Fragment implements View.OnClickListener {
    private MyMenuListener myMenuListener;
    private Button bt1, bt2, bt3;

    public MenuFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        myMenuListener = (MyMenuListener) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        bt1 = (Button) view.findViewById(R.id.bt1);
        bt1.setOnClickListener(this);
        bt2 = (Button) view.findViewById(R.id.bt2);
        bt2.setOnClickListener(this);
        bt3 = (Button) view.findViewById(R.id.bt3);
        bt3.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt1:
                myMenuListener.changeValue("您好，我是按钮一的页面");
                break;
            case R.id.bt2:
                myMenuListener.changeValue("为了显示不同，所以我是按钮二的页面，很神奇吧，这就是Fragment的用法,这是轻量级的组件,可以省好多的内存,比activity要好多啦~~");
                break;
            case R.id.bt3:
                myMenuListener.changeFragment();
                break;
        }
    }

    /**
     * 定义一个回调接口
     */
    public static interface MyMenuListener {
        public void changeValue(String value);
        public void changeFragment();
    }

}
