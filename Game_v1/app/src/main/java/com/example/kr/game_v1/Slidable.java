package com.example.kr.game_v1;

import android.graphics.Bitmap;

/**
 * Created by kr on 06/04/16.
 */
public interface Slidable {

    boolean topFree();
    boolean rightFree();
    boolean leftFree();
    boolean bottomFree();
    Bitmap getBitmap();

    enum Direction
    {
        RIGHT,UP,LEFT,DOWN,NONE
    }
}
