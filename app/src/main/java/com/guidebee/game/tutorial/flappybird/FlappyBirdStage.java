package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.camera.viewports.Viewport;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Group;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.tutorial.flappybird.actor.Background;
import com.guidebee.game.tutorial.flappybird.actor.Bird;
import com.guidebee.game.tutorial.flappybird.actor.StartButton;
import com.guidebee.game.tutorial.flappybird.actor.Tube;
import com.guidebee.game.ui.drawable.Drawable;
import com.guidebee.game.ui.drawable.TextureRegionDrawable;

import static com.guidebee.game.GameEngine.assetManager;


public class FlappyBirdStage extends Stage{
    private final Bird bird;
    private final Background background;
    private final Tube ground;

    private final Group actorGroup=new Group();

    private final StartButton startButton;

    public  FlappyBirdStage(Viewport viewport){
        super(viewport);


        addActor(actorGroup);

        bird=new Bird();
        bird.setPosition(-100, -100);
        addActor(bird);




        ground=new Tube();
        addActor(ground);
        ground.toBack();

        background=new Background();
        addActor(background);
        background.toBack();

        startButton=new StartButton(this);
        actorGroup.addActor(startButton);
        actorGroup.toFront();

        bird.setLive(false);
        ground.setStopMoving(true);
        background.setStopMoving(true);


    }

    public void removeStartButton(){
        startButton.setVisible(false);
    }

    public void startGame(){
        bird.reset();
        ground.generateLevelData();
        ground.setStopMoving(false);
        background.setStopMoving(false);
    }


    @Override
    public void act(float delta){

        super.act(delta);

        if(bird.isLive()) {
            if (ground.isCollideWithTube(bird.getX(), bird.getY()) || bird.isOutside()) {
                bird.killBird();
                ground.setStopMoving(true);
                background.setStopMoving(true);
            }
        }else{
            startButton.setVisible(true);
        }


    }

}
