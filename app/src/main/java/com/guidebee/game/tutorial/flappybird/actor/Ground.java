package com.guidebee.game.tutorial.flappybird.actor;

import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;
import com.guidebee.game.tutorial.flappybird.state.TubePosition;
import com.guidebee.utils.collections.Array;

import java.util.Random;

import static com.guidebee.game.GameEngine.assetManager;


public class Ground  extends Actor {

    private final TextureRegion groundTextRegion;
    private final TextureRegion bottomTubeTextRegion;
    private final TextureRegion topTubeTextRegion;

    private final int moveStep;
    private int offset;

    private Array<TubePosition> tubePositionArray=new Array<TubePosition>();
    private final Random random=new Random();

    public Ground(){
        super("Ground");
        TextureAtlas textureAtlas=assetManager.get("flappybird.atlas",TextureAtlas.class);
        groundTextRegion =textureAtlas.findRegion("ground");
        bottomTubeTextRegion =textureAtlas.findRegion("bottomtube");
        topTubeTextRegion=textureAtlas.findRegion("toptube");
        setSize(Configuration.SCREEN_WIDTH,
                groundTextRegion.getRegionHeight());
        setPosition(0, 0);
        moveStep=Configuration.MOVE_SPEED;
        offset=0;
        generateLevelData();

    }

    public void generateLevelData(){
        tubePositionArray.clear();
        int tubeLength=Configuration.SCREEN_HEIGHT-Configuration.groundHeight;
        for(int i=0;i<150;i++){
            TubePosition tubePosition=new TubePosition();
            tubePosition.posX=Configuration.SCREEN_WIDTH/2*i + random.nextInt(20);
            tubePosition.space=Configuration.SCREEN_WIDTH/2 + random.nextInt(100)+100;
            tubePosition.topTubeHeight=random.nextInt(tubeLength);
            if(tubePosition.topTubeHeight>topTubeTextRegion.getRegionHeight()){
                tubePosition.topTubeHeight=topTubeTextRegion.getRegionHeight();
            }
            tubePosition.buttomTubeHeight=tubeLength-tubePosition.topTubeHeight
                    -Configuration.MIN_GAP-random.nextInt(30);
            if(tubePosition.buttomTubeHeight<0) {
                tubePosition.buttomTubeHeight=10;
            }

            if(tubeLength-tubePosition.topTubeHeight-tubePosition.buttomTubeHeight
                    <Configuration.MIN_GAP){
                tubePosition.topTubeHeight=100;
                tubePosition.buttomTubeHeight=100;
            }
            tubePositionArray.add(tubePosition);
        }
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
        for(int i=0;i<tubePositionArray.size;i++){
            TubePosition tubePosition=tubePositionArray.get(i);
            if(tubePosition.posX>-bottomTubeTextRegion.getRegionWidth()
                    && tubePosition.posX<Configuration.SCREEN_WIDTH){

                batch.draw(bottomTubeTextRegion,tubePosition.posX, Configuration.groundHeight,
                        bottomTubeTextRegion.getRegionWidth(),
                        tubePosition.buttomTubeHeight);
                batch.draw(topTubeTextRegion,tubePosition.posX,
                        Configuration.SCREEN_HEIGHT-tubePosition.topTubeHeight);
            }
            tubePosition.posX-=moveStep;
        }

    }
}
