package com.guidebee.game.tutorial.flappybird.actor;

import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;

import static com.guidebee.game.GameEngine.assetManager;


public class BottomTube extends Actor {

    private final TextureRegion buttomTubeTextRegion;

    private int tubeHeight;
    private int posX;
    private int groundHeight;

    public BottomTube(int x, int height) {

        super("BottomTube");
        tubeHeight=height;
        posX=x;
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas", TextureAtlas.class);
        buttomTubeTextRegion =textureAtlas.findRegion("bottomtube");
        TextureRegion groundTextRegion =textureAtlas.findRegion("ground");
        groundHeight=groundTextRegion.getRegionHeight();
        setSize(buttomTubeTextRegion.getRegionWidth(),tubeHeight);
    }


    @Override
    public void draw (Batch batch, float parentAlpha){

        batch.draw(buttomTubeTextRegion,posX,groundHeight,
                buttomTubeTextRegion.getRegionWidth(),tubeHeight);
    }
}
