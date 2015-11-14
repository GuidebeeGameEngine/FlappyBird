package com.guidebee.game.tutorial.flappybird.actor;


import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;

import static com.guidebee.game.GameEngine.assetManager;

public class TopTube extends Actor {

    private final TextureRegion topTubeTextRegion;

    private int tubeHeight;
    private int posX;

    public TopTube(int x,int height) {

        super("TopTube");
        tubeHeight=height;
        posX=x;
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas", TextureAtlas.class);
        topTubeTextRegion=textureAtlas.findRegion("toptube");
        setSize(topTubeTextRegion.getRegionWidth(),tubeHeight);
    }


    @Override
    public void draw (Batch batch, float parentAlpha){
        batch.draw(topTubeTextRegion,posX,Configuration.SCREEN_HEIGHT-tubeHeight);
    }
}
