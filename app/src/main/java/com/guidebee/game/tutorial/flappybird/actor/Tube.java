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
import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;
import com.guidebee.game.tutorial.flappybird.state.TubePosition;
import com.guidebee.math.geometry.Rectangle;
import com.guidebee.utils.collections.Array;

import java.util.Random;

import static com.guidebee.game.GameEngine.assetManager;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Tube actor. The tube actor in charge of top and bottom tubes.
 * @author James Shen <james.shen@guidebee.com>
 */
public class Tube extends Actor {

    private final TextureRegion groundTextRegion;
    private final TextureRegion bottomTubeTextRegion;
    private final TextureRegion topTubeTextRegion;

    private final int moveStep;
    private final Random random = new Random();
    private final Rectangle topRect = new Rectangle();
    private final Rectangle bottomRect = new Rectangle();
    private final Rectangle scoreRect = new Rectangle();
    private final Sound hitSound;
    private final Sound pointSound;
    private int offset;
    /**
     * all tube positions.
     */
    private Array<TubePosition> tubePositionArray = new Array<TubePosition>();
    private boolean stopMoving = false;
    private int score = -1;

    public Tube() {
        super("Tube");
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas",
                TextureAtlas.class);
        groundTextRegion = textureAtlas.findRegion("ground");
        bottomTubeTextRegion = textureAtlas.findRegion("bottomtube");
        topTubeTextRegion = textureAtlas.findRegion("toptube");
        hitSound = assetManager.get("sfx_hit.ogg", Sound.class);
        pointSound = assetManager.get("sfx_point.ogg", Sound.class);
        setSize(Configuration.SCREEN_WIDTH,
                groundTextRegion.getRegionHeight());
        setPosition(0, 0);
        moveStep = Configuration.MOVE_SPEED;
        offset = 0;
        topRect.width = topTubeTextRegion.getRegionWidth();
        bottomRect.width = bottomTubeTextRegion.getRegionWidth();
        bottomRect.y = Configuration.groundHeight;
        scoreRect.width = topRect.width;
        scoreRect.y = bottomRect.y;
        scoreRect.height = Configuration.SCREEN_HEIGHT
                - Configuration.groundHeight;
        generateLevelData();

    }


    public int getScore() {
        return score + 1;
    }

    /**
     * random generate the tube positions.
     */
    public void generateLevelData() {
        tubePositionArray.clear();
        score = -1;
        int tubeLength = Configuration.SCREEN_HEIGHT
                - Configuration.groundHeight;
        for (int i = 0; i < 150; i++) {
            TubePosition tubePosition = new TubePosition();
            tubePosition.posX = Configuration.SCREEN_WIDTH / 2 * i
                    + random.nextInt(100) +
                    Configuration.SCREEN_WIDTH * 3;
            tubePosition.topTubeHeight = random.nextInt(tubeLength);
            if (tubePosition.topTubeHeight
                    > topTubeTextRegion.getRegionHeight()) {
                tubePosition.topTubeHeight
                        = topTubeTextRegion.getRegionHeight();
            }
            tubePosition.bottomTubeHeight = tubeLength
                    - tubePosition.topTubeHeight
                    - Configuration.MIN_GAP - random.nextInt(30);
            if (tubePosition.bottomTubeHeight < 0) {
                tubePosition.bottomTubeHeight = 10;
            }

            if (tubeLength - tubePosition.topTubeHeight
                    - tubePosition.bottomTubeHeight
                    < Configuration.MIN_GAP) {
                tubePosition.topTubeHeight = 100;
                tubePosition.bottomTubeHeight = 100;
            }
            tubePositionArray.add(tubePosition);
        }
    }


    public boolean isCollideWithTube(float x, float y) {
        for (int i = 0; i < tubePositionArray.size; i++) {
            TubePosition tubePosition = tubePositionArray.get(i);
            if (tubePosition.posX
                    > -bottomTubeTextRegion.getRegionWidth()
                    && tubePosition.posX
                    < Configuration.SCREEN_WIDTH) {

                topRect.x = tubePosition.posX;
                topRect.y = Configuration.SCREEN_HEIGHT
                        - tubePosition.topTubeHeight;
                topRect.height = tubePosition.topTubeHeight;

                bottomRect.x = tubePosition.posX;

                bottomRect.height = tubePosition.bottomTubeHeight;

                boolean collide = topRect.contains(x, y)
                        || bottomRect.contains(x, y);
                if (collide) {
                    hitSound.play();
                    return true;
                }

                scoreRect.x = tubePosition.posX;
                if (scoreRect.contains(x, y)) {
                    if (score < i) {
                        score = i;
                        pointSound.play();
                    }
                }


            }

        }
        return false;
    }

    public void setStopMoving(boolean stop) {
        stopMoving = stop;
    }

    @Override
    public void act(float delta) {
        if (!stopMoving) {
            for (int i = 0; i < tubePositionArray.size; i++) {
                TubePosition tubePosition = tubePositionArray.get(i);
                tubePosition.posX -= moveStep;
            }
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int backWidth = groundTextRegion.getRegionWidth();
        int size = Configuration.SCREEN_WIDTH / backWidth;
        if (size * backWidth < Configuration.SCREEN_WIDTH) size++;
        //make the ground moving animation.
        if (!stopMoving) {
            offset += moveStep;
            offset %= backWidth;
        }
        for (int i = -1; i < size; i++) {
            batch.draw(groundTextRegion, offset + i * backWidth, 0);
        }
        for (int i = 0; i < tubePositionArray.size; i++) {
            TubePosition tubePosition = tubePositionArray.get(i);
            if (tubePosition.posX > -bottomTubeTextRegion.getRegionWidth()
                    && tubePosition.posX < Configuration.SCREEN_WIDTH) {

                batch.draw(bottomTubeTextRegion, tubePosition.posX,
                        Configuration.groundHeight,
                        bottomTubeTextRegion.getRegionWidth(),
                        tubePosition.bottomTubeHeight);
                batch.draw(topTubeTextRegion, tubePosition.posX,
                        Configuration.SCREEN_HEIGHT
                                - tubePosition.topTubeHeight);
            }

        }

    }
}
