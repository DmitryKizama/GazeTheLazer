package com.gazethelazer.fotongames.gazethelazer.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerDraw;
import com.gazethelazer.fotongames.gazethelazer.view.GameMapView;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ControllerDraw example_controller = new ControllerDraw();
        MatrixAPI example_matrix = new MatrixAPI(20, 20);

        example_controller.setSquareSize(50); //FIXME: magic number

        example_controller.render(example_matrix);

        GameMapView gameMapView = new GameMapView(this);
        gameMapView.setController(example_controller);

        setContentView(gameMapView);
    }
}
