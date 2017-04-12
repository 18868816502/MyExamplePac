package com.jonny.myexamplepac.fragment;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.jonny.myexamplepac.Adapter.GamePlayerAdapter;
import com.jonny.myexamplepac.R;
import com.jonny.myexamplepac.bean.GamePlayer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class GamePlayerFragment extends Fragment {

    private GamePlayerFragmentListener gamePlayerFragmentListener;
    private GamePlayerAdapter gamePlayerAdapter;
    public static interface GamePlayerFragmentListener{
        public void showGamePlayerFragment();
        public void showUpdateFragment(int id);
        public void delete(int id);
        public ArrayList<GamePlayer> findAll();
    }

    public static GamePlayerFragment newInstance(){
        GamePlayerFragment frag = new GamePlayerFragment();
        return frag;
    }
    public GamePlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("修改/删除");
        menu.setHeaderIcon(R.mipmap.icon1);
        getActivity().getMenuInflater().inflate(R.menu.listview_context_menu,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info;
        TextView tv_id;
        int id;
        switch (item.getItemId()){
            case R.id.delete_menu:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                tv_id = (TextView) info.targetView.findViewById(R.id.tv_id);
                id = Integer.parseInt(tv_id.getText().toString());
                gamePlayerFragmentListener.delete(id);
                changedData();
                break;
            case R.id.update_menu:
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                tv_id = (TextView) info.targetView.findViewById(R.id.tv_id);
                id = Integer.parseInt(tv_id.getText().toString());
                gamePlayerFragmentListener.showUpdateFragment(id);
                break;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        gamePlayerFragmentListener = null;
    }

    /**
     * 刷新视图
     */
    public void changedData(){
        gamePlayerAdapter.setGamePlayers(gamePlayerFragmentListener.findAll());
        gamePlayerAdapter.notifyDataSetChanged();
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ArrayList<GamePlayer> gamePlayers = gamePlayerFragmentListener.findAll();
        gamePlayerAdapter = new GamePlayerAdapter(getActivity(),gamePlayers);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_game_player,container,false);
        ListView listView = (ListView) view.findViewById(R.id.listView);
        registerForContextMenu(listView);//注册上下文,长按时弹出删除和修改选项
        listView.setAdapter(gamePlayerAdapter);
        return view;
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            gamePlayerFragmentListener = (GamePlayerFragmentListener) activity;
        } catch (ClassCastException e){
            e.printStackTrace();
        }
    }

}
