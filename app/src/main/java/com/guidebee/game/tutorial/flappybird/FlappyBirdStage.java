package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.camera.viewports.Viewport;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.tutorial.flappybird.actor.Background;
import com.guidebee.game.tutorial.flappybird.actor.Bird;
import com.guidebee.game.tutorial.flappybird.actor.Ground;


public class FlappyBirdStage extends Stage{
    private final Bird bird;
    private final Background background;
    private final Ground ground;


    private float period;





    public  FlappyBirdStage(Viewport viewport){
        super(viewport);

        bird=new Bird();
        addActor(bird);



        ground=new Ground();
        addActor(ground);
        ground.toBack();

        background=new Background();
        addActor(background);
        background.toBack();
    }



    @Override
    public void act(float delta){

        super.act(delta);


    }

}
