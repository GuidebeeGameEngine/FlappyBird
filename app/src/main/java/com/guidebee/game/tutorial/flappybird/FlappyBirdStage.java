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
import com.guidebee.game.audio.Music;
import com.guidebee.game.camera.viewports.Viewport;
import com.guidebee.game.scene.Group;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.tutorial.flappybird.actor.Background;
import com.guidebee.game.tutorial.flappybird.actor.Bird;
import com.guidebee.game.tutorial.flappybird.actor.GameOver;
import com.guidebee.game.tutorial.flappybird.actor.StartButton;
import com.guidebee.game.tutorial.flappybird.actor.Tube;
import com.guidebee.game.tutorial.flappybird.hud.Score;

import static com.guidebee.game.GameEngine.assetManager;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Flappy bird game stage.
 * @author James Shen <james.shen@guidebee.com>
 */
public class FlappyBirdStage extends Stage {

    private final Bird bird;
    private final Background background;
    private final Tube tube;

    private final Group actorGroup = new Group();
    private final StartButton startButton;
    private final GameOver gameOver;
    private final Score score;
    private final Music music;

    private volatile boolean paused=false;


    public FlappyBirdStage(Viewport viewport) {
        super(viewport);
        addActor(actorGroup);

        bird = new Bird();
        bird.setPosition(-100, -100);
        addActor(bird);

        tube = new Tube();
        addActor(tube);
        tube.toBack();

        background = new Background();
        addActor(background);
        background.toBack();

        startButton = new StartButton(this);
        actorGroup.addActor(startButton);

        gameOver = new GameOver();
        actorGroup.addActor(gameOver);
        actorGroup.toFront();

        score = new Score();
        addHUDComponent(score);

        bird.setLive(false);
        tube.setStopMoving(true);
        background.setStopMoving(true);

        music = assetManager.get("music.mp3", Music.class);
        music.setLooping(true);

    }

    public void removeStartButton() {
        startButton.setVisible(false);
        gameOver.setVisible(false);
    }

    public void startGame() {
        paused=false;
        bird.reset();
        tube.generateLevelData();
        tube.setStopMoving(false);
        background.setStopMoving(false);
        music.play();
    }

    private void GameOver(){
        bird.killBird();
        startButton.setVisible(true);
        gameOver.setVisible(true);
        tube.setStopMoving(true);
        background.setStopMoving(true);
        music.stop();
    }


    public void pauseGame(){
        paused=true;
        music.stop();
    }


    public void resumeGame(){
        paused=false;
        music.play();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if(!paused) {
            if (bird.isLive()) {
                score.setScore(tube.getScore());
                if (tube.isCollideWithTube(bird.getX(), bird.getY())
                        || bird.isOutside()) {
                    GameOver();
                }
            }
        }
    }


}
