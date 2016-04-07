package com.example.kr.game_v1;

import android.graphics.Color;
import android.util.Log;

/**
 * Created by kr on 06/04/16.
 */
public class GameManager implements PuzzleViewManager {

    private PuzzlePiece[][] pieces;
    private int rows;
    private int columns;

    private int row_hiddenPiece;
    private int column_hiddenPiece;

    public GameManager(PuzzlePiece[][] pieces,int row_hiddenPiece,int column_hiddenPiece)
    {
        this.pieces=pieces;
        this.rows=pieces.length;
        this.columns=pieces[0].length;

        this.row_hiddenPiece=row_hiddenPiece;
        this.column_hiddenPiece=column_hiddenPiece;

        pieces[row_hiddenPiece][column_hiddenPiece].getBitmap().eraseColor(Color.BLACK);
        setPermittedMovement();
    }

    public void shuffle()
    {
        for(int i=0;i<100;i++)
            moveRandomPiece();
    }

    public boolean win()
    {
        int o=0;
        for(byte i=0;i<rows;i++)
        {
            for(byte j=0;j<columns;j++) {
                if(pieces[i][j].getRank()!=o)
                    return false;
                o++;
            }
        }
        return true;
    }

    public Slidable[][] getSlidables()
    {
        return pieces;
    }

    public void actualize(int row,int column,Slidable.Direction direction)
    {
        move(row, column, direction);
    }

    private void move(int row,int column,Slidable.Direction direction)
    {
        PuzzlePiece temp=pieces[row][column];
        switch (direction)
        {
            case UP:
                pieces[row][column]=pieces[row-1][column];
                pieces[row-1][column]=temp;
                row_hiddenPiece++;
                break;
            case RIGHT:
                pieces[row][column]=pieces[row][column+1];
                pieces[row][column+1]=temp;
                column_hiddenPiece--;
                break;
            case LEFT:
                pieces[row][column]=pieces[row][column-1];
                pieces[row][column-1]=temp;
                column_hiddenPiece++;
                break;
            case DOWN:
                pieces[row][column]=pieces[row+1][column];
                pieces[row+1][column]=temp;
                row_hiddenPiece--;
                break;
        }
        setPermittedMovement();
    }

    private void setPermittedMovement()
    {
        for(byte i=0;i<rows;i++)
        {
            for(byte j=0;j<columns;j++) {
                pieces[i][j].setPermittedDirection(Slidable.Direction.NONE);
            }
        }

        if(row_hiddenPiece>0)
            pieces[row_hiddenPiece-1][column_hiddenPiece].setPermittedDirection(Slidable.Direction.DOWN);
        if(row_hiddenPiece<rows-1)
            pieces[row_hiddenPiece+1][column_hiddenPiece].setPermittedDirection(Slidable.Direction.UP);
        if(column_hiddenPiece>0)
            pieces[row_hiddenPiece][column_hiddenPiece-1].setPermittedDirection(Slidable.Direction.RIGHT);
        if(column_hiddenPiece<columns-1)
            pieces[row_hiddenPiece][column_hiddenPiece + 1].setPermittedDirection(Slidable.Direction.LEFT);
    }

    private void moveRandomPiece()
    {
        Slidable.Direction pieces[]=new  Slidable.Direction[4];
        byte n=0;

        if(row_hiddenPiece>0) {
            pieces[n]= Slidable.Direction.UP;
            n++;
        }
        if(row_hiddenPiece<rows-1) {
            pieces[n]= Slidable.Direction.DOWN;
            n++;
        }
        if(column_hiddenPiece>0) {
            pieces[n]= Slidable.Direction.LEFT;
            n++;
        }
        if(column_hiddenPiece<columns-1) {
            pieces[n]= Slidable.Direction.RIGHT;
            n++;
        }

        n=random(n);

        if(n!=-1)
        {
            switch (pieces[n])
            {
                case UP:
                    move(row_hiddenPiece-1,column_hiddenPiece, Slidable.Direction.DOWN);
                    break;
                case DOWN:
                    move(row_hiddenPiece+1,column_hiddenPiece, Slidable.Direction.UP);
                    break;
                case LEFT:
                    move(row_hiddenPiece,column_hiddenPiece-1, Slidable.Direction.RIGHT);
                    break;
                case RIGHT:
                    move(row_hiddenPiece,column_hiddenPiece+1, Slidable.Direction.LEFT);
                    break;
            }
        }

    }

    private byte random(byte n)
    {
        double r=Math.random()*120;

        switch (n)
        {
            case 1:
                return 0;
            case 2:
                if(r<60)
                    return 0;
                else
                    return 1;
            case 3:
                if (r<40)
                    return 0;
                else if(r<80)
                    return 1;
                else
                    return 2;
            case 4:
                if (r<30)
                    return 0;
                else if(r<60)
                    return 1;
                else if(r<90)
                    return 2;
                else
                    return 3;
        }
        return -1;
    }
}
