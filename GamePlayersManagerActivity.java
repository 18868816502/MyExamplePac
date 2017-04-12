package com.jonny.myexamplepac;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.jonny.myexamplepac.bean.GamePlayer;
import com.jonny.myexamplepac.db.DatabaseAdapter;
import com.jonny.myexamplepac.fragment.AddFragment;
import com.jonny.myexamplepac.fragment.GamePlayerFragment;
import com.jonny.myexamplepac.fragment.UpdateFragment;

import java.util.ArrayList;

public class GamePlayersManagerActivity extends AppCompatActivity implements AddFragment.AddFragmentListener, GamePlayerFragment.GamePlayerFragmentListener, UpdateFragment.UpdateFragmentListener {
    private DatabaseAdapter dbAdapter;
    private GamePlayerFragment gamePlayerFragment;
    private UpdateFragment updateFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_players_manager);
        dbAdapter = new DatabaseAdapter(this);
        showGamePlayerFragment();
    }

    @Override
    public void showGamePlayerFragment() {
        gamePlayerFragment = GamePlayerFragment.newInstance();
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_layout, gamePlayerFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void showUpdateFragment(int id) {
        updateFragment = UpdateFragment.newInstance(id);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_layout, updateFragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (getFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            } else {
                getFragmentManager().popBackStack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.add:
                AddFragment createGamePlayerFragment = AddFragment.newInstance();
                createGamePlayerFragment.show(getFragmentManager(), null);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void delete(int id) {
        dbAdapter.delete(id);
        gamePlayerFragment.changedData();
    }

    @Override
    public ArrayList<GamePlayer> findAll() {
        return dbAdapter.findAll();
    }

    @Override
    public void update(GamePlayer gamePlayer) {
        dbAdapter.update(gamePlayer);
        gamePlayerFragment.changedData();
    }

    @Override
    public GamePlayer findById(int id) {
        return dbAdapter.findById(id);
    }

    @Override
    public void add(GamePlayer gamePlayer) {
        dbAdapter.add(gamePlayer);
        gamePlayerFragment.changedData();
    }
}
