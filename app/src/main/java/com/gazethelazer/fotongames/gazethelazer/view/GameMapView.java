package com.gazethelazer.fotongames.gazethelazer.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerDraw;
import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;

import java.util.Calendar;

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

    long mLongClickDuration = 500; // ms
    long lastClick;
    boolean traceLongClick = false;

    ControllerDraw mControllerDraw;
    ControllerGame mControllerGame

    Paint mDummyPaint = new Paint();

    public GameMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mDummyPaint.setStyle(Paint.Style.FILL);
    }

    public GameMapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GameMapView(Context context) {
        this(context, null, 0);
    }

    public void setControllerDraw(ControllerDraw controllerDraw) {
        mControllerDraw = controllerDraw;
    }

    public void setControllerGame(ControllerGame controllergame)
    {
        mControllerGame = controllerGame;
    }

    public void generateBlankBitmap() {
        mHeight = mControllerDraw.getHeight();
        mWidth = mControllerDraw.getWidth();

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawColor(Color.WHITE);
    }

    public void createChooser(int sq_x, int sq_y, int[] moves)
    {
        BootstrapButton button = new BootstrapButton(getContext());
        button.setLeftIcon("fa-arrow-right");
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
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

                Log.i("lazer", "" + mControllerDraw.getSquareCoordsX(ev.getX() + -mShiftX));
                Log.i("lazer", "" + mControllerDraw.getSquareCoordsY(ev.getY() + -mShiftY));

                if (traceLongClick == false)
                {
                    traceLongClick = true;
                    lastClick = Calendar.getInstance().getTimeInMillis();
                }

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

                traceLongClick = false;

                break;

            case MotionEvent.ACTION_UP:
                mCurPointerId = Final.INVALID_POINTER_ID;

                if (traceLongClick)
                {
                    if (Calendar.getInstance().getTimeInMillis() - lastClick >= mLongClickDuration)
                    {
                        traceLongClick = false;

                        int sq_x = mControllerDraw.getSquareCoordsX(ev.getX() + -mShiftX);
                        int sq_y = mControllerDraw.getSquareCoordsY(ev.getY() + -mShiftY);

                        int moves[] = mControllerGame.checkForAwailableEdges(sq_x, sq_y);

                        int sum = 0;
                        for (int i = 0; i < 4; i++)
                            sum += moves[i];

                        if (sum == 1)
                        {
                            mControllerGame.turn(sq_x, sq_y, mController.getEdgeSingularMove(moves));
                        }
                        else if (sum > 1)
                        {
                            createChooser(sq_x, sq_y, moves);
                        }
                    }
                }

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

//        Log.i("lazer", "bounds set: "+ rect.left  + " " + rect.top + " " + rect.right + " " + rect.bottom);

        int[][][][] rendered = mControllerDraw.getRenderedArray();

        int width_sq = mControllerDraw.getmWidthInSquares();
        int height_sq = mControllerDraw.getHeightInSquares();

        Canvas local = new Canvas(mBitmap);

        for (int i = 0; i < height_sq; i++) {
            for (int j = 0; j < width_sq; j++) {
                for (int k = 0; k < 2; k++) {
                    int startx = rendered[i][j][k][0];
                    int starty = rendered[i][j][k][1];
                    int endx = rendered[i][j][k][2];
                    int endy = rendered[i][j][k][3];
                    int color = rendered[i][j][k][4];

                    mDummyPaint.setColor(color);
                    mDummyPaint.setStrokeWidth(10);

//                    Log.i("lazer", startx + " " + starty + " " + endx + " " + endy + " " + color);

                    local.drawLine(startx, starty, endx, endy, mDummyPaint);
                }
            }
        }

        int left = -(int) mShiftX;
        int top = -(int) mShiftY;

        if (mWidth > mScreenWidth || mHeight > mScreenHeight) {
            int bitmap_width = mWidth > mScreenWidth ? mScreenWidth : mWidth;
            int bitmap_height = mHeight > mScreenHeight ? mScreenHeight : mHeight;

            Bitmap crop = Bitmap.createBitmap(mBitmap, left, top, bitmap_width, bitmap_height);
            canvas.drawBitmap(crop, left, top, null);
            crop.recycle();
        } else {
            canvas.drawBitmap(mBitmap, left, top, null);
        }

        canvas.restore();
    }
}
