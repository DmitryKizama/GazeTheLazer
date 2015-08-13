package com.gazethelazer.fotongames.gazethelazer.activity;

import android.app.Activity;
import android.os.Bundle;

import com.gazethelazer.fotongames.gazethelazer.api.MatrixAPI;
import com.gazethelazer.fotongames.gazethelazer.controller.ControllerDraw;
import com.gazethelazer.fotongames.gazethelazer.static_and_final_variables.Final;
import com.gazethelazer.fotongames.gazethelazer.view.GameMapView;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ControllerDraw example_controller = new ControllerDraw();
        MatrixAPI example_matrix = new MatrixAPI(Final.NUMBER_OF_SQUARE_HEIGHTS, Final.NUMBER_OF_SQUARE_WEIGHTS);

        example_controller.setSquareSize(Final.SIZE_OF_SQUARE);

        example_controller.render(example_matrix);

        GameMapView gameMapView = new GameMapView(this);
        gameMapView.setControllerDraw(example_controller);

        setContentView(gameMapView);
    }
}
