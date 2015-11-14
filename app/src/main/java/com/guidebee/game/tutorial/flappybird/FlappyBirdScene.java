package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.audio.Music;
import com.guidebee.game.camera.viewports.ScreenViewport;
import com.guidebee.game.camera.viewports.StretchViewport;
import com.guidebee.game.scene.Scene;
import com.guidebee.game.tutorial.flappybird.actor.Background;
import com.guidebee.game.tutorial.flappybird.actor.Bird;
import com.guidebee.game.tutorial.flappybird.actor.BottomTube;
import com.guidebee.game.tutorial.flappybird.actor.Ground;
import com.guidebee.game.tutorial.flappybird.actor.TopTube;

import static com.guidebee.game.GameEngine.*;


public class FlappyBirdScene extends Scene {

    private final Bird bird;
    private final Background background;
    private final Ground ground;
    private final TopTube topTube;
    private final BottomTube buttomTube;

    private final Music music;

    public FlappyBirdScene(){
        super(new ScreenViewport());
        //play background music
        music=assetManager.get("music.mp3", Music.class);
        music.setLooping(true);

        bird=new Bird();
        sceneStage.addActor(bird);

        topTube=new TopTube(20,100);
        sceneStage.addActor(topTube);

        buttomTube=new BottomTube(20,100);
        sceneStage.addActor(buttomTube);

        ground=new Ground();
        sceneStage.addActor(ground);
        ground.toBack();

        background=new Background();
        sceneStage.addActor(background);
        background.toBack();




    }

    @Override
    public void render(float delta) {
        graphics.clearScreen(0, 0, 0.2f, 1);
        super.render(delta);

    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
    }

    @Override
    public void pause() {
        music.stop();

    }

    @Override
    public void show() {
        //music.play();

    }
}
