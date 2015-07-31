package com.gazethelazer.fotongames.gazethelazer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerDraw;
import com.gazethelazer.fotongames.gazethelazer.view.GameMapView;

public class GameActivity extends Activity {

    MatrixAPI mModel;
    ControllerDraw mDraw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mModel = new MatrixAPI(20, 20);
        mDraw = new ControllerDraw();

        mDraw.setSquareSize(50); //FIXME: magic number

        mDraw.render(mModel);

        GameMapView gameMapView = new GameMapView(this);
        gameMapView.setControllerDraw(mDraw);

        setContentView(gameMapView);
    }
}
