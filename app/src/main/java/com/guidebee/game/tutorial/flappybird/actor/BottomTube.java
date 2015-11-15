package com.guidebee.game.tutorial.flappybird.actor;

import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.tutorial.flappybird.Configuration;

import static com.guidebee.game.GameEngine.assetManager;


public class BottomTube extends Tube{

    private final TextureRegion bottomTubeTextRegion;

    public BottomTube(){
        this(0,0);
    }

    public BottomTube(int x, int height) {
        super("BottomTube",x,height);
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas", TextureAtlas.class);
        bottomTubeTextRegion =textureAtlas.findRegion("bottomtube");

        setSize(bottomTubeTextRegion.getRegionWidth(), tubeHeight);
    }


    @Override
    public void draw (Batch batch, float parentAlpha){
        batch.draw(bottomTubeTextRegion,posX, Configuration.groundHeight,
                bottomTubeTextRegion.getRegionWidth(),tubeHeight);
    }


}
