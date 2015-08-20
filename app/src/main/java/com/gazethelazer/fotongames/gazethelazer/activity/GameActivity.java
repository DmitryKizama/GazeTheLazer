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

    GameMapView mGameMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ControllerDraw example_controller = new ControllerDraw();
        MatrixAPI example_matrix = new MatrixAPI(Final.NUMBER_OF_SQUARE_HEIGHTS, Final.NUMBER_OF_SQUARE_WEIGHTS);

        example_controller.setSquareSize(Final.SIZE_OF_SQUARE);

        example_controller.render(example_matrix);

        ControllerGame game = new ControllerGame(example_matrix);


        setContentView(R.layout.activity_game);

        mGameMapView = (GameMapView) findViewById(R.id.gameMapView);
        mGameMapView.setControllerDraw(example_controller);
        mGameMapView.setControllerGame(game);

        mGameMapView.addButton((BootstrapButton) findViewById(R.id.arrowButton1));
        mGameMapView.addButton((BootstrapButton) findViewById(R.id.arrowButton2));
    }

    public void onArrowClick(View v)
    {
        mGameMapView.onArrowClick(v);
    }
}