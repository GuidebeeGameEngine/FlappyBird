package com.guidebee.game.tutorial.flappybird.actor;

import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;

import static com.guidebee.game.GameEngine.assetManager;


public class Background extends Actor {

    private final TextureRegion backgroundTextRegion;
    private final int moveStep=2;
    private int offset;

    public Background(){
        super("Background");
        TextureAtlas textureAtlas=assetManager.get("flappybird.atlas",TextureAtlas.class);
        backgroundTextRegion=textureAtlas.findRegion("bg");
        setSize(Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT);

    }

    @Override
    public void draw (Batch batch, float parentAlpha){
        int backWidth=backgroundTextRegion.getRegionWidth();
        int size=Configuration.SCREEN_WIDTH/backWidth;
        if(size*backWidth<Configuration.SCREEN_WIDTH) size++;
        offset+=moveStep;
        offset %= backWidth;
        for(int i=0;i<size+1;i++) {
            batch.draw(backgroundTextRegion,-offset +i*backWidth,0);
        }
    }
}
