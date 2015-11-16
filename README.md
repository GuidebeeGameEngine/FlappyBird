# FlappyBird
Guidebee Game Engine - FlappyBird Demo 

Now we have the basic knowledge of Guidebee Game engine, and we encourage using the Stage-Actor pattern for your game design, it’s time to show a complete game. We use a very simple game Flappybird.

#Idea
Following the description of Flappbird on wiki — the FlappyBird’s GamePlay.
Flappy Bird was a side-scrolling mobile game featuring 2D retro style graphics. The objective was to direct a flying bird, named “Faby”, who moves continuously to the right, between sets of Mario-like pipes. If the player touches the pipes, they lose. Faby briefly flaps upward each time that the player taps the screen; if the screen is not tapped, Faby falls because of gravity; each pair of pipes that he navigates between earns the player a single point, with medals awarded for the score at the end of the game.

Before we only focus on the game scene, a complete game also need a “GameStart” and “GameOver”.

Normally in this phase , we brainstorm the game idea and sketch draft game scene. since we follow existing game .we use the following game states (scenes). Most of existing Flappybird are display in portrait mode, our Faby will fly in landscape mode screen.

#GameStart

![alt text](http://i1.wp.com/www.guidebee.com.au/wordpress/wp-content/uploads/2015/11/flappybird-start.png "Game Start")
A start button is displayed, when pressed ,a new game start.

#In-Game Scene

![alt text](http://i0.wp.com/www.guidebee.com.au/wordpress/wp-content/uploads/2015/11/flappybird-ingame.png "InGame scene")

The main game scene includes

*Background — remote buildings and forests –moving slowing in the background.
*Tubes  — include top tubes and bottom tubes, there’s some gaps between top and bottom tubes to allow Faby fly through.
*Ground — where bottom tubes stand on.
*Faby — The bird user can control and fly on screen.
*Score — show player’s current score.

#GameOver
![alt text](http://i0.wp.com/www.guidebee.com.au/wordpress/wp-content/uploads/2015/11/flappybird-gameover.png "Game Over")
When Faby hits the pipes , flies too high or touches the ground, Faby dies. A game over image is shown on screen, and the “Play” button reappears on screen.

#Preparing Resources
After we have the draft sketch of the game scenes ,it’s time to gathering resources (images and sound effects) for the game, normally this is where graphics designers come to help. But for this game ,we gathered the resources for existing game ,made some changes to fit for our game  and then used TextureAtlas to store these images.

![alt text](http://i0.wp.com/www.guidebee.com.au/wordpress/wp-content/uploads/2015/11/flappybird.png "Game Over")

Sound effect include

*background music music.mp3 ,
*Faby flies when player touches screen.  sfx_wing.ogg
*Faby hits the piples . sfx_hit.ogg
*Faby dies . sfx_die.ogg
*Faby earns points .sfx_point.ogg

#Game Design
We now have the game sketch ,resources ready .now think in Stage-Actor pattern. for this simple game, we need one stage–FlappyBirdStage. then what about actors, Faby is one actor. what about tubes? My original design was for each tube there’s one actor ,and for different tubes ,there’s one type of Tube ,ie. BottomTube and TopTube, you can still find this design in old github commit  .but this design turns out it’s too actor heavy and made game slow. So instead , only one Actor –Tube for all tubes on screen ,the tube’s state is stored in object TubePostion.

Also I had the ground as a separate actor ,this generally was Ok, but later I found I can merge this actor to Tube too. the ground only display a moving image and nothing else.

For other HUD component like score, button ,game over ,we used Actor for StartButton and GameOver and Table for Score (actually I stole this score from the the RainDrop game)

Following is the final design:

![alt text](http://i2.wp.com/www.guidebee.com.au/wordpress/wp-content/uploads/2015/11/flappybird-classdesign.png "Game Design")

Of course ,there’s other scaffolding classes like FlappyBirdGameActivity, FlappyBirdGamePlay, FlappyBirdScene, these classes are almost identical for all games ,these classes are just bridge classes to Android platform. we’ll ignore them for now. For the basics ,you can refer to Guidebee Android Game Engine Basics.

#Configuration class 
```
package com.guidebee.game.tutorial.flappybird;
 
//[------------------------------ MAIN CLASS ----------------------------------]
 
/**
 * Game configurations.
 * @author James Shen <james.shen@guidebee.com>
 */
public class Configuration {
    /**
     * Screen virtual width
     */
    public final static int SCREEN_WIDTH = 800;
 
    /**
     * Screen virtual height
     */
    public final static int SCREEN_HEIGHT = 480;
 
    /**
     * bird start pos x
     */
    public final static int BIRD_START_X = 300;
 
    /**
     * bird start pos y
     */
    public final static int BIRD_START_Y = 300;
 
    /**
     * bird move speed
     */
    public final static int MOVE_SPEED = 5;
 
    /**
     * min gab between top and bottom tubes
     */
    public final static int MIN_GAP = 100;
 
    /**
     * the height of ground
     */
    public static int groundHeight;
 
 
}
```

This class defines some configuration (like screen size ,initial bird position etc) for the Flappybird game.

#TubePosition class

```
package com.guidebee.game.tutorial.flappybird.state;
 
//[------------------------------ MAIN CLASS ----------------------------------]
 
/**
 * Tube state.
 * @author James Shen <james.shen@guidebee.com>
 */
public class TubePosition {
    /**
     * Tube x location
     */
    public int posX;
 
    /**
     * top tube height
     */
    public int topTubeHeight;
 
    /**
     * button tube height
     */
    public int bottomTubeHeight;
 
 
}
```

This class store position state for each tube pair (top tube and bottom tube).

#StartButton class

```
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
```

StartButton is a type of Actor, it’s only purpose is to start the game.

#GameOver class

```
package com.guidebee.game.tutorial.flappybird.actor;
 
//--------------------------------- IMPORTS ------------------------------------
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;
 
import static com.guidebee.game.GameEngine.assetManager;
 
//[------------------------------ MAIN CLASS ----------------------------------]
 
/**
 * Game over image.
 * @author James Shen <james.shen@guidebee.com>
 */
public class GameOver extends Actor {
 
    public GameOver() {
        super("StartButton");
 
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas",
                TextureAtlas.class);
        TextureRegion buttonTextRegion = textureAtlas.findRegion("gameover");
        setTextureRegion(buttonTextRegion);
        //initial position.
        setPosition((Configuration.SCREEN_WIDTH
                        - buttonTextRegion.getRegionWidth()) / 2,
                (Configuration.SCREEN_HEIGHT) / 2
                        + buttonTextRegion.getRegionHeight());
        setVisible(false);
    }
}
```

The GameOver is also a simple actor, it’s purpose is to display “Game Over” on screen, initially it’s invisible.

#Background class

```
package com.guidebee.game.tutorial.flappybird.actor;
 
//--------------------------------- IMPORTS ------------------------------------
import com.guidebee.game.graphics.Batch;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.scene.Actor;
import com.guidebee.game.tutorial.flappybird.Configuration;
 
import static com.guidebee.game.GameEngine.assetManager;
 
//[------------------------------ MAIN CLASS ----------------------------------]
 
/**
 * Game background, i.e. the remote building and trees.
 * @author James Shen <james.shen@guidebee.com>
 */
public class Background extends Actor {
 
    private final TextureRegion backgroundTextRegion;
    private final TextureRegion skyTextureRegion;
    /**
     * the background move a bit slow because it's far way.
     */
    private final int moveStep = 1;
    private int offset;
    private boolean stopMoving = false;
 
 
    public Background() {
        super("Background");
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas",
                TextureAtlas.class);
        backgroundTextRegion = textureAtlas.findRegion("bg");
        skyTextureRegion = textureAtlas.findRegion("sky");
 
        setSize(Configuration.SCREEN_WIDTH,
                Configuration.SCREEN_HEIGHT);
 
    }
 
    public void setStopMoving(boolean stop) {
        stopMoving = stop;
    }
 
    @Override
    public void draw(Batch batch, float parentAlpha) {
        int backWidth = backgroundTextRegion.getRegionWidth();
        int skyHeight = skyTextureRegion.getRegionHeight();
        int widthSize = Configuration.SCREEN_WIDTH / backWidth;
        int remainSize = Configuration.SCREEN_WIDTH
                - Configuration.groundHeight
                - backgroundTextRegion.getRegionHeight();
        int skySize = 0;
        if (remainSize > 0) {
            skySize = remainSize / skyHeight;
            if (skySize * skyHeight < remainSize) skySize++;
        }
        if (widthSize * backWidth < Configuration.SCREEN_WIDTH) widthSize++;
 
 
        /**
         * animation -- moving slowly.
         */
        if (!stopMoving) {
            offset += moveStep;
            offset %= backWidth;
        }
        for (int i = 0; i < widthSize + 1; i++) {
            batch.draw(backgroundTextRegion, -offset + i * backWidth,
                    Configuration.groundHeight);
            for (int j = 0; j < skyHeight; j++) {
                batch.draw(skyTextureRegion, -offset + i * backWidth,
                        Configuration.groundHeight
                        + backgroundTextRegion.getRegionHeight()
                                + j * skyHeight);
            }
        }
    }
}
```

The background class is used to display remote building and trees, here we override the draw method, we use smaller image(tiles) to make bigger images and slowing moving the building and tree a bit to show they are far way.
Note: Remember to use “Nearest” as filtering mode when packing the texture atlas ,dont use “Linear” filtering, otherwise you may find “gaps” between these tiles:

![alt text](http://i2.wp.com/www.guidebee.com.au/wordpress/wp-content/uploads/2015/11/gapsflappybird.png "Game Design")







