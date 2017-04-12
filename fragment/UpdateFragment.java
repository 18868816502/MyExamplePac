package com.jonny.myexamplepac.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jonny.myexamplepac.R;
import com.jonny.myexamplepac.bean.GamePlayer;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateFragment extends Fragment implements View.OnClickListener {
    private UpdateFragmentListener updateFragmentListener;
    private GamePlayer gamePlayer;
    private EditText et_player, et_score, et_level;
    private Button bt_cancel, bt_update;


    public static interface UpdateFragmentListener {
        public void update(GamePlayer gamePlayer);

        public GamePlayer findById(int id);
    }

    public UpdateFragment() {
        // Required empty public constructor
    }

    public static UpdateFragment newInstance(int id) {
        UpdateFragment frag = new UpdateFragment();
        Bundle b = new Bundle();
        b.putInt("id", id);
        frag.setArguments(b);
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            updateFragmentListener = (UpdateFragmentListener) activity;
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        updateFragmentListener = null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int id = getArguments().getInt("id");
        gamePlayer = updateFragmentListener.findById(id);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_update, container, false);
        TextView tv_id = (TextView) view.findViewById(R.id.tv_id);
        et_player = (EditText) view.findViewById(R.id.et_player);
        et_score = (EditText) view.findViewById(R.id.et_score);
        et_level = (EditText) view.findViewById(R.id.et_level);
        bt_update = (Button) view.findViewById(R.id.bt_update);
        bt_update.setOnClickListener(this);
        bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        bt_cancel.setOnClickListener(this);
        tv_id.setText(String.valueOf(gamePlayer.getId()));
        et_player.setText(gamePlayer.getPlayer());
        et_score.setText(String.valueOf(gamePlayer.getScore()));
        et_level.setText(String.valueOf(gamePlayer.getLevel()));
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_update:
                save();
                break;
            case R.id.bt_cancel:
                getActivity().getFragmentManager().popBackStack();
                break;
        }
    }

    public void save() {
        GamePlayer g = new GamePlayer();
        g.setId(gamePlayer.getId());
        g.setPlayer(et_player.getText().toString());
        g.setScore(Integer.parseInt(et_score.getText().toString()));
        g.setLevel(Integer.parseInt(et_level.getText().toString()));
        updateFragmentListener.update(g);
        getActivity().getFragmentManager().popBackStack();
    }
}
