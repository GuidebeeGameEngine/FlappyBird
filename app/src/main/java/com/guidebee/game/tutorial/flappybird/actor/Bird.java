/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
//--------------------------------- PACKAGE ------------------------------------
package com.guidebee.game.tutorial.flappybird.actor;

//--------------------------------- IMPORTS ------------------------------------
import com.guidebee.game.audio.Sound;
import com.guidebee.game.graphics.Animation;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;
import com.guidebee.math.Vector3;
import com.guidebee.utils.collections.Array;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;
import static com.guidebee.game.GameEngine.input;


//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * The bird actor.
 * @author James Shen <james.shen@guidebee.com>
 */
public class Bird extends Actor {

    private static final int GRAVITY = -100;
    private static final int MOVEMENT = 100;
    private Vector3 position;
    private Vector3 velocity;

    private float tick = 0.05f;
    private final Animation flyAnimation;
    private final TextureRegion birdTextRegion;

    private final int SPRITE_WIDTH = 34;
    private final int SPRITE_HEIGHT = 24;
    private final int SPRITE_FRAME_SIZE = 3;

    private final Sound flapSound;
    private final Sound dieSound;

    private float elapsedTime = 0;

    /**
     * the variable is used to avoid sound be played to frequently and
     * became sound like noises.
     */
    private float nextSoundPeriod;
    private boolean isLive = true;


    public Bird() {
        super("Bird");

        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas",
                TextureAtlas.class);
        birdTextRegion = textureAtlas.findRegion("birdanimation");
        flapSound = assetManager.get("sfx_wing.ogg", Sound.class);
        dieSound = assetManager.get("sfx_die.ogg", Sound.class);
        Array<TextureRegion> keyFrames = new Array<TextureRegion>();
        for (int i = 0; i < SPRITE_FRAME_SIZE; i++) {
            TextureRegion textureRegion = new TextureRegion(birdTextRegion,
                    i * SPRITE_WIDTH, 0,
                    SPRITE_WIDTH, SPRITE_HEIGHT);
            keyFrames.add(textureRegion);
        }
        flyAnimation = new Animation(tick, keyFrames);
        setTextureRegion(flyAnimation.getKeyFrame(0));
        setPosition(Configuration.BIRD_START_X,
                Configuration.BIRD_START_Y);
        position = new Vector3(Configuration.BIRD_START_X,
                Configuration.BIRD_START_Y, 0);
        velocity = new Vector3(0, 0, 0);


    }

    public void reset() {
        setPosition(Configuration.BIRD_START_X,
                Configuration.BIRD_START_Y);
        position.set(Configuration.BIRD_START_X,
                Configuration.BIRD_START_Y, 0);
        velocity.set(0, 0, 0);
        setRotation(0);
        isLive = true;


    }

    public boolean isOutside() {
        return (position.y <= Configuration.groundHeight
                || position.y >= Configuration.SCREEN_HEIGHT);
    }

    public boolean isLive() {
        return isLive;
    }

    public void setLive(boolean live) {
        isLive = live;
    }

    public void killBird() {
        isLive = false;
        setRotation(180);
        dieSound.play();
        setTextureRegion(flyAnimation.getKeyFrame(0));
    }

    @Override
    public void act(float delta) {

        elapsedTime += graphics.getDeltaTime();
        nextSoundPeriod += graphics.getDeltaTime();
        /**
         * calculate bird's new position --free fall.
         */
        velocity.add(0, GRAVITY, 0);
        velocity.scl(delta);
        position.add(MOVEMENT * delta, velocity.y, 0);
        if (isLive) {
            setTextureRegion(flyAnimation.getKeyFrame(elapsedTime, true));
            /**
             * handle touch event, move the bird upward a bit
             */
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

            /**
             * control the bird's turning angle.
             */
            float currentAngle = getRotation();
            if (currentAngle < -90 && currentAngle < 0) {
                setRotation(-90);
            }
            if (currentAngle > 90 && currentAngle > 0) {
                setRotation(90);
            }
        }

        /**
         * make sure the bird not to pass through the ground.
         */
        if (position.y < Configuration.groundHeight) {
            position.y = Configuration.groundHeight;
        }
        setY(position.y);

    }


}
