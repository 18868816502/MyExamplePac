package com.jonny.myexamplepac.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.jonny.myexamplepac.R;
import com.jonny.myexamplepac.bean.GamePlayer;

public class AddFragment extends DialogFragment {
    private AddFragmentListener addFragmentListener;
    public static interface AddFragmentListener{
        public void add(GamePlayer gamePlayer);
    }
    public AddFragment() {
        // Required empty public constructor
    }
    public static AddFragment newInstance() {
        AddFragment frag = new AddFragment();
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            addFragmentListener = (AddFragmentListener) activity;
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final View view = LayoutInflater.from(getActivity()).inflate(R.layout.create_gameplayer_dialog,null);
        return new AlertDialog.Builder(getActivity())
                .setIcon(R.mipmap.icon1)
                .setView(view)
                .setTitle("新增游戏玩家")
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                EditText et_player = (EditText) view.findViewById(R.id.editText_player);
                                EditText et_score = (EditText) view.findViewById(R.id.editText_score);
                                EditText et_level = (EditText) view.findViewById(R.id.editText_level);
                                GamePlayer gamePlayer = new GamePlayer();
                                gamePlayer.setPlayer(et_player.getText().toString());
                                gamePlayer.setScore(Integer.parseInt(et_score.getText().toString()));
                                gamePlayer.setLevel(Integer.parseInt(et_level.getText().toString()));
                                addFragmentListener.add(gamePlayer);
                                dialog.dismiss();
                            }
                        }

                ).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }
}
