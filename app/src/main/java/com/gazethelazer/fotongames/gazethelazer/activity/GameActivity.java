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
        MatrixAPI example_matrix = new MatrixAPI();

        //example_matrix.setSize(20, 20); //FIXME: Implement this or different constructor in matrix
        example_controller.setSquareSize(50); //FIXME: don't make it less than 50

        example_controller.render(example_matrix);

        GameMapView gameMapView = new GameMapView(this);
        gameMapView.setController(example_controller);

        setContentView(gameMapView);
    }
}
