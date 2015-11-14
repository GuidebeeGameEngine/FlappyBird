package com.guidebee.game.tutorial.flappybird.actor;



import com.guidebee.game.graphics.Animation;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.utils.collections.Array;

import static com.guidebee.game.GameEngine.*;


public class Bird extends Actor {

    private final Animation flyAnimation;
    private final TextureRegion birdTextRegion;

    private final int SPRITE_WIDTH=34;
    private final int SPRITE_HEIGHT=24;
    private final int SPRITE_FRAME_SIZE=3;
    private float tick=0.05f;
    private float elapsedTime = 0;

    public Bird(){
        super("Bird");

        TextureAtlas textureAtlas=assetManager.get("flappybird.atlas",TextureAtlas.class);
        birdTextRegion = textureAtlas.findRegion("birdanimation");
        Array<TextureRegion> keyFrames=new Array<TextureRegion>();
        for(int i=0;i<SPRITE_FRAME_SIZE;i++){
            TextureRegion textureRegion=new TextureRegion(birdTextRegion,i*SPRITE_WIDTH,0,
                    SPRITE_WIDTH,SPRITE_HEIGHT);
            keyFrames.add(textureRegion);
        }
        flyAnimation=new Animation(tick,keyFrames);
        setTextureRegion(flyAnimation.getKeyFrame(0));
        setPosition(400, 240);



    }

    @Override
    public void act(float delta){
        elapsedTime += graphics.getDeltaTime();
        setTextureRegion(flyAnimation.getKeyFrame(elapsedTime, true));

    }



}
