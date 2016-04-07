package com.example.kr.game_v1;

import android.graphics.Bitmap;

/**
 * Created by kr on 06/04/16.
 */
public class PuzzlePiece implements Slidable {

    private Bitmap bitmap;
    private int rank;//le rang initiale de la pièce dans la grille
    private Direction permittedDirection;

    public PuzzlePiece(Bitmap bitmap,int rank)
    {
        this.bitmap=bitmap;
        this.rank=rank;
    }

    public void setBitmap(Bitmap bitmap)
    {
        this.bitmap=bitmap;
    }
    public void setPermittedDirection(Direction direction)
    {
        this.permittedDirection=direction;
    }
    public int getRank()
    {
        return rank;
    }
    public boolean topFree()
    {
        if(permittedDirection==Direction.UP)
            return true;
        return false;
    }

    public boolean rightFree()
    {
        if(permittedDirection==Direction.RIGHT)
            return true;
        return false;
    }
    public boolean leftFree()
    {
        if(permittedDirection==Direction.LEFT)
            return true;
        return false;
    }
    public boolean bottomFree()
    {
        if(permittedDirection==Direction.DOWN)
            return true;
        return false;
    }
    public Bitmap getBitmap()
    {
       return bitmap;
    }
}
