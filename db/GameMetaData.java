package com.jonny.myexamplepac.db;

import android.provider.BaseColumns;

/**
 * descreption: 数据库元数据
 * Created by Jonny on 2016/4/15.
 */
public class GameMetaData {
    private GameMetaData(){}
    public static abstract class GamePlayer implements BaseColumns{
        public static final String TABLE_NAME = "player_table";//表名
        public static final String PLAYER = "player";//玩家名
        public static final String SCORE = "score";//分数
        public static final String LEVEL = "level";//关卡数
    }
}
