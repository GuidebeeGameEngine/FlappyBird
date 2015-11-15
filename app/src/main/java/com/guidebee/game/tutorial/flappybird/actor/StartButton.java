package com.guidebee.game.tutorial.flappybird.actor;

import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;
import com.guidebee.game.tutorial.flappybird.FlappyBirdStage;
import com.guidebee.math.Vector3;

import static com.guidebee.game.GameEngine.*;


public class StartButton extends Actor{

    private final FlappyBirdStage flappyBirdStage;
    public StartButton(FlappyBirdStage stage){
        super("StartButton");
        flappyBirdStage=stage;
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas", TextureAtlas.class);
        TextureRegion buttonTextRegion=textureAtlas.findRegion("playbtn");
        setTextureRegion(buttonTextRegion);
        setPosition((Configuration.SCREEN_WIDTH - buttonTextRegion.getRegionWidth()) / 2,
                (Configuration.SCREEN_HEIGHT - buttonTextRegion.getRegionHeight()) / 2);

    }


    @Override
    public void act(float delta) {
        if(input.isTouched()){
            Vector3 touchPos=new Vector3();
            touchPos.set(input.getX(), input.getY(), 0);
            getStage().getCamera().unproject(touchPos);
            if(getBoundingAABB().contains(touchPos.x,touchPos.y)){
                flappyBirdStage.removeStartButton();
                flappyBirdStage.startGame();
            }
        }
    }
}
