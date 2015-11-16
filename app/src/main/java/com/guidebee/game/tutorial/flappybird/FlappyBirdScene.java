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
package com.guidebee.game.tutorial.flappybird;

//--------------------------------- IMPORTS ------------------------------------
import com.guidebee.game.ScreenAdapter;
import com.guidebee.game.audio.Music;
import com.guidebee.game.camera.viewports.StretchViewport;
import com.guidebee.game.scene.Stage;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Flappy bird Game Scene.
 * @author James Shen <james.shen@guidebee.com>
 */
public class FlappyBirdScene extends ScreenAdapter {


    /**
     * scene associated stage.
     */
    protected final FlappyBirdStage sceneStage;



    /**
     * Default constructor.
     */
    public FlappyBirdScene() {
        sceneStage = new FlappyBirdStage(
                new StretchViewport(Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT));


    }


    @Override
    public void render(float delta) {
        graphics.clearScreen(0, 0, 0.2f, 1);
        sceneStage.act();
        sceneStage.draw();
    }


    @Override
    public void dispose() {
        sceneStage.dispose();
        assetManager.dispose();

    }

    @Override
    public void pause() {
        sceneStage.pauseGame();

    }

    @Override
    public void show() {
        sceneStage.resumeGame();

    }

    @Override
    public void resize(int width, int height) {
        sceneStage.getViewport().update(width, height, false);
    }


}
