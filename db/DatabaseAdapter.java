package com.jonny.myexamplepac.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.jonny.myexamplepac.bean.GamePlayer;

import java.util.ArrayList;

/**
 * descreption: 数据库操作工具类
 * Created by Jonny on 2016/4/15.
 */
public class DatabaseAdapter {
    private DatabaseHelper dbHelper;

    public DatabaseAdapter(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    //添加操作
    public void add(GamePlayer gamePlayer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GameMetaData.GamePlayer.PLAYER,gamePlayer.getPlayer());
        values.put(GameMetaData.GamePlayer.SCORE,gamePlayer.getScore());
        values.put(GameMetaData.GamePlayer.LEVEL,gamePlayer.getLevel());
        db.insert(GameMetaData.GamePlayer.TABLE_NAME,null,values);
        db.close();
    }

    //删除操作
    public void delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = GameMetaData.GamePlayer._ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        db.delete(GameMetaData.GamePlayer.TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    //更新操作
    public void update(GamePlayer gamePlayer){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(GameMetaData.GamePlayer.PLAYER,gamePlayer.getPlayer());
        values.put(GameMetaData.GamePlayer.SCORE,gamePlayer.getScore());
        values.put(GameMetaData.GamePlayer.LEVEL,gamePlayer.getLevel());
        String whereClause = GameMetaData.GamePlayer._ID + "=?";
        String[] whereArgs = {String.valueOf(gamePlayer.getId())};
        db.update(GameMetaData.GamePlayer.TABLE_NAME, values, whereClause, whereArgs);
        db.close();
    }

    //查询操作
    public GamePlayer findById(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query(true, GameMetaData.GamePlayer.TABLE_NAME, null, GameMetaData.GamePlayer._ID + "=?", new String[]{String.valueOf(id)},null,null,null,null);
        GamePlayer gamePlayer = null;
        if(c.moveToNext()){
            gamePlayer = new GamePlayer();
            gamePlayer.setId(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer._ID)));
            gamePlayer.setPlayer(c.getString(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.PLAYER)));
            gamePlayer.setScore(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.SCORE)));
            gamePlayer.setLevel(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.LEVEL)));
        }
        c.close();
        db.close();
        return gamePlayer;
    }

    //查询所有
    public ArrayList<GamePlayer> findAll(){
        String sql = "select _id,player,score,level from player_table order by score desc";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.rawQuery(sql, null);
//        System.out.println("c.getColumnCount()"+c.getCount());
        ArrayList<GamePlayer> gamePlayers = new ArrayList<>();
        GamePlayer gamePlayer=null;
        while(c.moveToNext()){
            gamePlayer = new GamePlayer();
            gamePlayer.setId(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer._ID)));
            gamePlayer.setPlayer(c.getString(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.PLAYER)));
            gamePlayer.setScore(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.SCORE)));
            gamePlayer.setLevel(c.getInt(c.getColumnIndexOrThrow(GameMetaData.GamePlayer.LEVEL)));
            gamePlayers.add(gamePlayer);
        }
        c.close();
        db.close();
        return gamePlayers;
    }

    public int getCount(){
        int count = 0;
        String sql = "select count(_id) from player_table";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor c = db.rawQuery(sql,null);
        c.moveToFirst();
        count = c.getInt(0);
        c.close();
        db.close();
        return count;
    }

}
