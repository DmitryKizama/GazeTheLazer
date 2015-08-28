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

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerDraw;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerGame;
import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;

import java.util.ArrayList;
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

    long mLongClickDuration = Final.LONG_CLICK_DURATION; // ms
    long lastClick;
    boolean traceLongClick = false;

    ControllerDraw mControllerDraw;
    ControllerGame mControllerGame;

    Paint mDummyPaint = new Paint();

    ArrayList<BootstrapButton> mButtons = new ArrayList<BootstrapButton>();

    float mLastTouchX;
    float mLastTouchY;

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

    public void setControllerGame(ControllerGame controllergame) {
        mControllerGame = controllergame;
    }

    public void cleanUp()
    {
        mControllerDraw = null;
        mControllerGame = null;
        mBitmap.recycle();
    }

    public void generateBlankBitmap() {
        mHeight = mControllerDraw.getHeight();
        mWidth = mControllerDraw.getWidth();

        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(mBitmap);
        canvas.drawColor(Color.WHITE);
    }

    public void createChooser(float x, float y, int[] moves) {
        BootstrapButton first = mButtons.get(0);
        BootstrapButton second = mButtons.get(1);

        first.setVisibility(VISIBLE);
        second.setVisibility(VISIBLE);

        float dragx = 0, dragy = 0;
        float shift = Final.BUTTON_SHIFT;
        float rotate_f = 0, rotate_s = 0;

        if (moves[0] == 1)
        {
            dragy = shift;
        }
        else if (moves[2] == 1)
        {
            dragy = -shift;
            rotate_f = 180;
        }

        if (moves[1] == 1)
        {
            dragx = shift;
            rotate_s = 90;
        }
        else if (moves[3] == 1)
        {
            dragx = -shift;
            rotate_s = 270;
        }

        first.setRotation(rotate_f);
        first.setX(x - first.getWidth() / 2);
        first.setY(y - first.getHeight() / 2 - dragy);

        second.setX(x - first.getWidth() / 2 + dragx);
        second.setY(y - first.getHeight() / 2);
        second.setRotation(rotate_s);
    }

    public void addButton(BootstrapButton b)
    {
        mButtons.add(b);
    }

    public void hideButtons()
    {
        for (BootstrapButton b : mButtons)
        {
            b.setVisibility(INVISIBLE);
        }
    }

    public void onArrowClick(View v)
    {
        int r = (int) v.getRotation();
        int axisx = 0, axisy = 0;

        switch (r)
        {
            case 0:
                axisy = 1;
                break;
            case 180:
                axisy = -1;
                break;
            case 90:
                axisx = 1;
                break;
            case 270:
                axisx = -1;
                break;
        }

        int x = mControllerDraw.getSquareCoordsX(mLastTouchX + -mShiftX);
        int y = mControllerDraw.getSquareCoordsY(mLastTouchY + -mShiftY);

        mControllerGame.turn(x, y, axisx, axisy);
        hideButtons();
        invalidate();
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

                if (traceLongClick == false) {
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

                if (Math.abs(relshiftX) > mControllerDraw.getSquareSize()/3
                        || Math.abs(relshiftY) > mControllerDraw.getSquareSize()/3)
                {
                    traceLongClick = false;
                }

                if (Math.abs(mLastTouchX - ev.getX(index)) > mControllerDraw.getSquareSize()
                        || Math.abs(mLastTouchY - ev.getY(index)) > mControllerDraw.getSquareSize())
                {
                    hideButtons();
                }

                break;

            case MotionEvent.ACTION_UP:
                mCurPointerId = Final.INVALID_POINTER_ID;

                if (traceLongClick) {
                    if (Calendar.getInstance().getTimeInMillis() - lastClick >= mLongClickDuration) {
                        traceLongClick = false;

                        //TODO: move this to ControllerGame
                        int sq_x = mControllerDraw.getSquareCoordsX(ev.getX() + -mShiftX);
                        int sq_y = mControllerDraw.getSquareCoordsY(ev.getY() + -mShiftY);

                        int moves[] = mControllerGame.checkForAwailableEdges(sq_x, sq_y);

                        int sum = 0;
                        for (int i = 0; i < 4; i++)
                            sum += moves[i];

                        if (sum == 1)
                        {
                            int[] axis = mControllerGame.getEdgeSingularMove(moves);

                            mControllerGame.turn(sq_x, sq_y, axis[0], axis[1]);
                            invalidate();
                        }
                        else if (sum > 1)
                        {
                            mLastTouchX = ev.getX();
                            mLastTouchY = ev.getY();

                            createChooser(ev.getX(), ev.getY(), moves);
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
//                    if (color == Color.GRAY) {
//                        mDummyPaint.setAlpha(50);
//                    }
//                    Log.d("Color", "ALPHA = " + mDummyPaint.getAlpha() + " COLOR =  " + color);
//                    Log.i("lazer", startx + " " + starty + " " + endx + " " + endy + " " + color);

                    local.drawLine(startx, starty, endx, endy, mDummyPaint);
//                    mDummyPaint.setAlpha(255);
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
