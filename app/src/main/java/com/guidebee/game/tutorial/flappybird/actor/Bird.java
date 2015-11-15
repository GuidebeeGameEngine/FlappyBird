package com.guidebee.game.tutorial.flappybird.actor;


import com.guidebee.game.audio.Sound;
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

    private final int SPRITE_WIDTH = 34;
    private final int SPRITE_HEIGHT = 24;
    private final int SPRITE_FRAME_SIZE = 3;
    private float tick = 0.05f;
    private float elapsedTime = 0;

    private static final int GRAVITY = -100;
    private static final int MOVEMENT = 100;

    private Vector3 position;
    private Vector3 velocity;
    private final Sound flapSound;
    private final Sound dieSound;
    private float nextSoundPeriod;
    private boolean isLive = true;


    public Bird() {
        super("Bird");

        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas", TextureAtlas.class);
        birdTextRegion = textureAtlas.findRegion("birdanimation");
        flapSound = assetManager.get("sfx_wing.ogg", Sound.class);
        dieSound = assetManager.get("sfx_die.ogg", Sound.class);
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        for (int i = 0; i < SPRITE_FRAME_SIZE; i++) {
            TextureRegion textureRegion = new TextureRegion(birdTextRegion, i * SPRITE_WIDTH, 0,
                    SPRITE_WIDTH, SPRITE_HEIGHT);
            keyFrames.add(textureRegion);
        }
        flyAnimation = new Animation(tick, keyFrames);
        setTextureRegion(flyAnimation.getKeyFrame(0));
        setPosition(Configuration.BIRD_START_X, Configuration.BIRD_START_Y);
        position = new Vector3(Configuration.BIRD_START_X, Configuration.BIRD_START_Y, 0);
        velocity = new Vector3(0, 0, 0);


    }

    public void reset(){
        setPosition(Configuration.BIRD_START_X, Configuration.BIRD_START_Y);
        position.set(Configuration.BIRD_START_X, Configuration.BIRD_START_Y, 0);
        velocity.set(0,0,0);
        setRotation(0);
        isLive=true;


    }

    public boolean isOutside() {
        return (position.y <= Configuration.groundHeight
                || position.y >= Configuration.SCREEN_HEIGHT);
    }

    public boolean isLive(){
        return isLive;
    }

    public void killBird() {
        isLive = false;
        setRotation(180);
        dieSound.play();
        setTextureRegion(flyAnimation.getKeyFrame(0));
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    @Override
    public void act(float delta) {

        elapsedTime += graphics.getDeltaTime();
        nextSoundPeriod += graphics.getDeltaTime();
        velocity.add(0, GRAVITY, 0);
        velocity.scl(delta);
        position.add(MOVEMENT * delta, velocity.y, 0);
        if(isLive) {
            setTextureRegion(flyAnimation.getKeyFrame(elapsedTime, true));
            if (input.isTouched()) {
                rotateBy(30 * delta);
                velocity.y = 250;
                if (nextSoundPeriod > 0.3) {
                    flapSound.play();
                    nextSoundPeriod = 0;
                }

            } else {
                rotateBy(-20 * delta);

            }
            float currentAngle = getRotation();
            if (currentAngle < -90 && currentAngle < 0) setRotation(-90);
            if (currentAngle > 90 && currentAngle > 0) setRotation(90);
        }
        if(position.y<Configuration.groundHeight){
            position.y= Configuration.groundHeight;
        }
        setY(position.y);




    }


}
