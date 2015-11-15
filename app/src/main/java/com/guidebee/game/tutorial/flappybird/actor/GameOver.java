package com.guidebee.game.tutorial.flappybird.actor;


import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;

import static com.guidebee.game.GameEngine.assetManager;

public class GameOver extends Actor {

    public GameOver(){
        super("StartButton");

        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas", TextureAtlas.class);
        TextureRegion buttonTextRegion=textureAtlas.findRegion("gameover");
        setTextureRegion(buttonTextRegion);
        setPosition((Configuration.SCREEN_WIDTH - buttonTextRegion.getRegionWidth()) / 2,
                (Configuration.SCREEN_HEIGHT ) / 2+buttonTextRegion.getRegionHeight());
        setVisible(false);
    }
}
