package com.guidebee.game.tutorial.flappybird;

import android.os.Bundle;

import com.guidebee.game.Configuration;
import com.guidebee.game.activity.GameActivity;

/**
 * Created by James on 14/11/2015.
 */
public class FlappyBirdGameActivity extends GameActivity{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = new Configuration();

        config.useAccelerometer = false;
        config.useCompass = false;

        initialize(new FlappyBirdGamePlay(), config);
    }
}
