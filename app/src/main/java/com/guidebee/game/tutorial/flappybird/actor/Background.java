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
import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;

import static com.guidebee.game.GameEngine.assetManager;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Game background, i.e. the remote building and trees.
 * @author James Shen <james.shen@guidebee.com>
 */
public class Background extends Actor {

    private final TextureRegion backgroundTextRegion;
    private final TextureRegion skyTextureRegion;
    /**
     * the background move a bit slow because it's far way.
     */
    private final int moveStep = 1;
    private int offset;
    private boolean stopMoving = false;


    public Background() {
        super("Background");
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas",
                TextureAtlas.class);
        backgroundTextRegion = textureAtlas.findRegion("bg");
        skyTextureRegion = textureAtlas.findRegion("sky");

        setSize(Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT);

    }

    public void setStopMoving(boolean stop) {
        stopMoving = stop;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        int backWidth = backgroundTextRegion.getRegionWidth();
        int skyHeight = skyTextureRegion.getRegionHeight();
        int widthSize = Configuration.SCREEN_WIDTH / backWidth;
        int remainSize = Configuration.SCREEN_WIDTH
                - Configuration.groundHeight
                - backgroundTextRegion.getRegionHeight();
        int skySize = 0;
        if (remainSize > 0) {
            skySize = remainSize / skyHeight;
            if (skySize * skyHeight < remainSize) skySize++;
        }
        if (widthSize * backWidth < Configuration.SCREEN_WIDTH) widthSize++;


        /**
         * animation -- moving slowly.
         */
        if (!stopMoving) {
            offset += moveStep;
            offset %= backWidth;
        }
        for (int i = 0; i < widthSize + 1; i++) {
            batch.draw(backgroundTextRegion, -offset + i * backWidth,
                    Configuration.groundHeight);
            for (int j = 0; j < skyHeight; j++) {
                batch.draw(skyTextureRegion, -offset + i * backWidth,
                        Configuration.groundHeight
                        + backgroundTextRegion.getRegionHeight()
                                + j * skyHeight);
            }
        }
    }
}
