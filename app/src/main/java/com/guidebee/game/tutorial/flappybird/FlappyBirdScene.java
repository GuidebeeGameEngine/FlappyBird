package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.ScreenAdapter;
import com.guidebee.game.audio.Music;
import com.guidebee.game.camera.viewports.StretchViewport;
import com.guidebee.game.scene.Stage;

import static com.guidebee.game.GameEngine.assetManager;
import static com.guidebee.game.GameEngine.graphics;


public class FlappyBirdScene extends ScreenAdapter {


    private final Music music;



    /**
     * scene associated stage.
     */
    protected final Stage sceneStage;


    /**
     * Default constructor.
     */
    public FlappyBirdScene(){
        sceneStage=new FlappyBirdStage(new StretchViewport(Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT));
        //play background music
        music=assetManager.get("music.mp3", Music.class);
        music.setLooping(true);

    }




    @Override
    public void render(float delta){
        graphics.clearScreen(0, 0, 0.2f, 1);
        sceneStage.act();
        sceneStage.draw();
    }


    @Override
    public void dispose(){
        sceneStage.dispose();
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

    @Override
    public void resize(int width, int height){
        sceneStage.getViewport().update(width,height,false);
    }


}
