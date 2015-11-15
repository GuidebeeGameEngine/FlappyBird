package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.camera.viewports.Viewport;
import com.guidebee.game.scene.Group;
import com.guidebee.game.scene.Stage;
import com.guidebee.game.tutorial.flappybird.actor.Background;
import com.guidebee.game.tutorial.flappybird.actor.Bird;
import com.guidebee.game.tutorial.flappybird.actor.BottomTube;
import com.guidebee.game.tutorial.flappybird.actor.Ground;
import com.guidebee.game.tutorial.flappybird.actor.TopTube;
import com.guidebee.utils.Pool;
import com.guidebee.utils.collections.Array;


public class FlappyBirdStage extends Stage{
    private final Bird bird;
    private final Background background;
    private final Ground ground;

    private final int moveStep;
    private final Group tubeGroup=new Group();

    private float period;



    private final Array<TopTube> topTubes=new Array<TopTube>();
    private final Array<BottomTube> bottomTubes=new Array<BottomTube>();

    private final Pool<TopTube> topTubePool=new Pool<TopTube>() {

        @Override
        protected TopTube newObject() {
            return new TopTube();
        }
    };

    private final Pool<BottomTube> bottomTubePool=new Pool<BottomTube>() {

        @Override
        protected BottomTube newObject() {
            return new BottomTube();
        }
    };

    public  FlappyBirdStage(Viewport viewport){
        super(viewport);
        moveStep=Configuration.MOVE_STEP;
        bird=new Bird();
        addActor(bird);

        addActor(tubeGroup);
        generateTubes();

        ground=new Ground();
        addActor(ground);
        ground.toBack();

        background=new Background();
        addActor(background);
        background.toBack();
    }

    private void generateTubes(){
        for(int i=0;i<10;i++){
            TopTube topTube=topTubePool.obtain();
            topTube.setAttr(i*150,100);
            BottomTube bottomTube=bottomTubePool.obtain();
            bottomTube.setAttr(i*150,140);
            tubeGroup.addActor(topTube);
            tubeGroup.addActor(bottomTube);

            topTubes.add(topTube);
            bottomTubes.add(bottomTube);

        }
    }

    @Override
    public void act(float delta){

        super.act(delta);
        period+=delta;

        float steps=moveStep*period;
        if(steps>1) {
            period=0;

            for (int i = 0; i < topTubes.size; i++) {
                TopTube topTube = topTubes.get(i);
                BottomTube bottomTube = bottomTubes.get(i);

                int newPos = topTube.getPosX() - (int) steps*5;
                topTube.setPosX(newPos);
                bottomTube.setPosX(newPos);
                if (newPos < -topTube.getWidth()) {
                    tubeGroup.removeActor(topTube);
                    tubeGroup.removeActor(bottomTube);
                    topTubes.removeIndex(i);
                    bottomTubes.removeIndex(i);

                }

            }
        }

    }

}
