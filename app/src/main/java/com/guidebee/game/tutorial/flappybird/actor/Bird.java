package com.guidebee.game.tutorial.flappybird.actor;



import com.guidebee.game.graphics.Animation;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;
import com.guidebee.math.Vector3;
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

    private static final int GRAVITY = -100;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;


    public Bird(){
        super("Bird");

        TextureAtlas textureAtlas=assetManager.get("flappybird.atlas",TextureAtlas.class);
        birdTextRegion = textureAtlas.findRegion("birdanimation");
        Array<TextureRegion> keyFrames=new Array<TextureRegion>();
        for(int i=0;i<SPRITE_FRAME_SIZE;i++){
            TextureRegion textureRegion=new TextureRegion(birdTextRegion,i*SPRITE_WIDTH,0,
                    SPRITE_WIDTH, SPRITE_HEIGHT);
            keyFrames.add(textureRegion);
        }
        flyAnimation=new Animation(tick,keyFrames);
        setTextureRegion(flyAnimation.getKeyFrame(0));
        setPosition(Configuration.BIRD_START_X, Configuration.BIRD_START_Y);
        position = new Vector3(Configuration.BIRD_START_X, Configuration.BIRD_START_Y, 0);
        velocity = new Vector3(0, 0, 0);


    }


    @Override
    public void act(float delta){
        elapsedTime += graphics.getDeltaTime();

        setTextureRegion(flyAnimation.getKeyFrame(elapsedTime, true));
        velocity.add(0, GRAVITY, 0);
        velocity.scl(delta);
        position.add(MOVEMENT * delta, velocity.y, 0);
        if(input.isTouched()) {
            rotateBy(30*delta);
            velocity.y = 250;

        }else{
            rotateBy(-20*delta);

        }
        setY(position.y);


        float currentAngle=getRotation();
        if(currentAngle<-90 && currentAngle<0) setRotation(-90);
        if(currentAngle>90 && currentAngle>0) setRotation(90);
        if(position.y< Configuration.groundHeight){
            setY(Configuration.groundHeight);
            setRotation(0);
            setTextureRegion(flyAnimation.getKeyFrame(0));
        }
        if(position.y>Configuration.SCREEN_HEIGHT){
            setY(Configuration.groundHeight);
            setRotation(0);
            setTextureRegion(flyAnimation.getKeyFrame(0));
            position.y=Configuration.groundHeight;
        }




    }



}
