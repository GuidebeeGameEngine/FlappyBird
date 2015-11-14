package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.GamePlay;

import static com.guidebee.game.GameEngine.assetManager;


public class FlappyBirdGamePlay extends GamePlay {
    @Override
    public void create() {
        loadAssets();
        FlappyBirdScene screen=new FlappyBirdScene();
        setScreen(screen);
    }

    @Override
    public void dispose(){
        assetManager.dispose();
    }

    private void loadAssets(){


        assetManager.finishLoading();
    }
}
