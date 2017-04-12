package com.jonny.myexamplepac.bean;

/**
 * 游戏玩家实体类
 * Created by Jonny on 2016/4/15.
 */
public class GamePlayer {
    private int id;
    private String player;
    private int score;
    private int level;

    public GamePlayer() {
    }

    public GamePlayer(int id, String player, int score, int level) {
        this.id = id;
        this.player = player;
        this.score = score;
        this.level = level;
    }

    public int getId() {
        return id;
    }

    public String getPlayer() {
        return player;
    }

    public int getScore() {
        return score;
    }

    public int getLevel() {
        return level;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
