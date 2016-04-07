package com.example.kr.game_v1;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kr on 07/04/16.
 */
public class PuzzleView extends View {

    private PuzzleViewManager manager;
    private int rows;
    private int columns;

    private int row_movingPiece;
    private int column_movingPiece;

    private boolean movement=false;
    private float first_x;
    private float first_y;
    private float x=0;
    private float y=0;

    private int width;
    private int height;

    public PuzzleView(Context context,PuzzleViewManager manager) {
        super(context);
        this.manager=manager;

        this.rows=manager.getSlidables().length;
        this.columns=manager.getSlidables()[0].length;

        width=manager.getSlidables()[0][0].getBitmap().getWidth();
        height=manager.getSlidables()[0][0].getBitmap().getHeight();
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(byte i=0;i<rows;i++)
        {
            for(byte j=0;j<columns;j++) {
                if(!movement || (i!=row_movingPiece || j!=column_movingPiece))
                    canvas.drawBitmap(manager.getSlidables()[i][j].getBitmap(),width*j, height*i, null);
            }
        }
        if(movement)
            canvas.drawBitmap(manager.getSlidables()[row_movingPiece][column_movingPiece].getBitmap(),(width*column_movingPiece)+x, (height*row_movingPiece)+y, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if(movement) {
                    Slidable s = manager.getSlidables()[row_movingPiece][column_movingPiece];
                    if (s.rightFree() && motionEvent.getX() > first_x && motionEvent.getX() < first_x + width)
                        x = motionEvent.getX() - first_x;
                    if (s.topFree() && motionEvent.getY() < first_y && motionEvent.getY() > first_y - height)
                        y = motionEvent.getY() - first_y;
                    if (s.leftFree() && motionEvent.getX() < first_x && motionEvent.getX() > first_x - width)
                        x = motionEvent.getX() - first_x;
                    if (s.bottomFree() && motionEvent.getY() > first_y && motionEvent.getY() < first_y + height)
                        y = motionEvent.getY() - first_y;
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_DOWN:
                if(!movement) {
                    int row = (int) motionEvent.getY() / height;
                    int column = (int) motionEvent.getX() / width;
                    Slidable s = manager.getSlidables()[row][column];
                    if (s.bottomFree() || s.leftFree() || s.topFree() || s.rightFree()) {
                        movement = true;
                        first_x = motionEvent.getX();
                        first_y = motionEvent.getY();
                        row_movingPiece = row;
                        column_movingPiece = column;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(movement){
                    if(x>width/2)
                        manager.actualize(row_movingPiece,column_movingPiece, Slidable.Direction.RIGHT);
                    else if(-1*x>width/2)
                        manager.actualize(row_movingPiece,column_movingPiece, Slidable.Direction.LEFT);
                    if(y>height/2)
                        manager.actualize(row_movingPiece,column_movingPiece, Slidable.Direction.DOWN);
                    else if(-1*y>height/2)
                        manager.actualize(row_movingPiece,column_movingPiece, Slidable.Direction.UP);
                    movement=false;
                    x=0;
                    y=0;
                    invalidate();
                }
                break;
        }
        return true;
    }
}
