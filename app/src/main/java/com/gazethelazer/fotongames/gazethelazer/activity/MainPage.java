package com.gazethelazer.fotongames.gazethelazer.activity;

import android.animation.Animator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gazethelazer.fotongames.gazethelazer.R;
import com.gazethelazer.fotongames.gazethelazer.listeners.PureAnimatorListener;
import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;

public class MainPage extends AppCompatActivity {

    private TextView imageSettings, tvSounds, tvMusic;
    private EditText edName;
    private SeekBar seekBarSounds, seekBarMusic;
    private Animation rotate_and_scale, rotate_minus;
    private BootstrapButton btnSingle, btnMulty, btnInfo;
    private boolean CONTERSETTINGSCLICL = true;

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
        tvMusic = (TextView) findViewById(R.id.tvMusic);
        tvSounds = (TextView) findViewById(R.id.tvSounds);

        imageSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CONTERSETTINGSCLICL) {
//                    imageSettings.animate().rotation(360).scaleX(2).scaleY(2).setListener(new PureAnimatorListener() {
//                        @Override
//                        public void onAnimationEnd(Animator animation) {
//
//                        }
//                    }).setDuration(Final.DURATION).start();

                    imageSettings.startAnimation(rotate_minus);
                    appearOrDisappearBootstrapButton(btnInfo, Final.MOVINGMINUS, Final.VISIBILITYGONE);
                    appearOrDisappearBootstrapButton(btnSingle, Final.MOVINGMINUS, Final.VISIBILITYGONE);
                    appearOrDisappearBootstrapButton(btnMulty, Final.MOVINGMINUS, Final.VISIBILITYGONE);
                    CONTERSETTINGSCLICL = false;
                } else {
                    imageSettings.startAnimation(rotate_and_scale);
                    appearOrDisappearBootstrapButton(btnInfo, Final.MOVINGPLUS, Final.VISIBILITYVISIBLE);
                    appearOrDisappearBootstrapButton(btnSingle, Final.MOVINGPLUS, Final.VISIBILITYVISIBLE);
                    appearOrDisappearBootstrapButton(btnMulty, Final.MOVINGPLUS, Final.VISIBILITYVISIBLE);
                    CONTERSETTINGSCLICL = true;
                }


//                appearBootstrapButton(tvSounds);
            }
        });
    }

    private void appearOrDisappearBootstrapButton(final BootstrapButton btn, final int move, final int visib) {
        btn.animate().translationX(move).alpha(visib).setListener(new PureAnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                btn.setVisibility(View.VISIBLE);
            }

        }).setDuration(Final.DURATION).start();
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
