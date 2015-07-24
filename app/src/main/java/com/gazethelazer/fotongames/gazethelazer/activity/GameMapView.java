package com.gazethelazer.fotongames.gazethelazer.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GameMapView extends View {
    static final int INVALID_POINTER_ID = -1;

    float mPreviousX;
    float mPreviousY;
    int mCurPointerId = INVALID_POINTER_ID;

    float mShiftX;
    float mShiftY;

    Drawable mPlain;

    int mWidth;
    int mHeight;

    int mScreenWidth;
    int mScreenHeight;

    GameMapView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        mWidth = 2000;
        mHeight = 3000;
        Bitmap bitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);

        Canvas c = new Canvas(bitmap);
        Paint p = new Paint();

        p.setColor(Color.RED);
        p.setStyle(Paint.Style.FILL);

        c.drawColor(Color.WHITE);
        c.drawRect(0, 0, 50, 50, p);
        c.drawRect(mWidth - 50, 0, mWidth, 50, p);
        c.drawRect(0, mHeight - 50, 50, mHeight, p);
        c.drawRect(mWidth - 50, mHeight - 50, mWidth, mHeight, p);

        mPlain = new BitmapDrawable(context.getResources(), bitmap);
        mPlain.setBounds(0, 0, mPlain.getIntrinsicWidth(), mPlain.getIntrinsicHeight());
    }

    GameMapView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    GameMapView(Context context) {
        this(context, null, 0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mScreenWidth == 0 || mScreenHeight == 0) {
            mScreenWidth = getWidth();
            mScreenHeight = getHeight();
        }

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

                //Screed border: X
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
                }

                //Screed border: Y
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
                }

                invalidate();

                break;

            case MotionEvent.ACTION_UP:
                mCurPointerId = INVALID_POINTER_ID;
                break;

            case MotionEvent.ACTION_CANCEL:
                mCurPointerId = INVALID_POINTER_ID;
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
        mPlain.draw(canvas);

        canvas.restore();
    }
}
