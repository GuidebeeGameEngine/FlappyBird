package com.guidebee.game.tutorial.flappybird.actor;

import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;

import org.w3c.dom.Text;

import static com.guidebee.game.GameEngine.assetManager;


public class Background extends Actor {

    private final TextureRegion backgroundTextRegion;
    private final TextureRegion skyTextureRegion;
    private final int moveStep=2;
    private int offset;


    public Background(){
        super("Background");
        TextureAtlas textureAtlas=assetManager.get("flappybird.atlas", TextureAtlas.class);
        backgroundTextRegion=textureAtlas.findRegion("bg");
        skyTextureRegion=textureAtlas.findRegion("sky");

        setSize(Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT);

    }

    @Override
    public void draw (Batch batch, float parentAlpha){
        int backWidth=backgroundTextRegion.getRegionWidth();
        int skyHeight=skyTextureRegion.getRegionHeight();
        int widthSize=Configuration.SCREEN_WIDTH/backWidth;
        int remainSize=Configuration.SCREEN_WIDTH-Configuration.groundHeight
                -backgroundTextRegion.getRegionHeight();
        int skySize=0;
        if(remainSize>0){
            skySize=remainSize/skyHeight;
            if(skySize*skyHeight<remainSize) skySize++;
        }
        if(widthSize*backWidth<Configuration.SCREEN_WIDTH) widthSize++;


        offset+=moveStep;
        offset %= backWidth;
        for(int i=0;i<widthSize+1;i++) {
            batch.draw(backgroundTextRegion,-offset +i*backWidth,Configuration.groundHeight);
            for(int j=0;j<skyHeight;j++){
                batch.draw(skyTextureRegion,-offset +i*backWidth,Configuration.groundHeight
                        +backgroundTextRegion.getRegionHeight()+j*skyHeight);
            }
        }
    }
}
