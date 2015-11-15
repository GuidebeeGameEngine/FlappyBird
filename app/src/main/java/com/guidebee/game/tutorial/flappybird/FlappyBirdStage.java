package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.camera.viewports.Viewport;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.tutorial.flappybird.actor.Background;
import com.guidebee.game.tutorial.flappybird.actor.Bird;
import com.guidebee.game.tutorial.flappybird.actor.Tube;


public class FlappyBirdStage extends Stage{
    private final Bird bird;
    private final Background background;
    private final Tube ground;


    private float period;





    public  FlappyBirdStage(Viewport viewport){
        super(viewport);

        bird=new Bird();
        addActor(bird);



        ground=new Tube();
        addActor(ground);
        ground.toBack();

        background=new Background();
        addActor(background);
        background.toBack();
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
        }


    }

}
