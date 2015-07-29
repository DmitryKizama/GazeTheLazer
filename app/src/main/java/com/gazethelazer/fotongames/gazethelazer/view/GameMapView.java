package com.gazethelazer.fotongames.gazethelazer.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.gazethelazer.fotongames.gazethelazer.controller.ControllerDraw;
import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;

public class GameMapView extends View {
    float mPreviousX;
    float mPreviousY;
    int mCurPointerId = Final.INVALID_POINTER_ID;

    float mShiftX;
    float mShiftY;

    Bitmap mBitmap;

    int mWidth;
    int mHeight;

    int mScreenWidth;
    int mScreenHeight;
    ControllerDraw mController;

    Paint mDummyPaint = new Paint();

    public GameMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Log.i("lazer", "constructor!");
        mDummyPaint.setStyle(Paint.Style.FILL);
    }

    public GameMapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameMapView(Context context) {
        this(context, null, 0);
    }

    public void setController(ControllerDraw controllerDraw)
    {
        mController = controllerDraw;
    }

    public void generateBlankBitmap()
    {
        mHeight = mController.getHeight();
        mWidth = mController.getWidth();

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawColor(Color.WHITE);
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        if (mBitmap == null)
            generateBlankBitmap();

        mScreenWidth = w;
        mScreenHeight = h;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent ev) {
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                mPreviousX = ev.getX();
                mPreviousY = ev.getY();
                mCurPointerId = ev.getPointerId(0);

                break;

            case MotionEvent.ACTION_MOVE:
                int index = ev.findPointerIndex(mCurPointerId);
                float x = ev.getX(index);
                float y = ev.getY(index);

                float relshiftX = x - mPreviousX;
                float relshiftY = y - mPreviousY;

                boolean invalidate = false;

                //Screen border: X
                if (mWidth > mScreenWidth) {
                    if (mShiftX + relshiftX > 0) {
                        mShiftX = 0;
                        mPreviousX = 0;
                    } else if (mShiftX + relshiftX < -(mWidth - mScreenWidth)) {
                        mShiftX = -(mWidth - mScreenWidth);
                        mPreviousX = x;
                    } else {
                        mShiftX += relshiftX;
                        mPreviousX = x;
                    }
                    invalidate = true;
                }

                //Screen border: Y
                if (mHeight > mScreenHeight) {
                    if (mShiftY + relshiftY > 0) {
                        mShiftY = 0;
                        mPreviousY = 0;
                    } else if (mShiftY + relshiftY < -(mHeight - mScreenHeight)) {
                        mShiftY = -(mHeight - mScreenHeight);
                        mPreviousY = y;
                    } else {
                        mShiftY += relshiftY;
                        mPreviousY = y;
                    }
                    invalidate = true;
                }

                if (invalidate) invalidate();

                break;

            case MotionEvent.ACTION_UP:
                mCurPointerId = Final.INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_CANCEL:
                mCurPointerId = Final.INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_POINTER_UP:

            {
                final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK)
                        >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
                final int pointerId = ev.getPointerId(pointerIndex);
                if (pointerId == mCurPointerId) {
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    mPreviousX = ev.getX(newPointerIndex);
                    mPreviousY = ev.getY(newPointerIndex);
                    mCurPointerId = ev.getPointerId(newPointerIndex);
                }
                break;
            }
        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();

        canvas.translate(mShiftX, mShiftY);
        // TODO: clean this up
        Rect rect = new Rect(-(int)mShiftX, -(int)mShiftY, mScreenWidth + -(int)mShiftX, mScreenHeight + -(int)mShiftY);
        //Log.i("lazer", "bounds set: "+ rect.left  + " " + rect.top + " " + rect.right + " " + rect.bottom);

        int[][][][] rendered = mController.getRenderedArray();

        int width_sq = mController.getmWidthInSquares();
        int height_sq = mController.getHeightInSquares();

        Canvas local = new Canvas(mBitmap);

        for (int i = 0; i < height_sq; i++)
        {
            for (int j = 0; j < width_sq; j++)
            {
                for (int k = 0; k < 2; k++)
                {
                    int startx = rendered[i][j][k][0];
                    int starty = rendered[i][j][k][1];
                    int endx = rendered[i][j][k][2];
                    int endy = rendered[i][j][k][3];
                    int color = rendered[i][j][k][4];

                    mDummyPaint.setColor(color);

                    //Log.i("lazer", startx + " " + starty + " " + endx + " " + endy + " " + color);

                    local.drawLine(startx, starty, endx, endy, mDummyPaint);
                }
            }
        }

        int bitmap_width = mWidth > mScreenWidth ? mScreenWidth : mWidth;
        int bitmap_height = mHeight > mScreenHeight ? mScreenHeight : mHeight;

        // FIXME: crop ONLY when screen is smaller than bitmap
        Bitmap crop = Bitmap.createBitmap(mBitmap, rect.left, rect.top, bitmap_width, bitmap_height);
        canvas.drawBitmap(crop, rect.left, rect.top, null);
        crop.recycle();

        canvas.restore();
    }
}
