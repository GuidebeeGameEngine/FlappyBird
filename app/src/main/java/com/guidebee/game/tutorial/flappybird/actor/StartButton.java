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
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;
import com.guidebee.game.tutorial.flappybird.FlappyBirdStage;
import com.guidebee.math.Vector3;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.input;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Start game button.
 * @author James Shen <james.shen@guidebee.com>
 */
public class StartButton extends Actor {

    private final FlappyBirdStage flappyBirdStage;

    public StartButton(FlappyBirdStage stage) {
        super("StartButton");
        flappyBirdStage = stage;
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas",
                TextureAtlas.class);
        TextureRegion buttonTextRegion = textureAtlas.findRegion("playbtn");
        setTextureRegion(buttonTextRegion);
        //show in the center of the screen.
        setPosition((Configuration.SCREEN_WIDTH
                        - buttonTextRegion.getRegionWidth()) / 2,
                (Configuration.SCREEN_HEIGHT
                        - buttonTextRegion.getRegionHeight()) / 2);

    }


    @Override
    public void act(float delta) {
        if (input.isTouched()) {
            //handel touch event
            Vector3 touchPos = new Vector3();
            touchPos.set(input.getX(), input.getY(), 0);
            getStage().getCamera().unproject(touchPos);
            if (getBoundingAABB().contains(touchPos.x, touchPos.y)) {
                flappyBirdStage.removeStartButton();
                flappyBirdStage.startGame();
            }
        }
    }
}
