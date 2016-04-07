package com.example.kr.game_v1;


/**
 * Created by kr on 06/04/16.
 */
public interface PuzzleViewManager {
    Slidable[][] getSlidables();
    void actualize(int row,int column,Slidable.Direction direction);
}
