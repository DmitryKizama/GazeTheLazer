package com.gazethelazer.fotongames.gazethelazer.controller;

import android.animation.Animator;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.gazethelazer.fotongames.gazethelazer.listeners.PureAnimatorListener;
import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;

public class ControllerOfSettings {

    public void move(EditText ed, SeekBar sOne, SeekBar sTwo, TextView tvOne, TextView tvTwo) {
        ed.animate().translationX(Final.MOVINGPLUS).alpha(Final.VISIBILITYGONE).setListener(new PureAnimatorListener() {
        }).setDuration(1).start();
        sOne.animate().translationX(Final.MOVINGPLUS).alpha(Final.VISIBILITYGONE).setListener(new PureAnimatorListener() {
        }).setDuration(1).start();
        sTwo.animate().translationX(Final.MOVINGPLUS).alpha(Final.VISIBILITYGONE).setListener(new PureAnimatorListener() {
        }).setDuration(1).start();
        tvOne.animate().translationX(Final.MOVINGPLUS).alpha(Final.VISIBILITYGONE).setListener(new PureAnimatorListener() {
        }).setDuration(1).start();
        tvTwo.animate().translationX(Final.MOVINGPLUS).alpha(Final.VISIBILITYGONE).setListener(new PureAnimatorListener() {
        }).setDuration(1).start();
    }

    public void appearOrDisappearView(final View v, final int move, final int visib) {
        v.animate().translationX(move).alpha(visib).setListener(new PureAnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (visib == Final.VISIBILITYGONE) {
                    v.setVisibility(View.GONE);
                }
            }
        }).setDuration(Final.DURATION).start();
    }

}
