package com.example.kr.game_v1;

/**
 * Created by kr on 06/04/16.
 */
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class Slicer
{
    private int width;
    private int height;

    private int columns;
    private int rows;

    public Slicer(int width,int height,int columns,int rows)
    {
        this.width=width;
        this.height=height;
        this.columns =columns;
        this.rows =rows;
    }

    public PuzzlePiece[][] slice(Resources resources,int resId)
    {
        PuzzlePiece list[][]=new PuzzlePiece[rows][columns];

        Bitmap bitmap=loadBitmap(resources,resId);

        int t_width=width/columns;
        int t_height=height/rows;

        for(byte i=0;i<rows;i++)
        {
            for(byte j=0;j<columns;j++) {
                Bitmap b=Bitmap.createBitmap(bitmap,j*t_width,i*t_height,t_width,t_height);
                list[i][j] = new PuzzlePiece(b,i*columns+j);
            }
        }
        return list;
    }

    private Bitmap loadBitmap(Resources resources,int resId)
    {
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(resources,resId,options);
        options.inSampleSize=calculateInSampleSize(options);
        options.inJustDecodeBounds=false;

        Bitmap bitmap;
        bitmap=BitmapFactory.decodeResource(resources, resId, options);
        bitmap=Bitmap.createScaledBitmap(bitmap, width, height, false);
        return bitmap;
    }

    private int calculateInSampleSize(BitmapFactory.Options options)
    {
        int b_height=options.outHeight;
        int b_width=options.outWidth;
        int inSampleSize = 1;

        if (b_height>height && b_width>width)
        {
            int halfHeight=b_height/2;
            int halfWidth=b_width/2;

            while((halfHeight/inSampleSize)> height && (halfWidth/inSampleSize)>width)
            {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

}