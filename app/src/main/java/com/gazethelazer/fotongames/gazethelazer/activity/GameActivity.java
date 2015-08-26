package com.gazethelazer.fotongames.gazethelazer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.gazethelazer.fotongames.gazethelazer.R;
import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerDraw;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerGame;
import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;
import com.gazethelazer.fotongames.gazethelazer.view.GameMapView;

public class GameActivity extends Activity {

    static boolean mGameStarted = false;

    GameMapView mGameMapView;
    static ControllerDraw mControllerDraw;
    static ControllerGame mControllerGame;
    static MatrixAPI mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (mGameStarted == false)
        {
            mControllerDraw = new ControllerDraw();
            mModel = new MatrixAPI(Final.NUMBER_OF_SQUARE_HEIGHTS, Final.NUMBER_OF_SQUARE_WEIGHTS);

            mControllerDraw.setSquareSize(Final.SIZE_OF_SQUARE);
            mControllerDraw.render(mModel);

            mControllerGame = new ControllerGame(mModel);
            mControllerGame.setControllerDraw(mControllerDraw);

            mGameStarted = true;
        }


        setContentView(R.layout.activity_game);

        mGameMapView = (GameMapView) findViewById(R.id.gameMapView);
        mGameMapView.setControllerDraw(mControllerDraw);
        mGameMapView.setControllerGame(mControllerGame);

        mGameMapView.addButton((BootstrapButton) findViewById(R.id.arrowButton1));
        mGameMapView.addButton((BootstrapButton) findViewById(R.id.arrowButton2));
    }

    public void onArrowClick(View v)
    {
        mGameMapView.onArrowClick(v);
    }

    @Override
    public void onStop()
    {
        super.onStop();

        mGameMapView.cleanUp();
    }
}
