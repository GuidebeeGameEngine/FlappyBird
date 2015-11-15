package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.camera.viewports.Viewport;
import com.guidebee.game.scene.Group;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.tutorial.flappybird.actor.Background;
import com.guidebee.game.tutorial.flappybird.actor.Bird;
import com.guidebee.game.tutorial.flappybird.actor.GameOver;
import com.guidebee.game.tutorial.flappybird.actor.StartButton;
import com.guidebee.game.tutorial.flappybird.actor.Tube;
import com.guidebee.game.tutorial.flappybird.hud.Score;


public class FlappyBirdStage extends Stage{
    private final Bird bird;
    private final Background background;
    private final Tube tube;

    private final Group actorGroup=new Group();

    private final StartButton startButton;
    private final GameOver gameOver;
    private final Score score;

    public  FlappyBirdStage(Viewport viewport){
        super(viewport);


        addActor(actorGroup);

        bird=new Bird();
        bird.setPosition(-100, -100);
        addActor(bird);




        tube =new Tube();
        addActor(tube);
        tube.toBack();

        background=new Background();
        addActor(background);
        background.toBack();

        startButton=new StartButton(this);
        actorGroup.addActor(startButton);

        gameOver=new GameOver();
        actorGroup.addActor(gameOver);
        actorGroup.toFront();


        score =new Score();
        addHUDComponent(score);

        bird.setLive(false);
        tube.setStopMoving(true);
        background.setStopMoving(true);


    }

    public void removeStartButton(){
        startButton.setVisible(false);
        gameOver.setVisible(false);
    }

    public void startGame(){
        bird.reset();
        tube.generateLevelData();
        tube.setStopMoving(false);
        background.setStopMoving(false);
    }


    @Override
    public void act(float delta){

        super.act(delta);

        if(bird.isLive()) {
            score.setScore(tube.getScore());
            if (tube.isCollideWithTube(bird.getX(), bird.getY()) || bird.isOutside()) {
                bird.killBird();
                startButton.setVisible(true);
                gameOver.setVisible(true);
                tube.setStopMoving(true);
                background.setStopMoving(true);
            }
        }


    }

}
