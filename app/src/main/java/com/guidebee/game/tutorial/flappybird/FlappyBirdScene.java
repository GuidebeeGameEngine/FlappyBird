package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.scene.Scene;
import static com.guidebee.game.GameEngine.*;


public class FlappyBirdScene extends Scene {

    @Override
    public void render(float delta) {
        graphics.clearScreen(0, 0, 0.2f, 1);
        super.render(delta);

    }
}
