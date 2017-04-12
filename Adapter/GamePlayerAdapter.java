package com.jonny.myexamplepac.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.jonny.myexamplepac.R;
import com.jonny.myexamplepac.bean.GamePlayer;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/4/15.
 */
public class GamePlayerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<GamePlayer> gamePlayers;

    public void setGamePlayers(ArrayList<GamePlayer> gamePlayers) {
        this.gamePlayers = gamePlayers;
    }

    public GamePlayerAdapter(Context context, ArrayList<GamePlayer> gamePlayers) {
        this.context = context;
        this.gamePlayers = gamePlayers;
    }

    @Override
    public int getCount() {
//        System.out.println("size: "+gamePlayers.size());
        return gamePlayers.size();
    }

    @Override
    public Object getItem(int position) {
        return gamePlayers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_gameplayer,null);
            vh = new ViewHolder();
            vh.tv_id = (TextView) convertView.findViewById(R.id.tv_id);
            vh.tv_player = (TextView) convertView.findViewById(R.id.tv_player);
            vh.tv_score = (TextView) convertView.findViewById(R.id.tv_score);
            vh.tv_level = (TextView) convertView.findViewById(R.id.tv_level);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        GamePlayer g = gamePlayers.get(position);
        vh.tv_id.setText(String.valueOf(g.getId()));
        vh.tv_player.setText(g.getPlayer());
        vh.tv_score.setText(String.valueOf(g.getScore()));
        vh.tv_level.setText(String.valueOf(g.getLevel()));
        return convertView;
    }

    private static class ViewHolder {
        TextView tv_id;
        TextView tv_player;
        TextView tv_score;
        TextView tv_level;
    }
}
