package com.gazethelazer.fotongames.gazethelazer.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gazethelazer.fotongames.gazethelazer.R;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerOfSettings;
import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;

public class MainPage extends Activity {

    private ControllerOfSettings controllerrSettings = new ControllerOfSettings();
    private TextView imageSettings, imageAboutAutors, tvSounds, tvMusic;
    private EditText edName;
    private SeekBar seekBarSounds, seekBarMusic;
    private Animation rotate_and_scale, rotate_minus;
    private BootstrapButton btnSingle, btnMulty, btnInfo;
    private PopupWindow pWindow;
    private boolean CONTERSETTINGSCLICL = true;
    private boolean ISSHOWING = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        rotate_and_scale = AnimationUtils.loadAnimation(this, R.anim.rotate_and_scale);
        rotate_minus = AnimationUtils.loadAnimation(this, R.anim.ratate_and_scale_minus);

        seekBarMusic = (SeekBar) findViewById(R.id.seekBarMusic);
        seekBarSounds = (SeekBar) findViewById(R.id.seekBarSounds);

        edName = (EditText) findViewById(R.id.edName);

        btnSingle = (BootstrapButton) findViewById(R.id.btnSingle);
        btnMulty = (BootstrapButton) findViewById(R.id.btnMulty);
        btnInfo = (BootstrapButton) findViewById(R.id.btnInfo);

        imageSettings = (TextView) findViewById(R.id.settingsImage);
        imageAboutAutors = (TextView) findViewById(R.id.infoAboutAutorsImage);
        tvMusic = (TextView) findViewById(R.id.tvMusic);
        tvSounds = (TextView) findViewById(R.id.tvSounds);

        LayoutInflater layoutInflater = (LayoutInflater) getApplication()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View popupView = layoutInflater.inflate(R.layout.popup, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, AbsListView.LayoutParams.WRAP_CONTENT,
                AbsListView.LayoutParams.WRAP_CONTENT);
        this.pWindow = popupWindow;

        //move settings menu right
        controllerrSettings.move(edName, seekBarMusic, seekBarSounds, tvMusic, tvSounds);

        imageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CONTERSETTINGSCLICL) {
                    imageSettings.startAnimation(rotate_minus);
                    controllerrSettings.appearOrDisappearView(btnInfo, Final.MOVINGMINUS, Final.VISIBILITYGONE);
                    controllerrSettings.appearOrDisappearView(btnSingle, Final.MOVINGMINUS, Final.VISIBILITYGONE);
                    controllerrSettings.appearOrDisappearView(btnMulty, Final.MOVINGMINUS, Final.VISIBILITYGONE);

                    controllerrSettings.appearOrDisappearView(tvMusic, Final.MOVINGZERO, Final.VISIBILITYVISIBLE);
                    controllerrSettings.appearOrDisappearView(tvSounds, Final.MOVINGZERO, Final.VISIBILITYVISIBLE);
                    controllerrSettings.appearOrDisappearView(edName, Final.MOVINGZERO, Final.VISIBILITYVISIBLE);
                    controllerrSettings.appearOrDisappearView(seekBarMusic, Final.MOVINGZERO, Final.VISIBILITYVISIBLE);
                    controllerrSettings.appearOrDisappearView(seekBarSounds, Final.MOVINGZERO, Final.VISIBILITYVISIBLE);

                    CONTERSETTINGSCLICL = false;
                } else {
                    imageSettings.startAnimation(rotate_and_scale);
                    controllerrSettings.appearOrDisappearView(btnInfo, Final.MOVINGZERO, Final.VISIBILITYVISIBLE);
                    controllerrSettings.appearOrDisappearView(btnSingle, Final.MOVINGZERO, Final.VISIBILITYVISIBLE);
                    controllerrSettings.appearOrDisappearView(btnMulty, Final.MOVINGZERO, Final.VISIBILITYVISIBLE);

                    controllerrSettings.appearOrDisappearView(tvMusic, Final.MOVINGPLUS, Final.VISIBILITYGONE);
                    controllerrSettings.appearOrDisappearView(tvSounds, Final.MOVINGPLUS, Final.VISIBILITYGONE);
                    controllerrSettings.appearOrDisappearView(edName, Final.MOVINGPLUS, Final.VISIBILITYGONE);
                    controllerrSettings.appearOrDisappearView(seekBarMusic, Final.MOVINGPLUS, Final.VISIBILITYGONE);
                    controllerrSettings.appearOrDisappearView(seekBarSounds, Final.MOVINGPLUS, Final.VISIBILITYGONE);
                    CONTERSETTINGSCLICL = true;
                }

            }
        });

        imageAboutAutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                ISSHOWING = true;
                BootstrapButton btnAsshole = (BootstrapButton) popupView.findViewById(R.id.btnGiveMoney);
                BootstrapButton btnGiveMoney = (BootstrapButton) popupView.findViewById(R.id.btnGiveMoney);
                popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
            }
        });

        btnSingle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSingle = new Intent(MainPage.this, GameActivity.class);
                startActivity(intentSingle);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (ISSHOWING) {
            pWindow.dismiss();
            ISSHOWING = false;
        } else {
            new AlertDialog.Builder(this).setTitle("Really Exit?")
                    .setMessage("Are you sure you want to exit?").setNegativeButton(android.R.string.no, null)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            MainPage.super.onBackPressed();
                        }
                    }).create().show();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
