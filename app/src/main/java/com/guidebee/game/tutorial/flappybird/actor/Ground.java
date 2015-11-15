package com.guidebee.game.tutorial.flappybird.actor;

import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;

import static com.guidebee.game.GameEngine.assetManager;


public class Ground  extends Actor {

    private final TextureRegion groundTextRegion;
    private final int moveStep;
    private int offset;

    public Ground(){
        super("Ground");
        TextureAtlas textureAtlas=assetManager.get("flappybird.atlas",TextureAtlas.class);
        groundTextRegion =textureAtlas.findRegion("ground");
        setSize(Configuration.SCREEN_WIDTH,
                groundTextRegion.getRegionHeight());
        setPosition(0, 0);
        moveStep=Configuration.MOVE_STEP;
        offset=0;

    }

    @Override
    public void draw (Batch batch, float parentAlpha){
        int backWidth= groundTextRegion.getRegionWidth();
        int size=Configuration.SCREEN_WIDTH/backWidth;
        if(size*backWidth<Configuration.SCREEN_WIDTH) size++;
        offset+=moveStep;
        offset %= backWidth;


        for(int i=-1;i<size;i++) {
            batch.draw(groundTextRegion,offset + i*backWidth,0);
        }

    }
}
