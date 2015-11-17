# FlappyBird
Guidebee Game Engine - FlappyBird Demo 

Now we have the basic knowledge of Guidebee Game engine, and we encourage using the Stage-Actor pattern for your game design, it’s time to show a complete game. We use a very simple game Flappybird.

[Guidebee IT Consulting Service Perth](http://www.guidebee.com.au)

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
```java
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

```java
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

```java
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

```java
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

```java
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

#Create the project


As a reminder,we create an Android Gradle project (FlappBird) with Android Studio , in the app’s build.gradle, add guidebee game engine dependencies
‘compile com.guidebee:game-engine:0.9.8’

```
dependencies {
    compile fileTree(dir: 'libs', include: ['*.jar'])
    testCompile 'junit:junit:4.12'
    compile 'com.android.support:appcompat-v7:19+'
    compile 'com.guidebee:game-engine:0.9.8'
}

```
Add related resources and scaffolding classes (code).

#Project Structure
![alt text](http://i2.wp.com/www.guidebee.com.au/wordpress/wp-content/uploads/2015/11/flappybirdproject.png "Game Design")

#scaffolding classes

Scaffolding classes  : FlappbirdGameActivity, FlappingBirdGamePlay and FlappingBirdScene connect Game Logic /Scene with Android Platform. Their implementations are almost the same for most of games use Guidebee Game Engine.

For the game scene, we still want to use 800 X 480 virtual screen resolution and want to an customized stage instead of build-in stage defined in Scene. so our FlappyBirdScene derived from ScreenAdapter instead of Scene. most of code are copied from Scene class:

```java
//--------------------------------- PACKAGE ------------------------------------
package com.guidebee.game.tutorial.flappybird;
 
//--------------------------------- IMPORTS ------------------------------------
import com.guidebee.game.ScreenAdapter;
import com.guidebee.game.camera.viewports.StretchViewport;
 
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

```

FlappyBirdScene includes the FlappyBirdStage ,which is the main Stage for the game. when player switch to other application(like taking calls). the life cycle event pause and show will accordingly pause and resume game, these methods are defined in FlappyBirdStage.

#Bird class
Let’s first focus on our main actor class -Bird.  Bird (Fapy) has animations (flapping it’s wings). So we use Animation class .

```java
private float tick = 0.05f;
private final Animation flyAnimation;
private final TextureRegion birdTextRegion;
 
private final int SPRITE_WIDTH = 34;
private final int SPRITE_HEIGHT = 24;
private final int SPRITE_FRAME_SIZE = 3;
...
TextureAtlas textureAtlas = assetManager.get("flappybird.atlas",
                TextureAtlas.class);
birdTextRegion = textureAtlas.findRegion("birdanimation");
Array<TextureRegion> keyFrames = new Array<TextureRegion>();
for (int i = 0; i < SPRITE_FRAME_SIZE; i++) {
    TextureRegion textureRegion = new TextureRegion(birdTextRegion,
            i * SPRITE_WIDTH, 0,
            SPRITE_WIDTH, SPRITE_HEIGHT);
    keyFrames.add(textureRegion);
}
flyAnimation = new Animation(tick, keyFrames);

```

To achieve animation ,in Bird’s act method, we constantly change Bird’s image with one of key frame in the animation object.

```java

setTextureRegion(flyAnimation.getKeyFrame(elapsedTime, true));
```
By default, if player doesn’t touch the screen, the Bird will following free-fall movement, we can use Box2D graphics, but for this simple game, it’s a overkill, instead we use simple math to calculate new position for the bird.

```java
private static final int GRAVITY = -100;
private static final int MOVEMENT = 100;
private Vector3 position;
private Vector3 velocity;
...
setPosition(Configuration.BIRD_START_X,
                Configuration.BIRD_START_Y);
position = new Vector3(Configuration.BIRD_START_X,
        Configuration.BIRD_START_Y, 0);
velocity = new Vector3(0, 0, 0);
 
...
/**
 * calculate bird's new position --free fall.
 */
velocity.add(0, GRAVITY, 0);
velocity.scl(delta);
position.add(MOVEMENT * delta, velocity.y, 0);
...
/**
 * make sure the bird not to pass through the ground.
 */
if (position.y < Configuration.groundHeight) {
    position.y = Configuration.groundHeight;
}
setY(position.y);

```

We also make sure bird doesn’t fall through the ground area, the bird initially was put in center (slight on the left) of the screen. and can only move up and down.

When player touch the screen ,this gives the bird a boost up ,we need to handle the touch event, we use pooling to handle this event:

```java
/**
 * handle touch event, move the bird upward a bit
 */
if (input.isTouched()) {
    rotateBy(30 * delta);
    velocity.y = 250;
    if (nextSoundPeriod > 0.3) {
        flapSound.play();
        nextSoundPeriod = 0;
    }
 
} else {
    rotateBy(-20 * delta);
 
}
 
/**
 * control the bird's turning angle.
 */
float currentAngle = getRotation();
if (currentAngle < -90 && currentAngle < 0) { 
      setRotation(-90);
 }
 if (currentAngle > 90 && currentAngle > 0) {
    setRotation(90);
}

```
to make a better simulation of bird, we only turn Fapy’s head up and down a bit when he flies. this is why setRotation and rotateBy are used, and we dont want the bird to turn around and around, it can only turn from -90 to 90 degree.

Bird also have one state variable ,isLive or not. and one method to check if the bird fly within the screen:

```java
public boolean isOutside() {
    return (position.y <= Configuration.groundHeight 
       || position.y >= Configuration.SCREEN_HEIGHT);
}

```

and if the bird was killed ,or player start a new game, we need to reset the state of the bird:

```java
public void reset() {
    setPosition(Configuration.BIRD_START_X,
            Configuration.BIRD_START_Y);
    position.set(Configuration.BIRD_START_X,
            Configuration.BIRD_START_Y, 0);
    velocity.set(0, 0, 0);
    setRotation(0);
    isLive = true;
 
 
}
 
 
public void killBird() {
    isLive = false;
    setRotation(180);
    dieSound.play();
    setTextureRegion(flyAnimation.getKeyFrame(0));
}
 
public boolean isLive() {
    return isLive;
}
 
public void setLive(boolean live) {
    isLive = live;
}

```

#Tube class
As mentioned before, we decided to use on Tube actor to represent all the tubes in the game scene.  for all the tubes ,we use one array of TubePosition to store the tubes’s state:

```java
/**
 * all tube positions.
 */
private Array<TubePosition> tubePositionArray = new Array<TubePosition>();
```

Also we want to randomly generated all the tubes ,their positions, height of top tubes and bottom tubes:

```java
/**
 * random generate the tube positions.
 */
public void generateLevelData() {
    tubePositionArray.clear();
    score = -1;
    int tubeLength = Configuration.SCREEN_HEIGHT
            - Configuration.groundHeight;
    for (int i = 0; i < 150; i++) {
        TubePosition tubePosition = new TubePosition();
        tubePosition.posX = Configuration.SCREEN_WIDTH / 2 * i
                + random.nextInt(100) +
                Configuration.SCREEN_WIDTH * 3;
        tubePosition.topTubeHeight = random.nextInt(tubeLength);
        if (tubePosition.topTubeHeight
                > topTubeTextRegion.getRegionHeight()) {
            tubePosition.topTubeHeight
                    = topTubeTextRegion.getRegionHeight();
        }
        tubePosition.bottomTubeHeight = tubeLength
                - tubePosition.topTubeHeight
                - Configuration.MIN_GAP - random.nextInt(30);
        if (tubePosition.bottomTubeHeight < 0) {
            tubePosition.bottomTubeHeight = 10;
        }
 
        if (tubeLength - tubePosition.topTubeHeight
                - tubePosition.bottomTubeHeight
                < Configuration.MIN_GAP) {
            tubePosition.topTubeHeight = 100;
            tubePosition.bottomTubeHeight = 100;
        }
        tubePositionArray.add(tubePosition);
    }
}

```


These code has logic to make sure the gap distance between top tube and bottom tubes have a minimum safe distance Configuration.MIN_GAP to allow the bird fly through.

For the tube’s draw method, since the tube manager all the tubes, it needs to draw all tubes which currently with screen area:

```java
@Override
public void draw(Batch batch, float parentAlpha) {
    int backWidth = groundTextRegion.getRegionWidth();
    int size = Configuration.SCREEN_WIDTH / backWidth;
    if (size * backWidth < Configuration.SCREEN_WIDTH) size++;
    //make the ground moving animation.
    if (!stopMoving) {
        offset += moveStep;
        offset %= backWidth;
    }
    for (int i = -1; i < size; i++) {
        batch.draw(groundTextRegion, offset + i * backWidth, 0);
    }
    for (int i = 0; i < tubePositionArray.size; i++) {
        TubePosition tubePosition = tubePositionArray.get(i);
        if (tubePosition.posX > -bottomTubeTextRegion.getRegionWidth()
                && tubePosition.posX < Configuration.SCREEN_WIDTH) {
 
            batch.draw(bottomTubeTextRegion, tubePosition.posX,
                    Configuration.groundHeight,
                    bottomTubeTextRegion.getRegionWidth(),
                    tubePosition.bottomTubeHeight);
            batch.draw(topTubeTextRegion, tubePosition.posX,
                    Configuration.SCREEN_HEIGHT
                            - tubePosition.topTubeHeight);
        }
 
    }
 
}

```


The Tube also in charge the ground drawing, we could use a separate Actor to do this, but it’s easier to merge the Ground Actor to Tube.

Since the bird only moves up and down, we need to move tubes left continuously to achieving Bird flying animation. so in tube’s act methods, we move all tubes left a bit.

```java
@Override
public void act(float delta) {
    if (!stopMoving) {
        for (int i = 0; i < tubePositionArray.size; i++) {
            TubePosition tubePosition = tubePositionArray.get(i);
            tubePosition.posX -= moveStep;
        }
    }
}
```

We also need a method to check if the bird collides with tubes and if the bird flies pass through the gaps between top and bottom tubes:

```java
public boolean isCollideWithTube(float x, float y) {
    for (int i = 0; i < tubePositionArray.size; i++) {
        TubePosition tubePosition = tubePositionArray.get(i);
        if (tubePosition.posX
                > -bottomTubeTextRegion.getRegionWidth()
                && tubePosition.posX
                < Configuration.SCREEN_WIDTH) {
 
            topRect.x = tubePosition.posX;
            topRect.y = Configuration.SCREEN_HEIGHT
                    - tubePosition.topTubeHeight;
            topRect.height = tubePosition.topTubeHeight;
 
            bottomRect.x = tubePosition.posX;
 
            bottomRect.height = tubePosition.bottomTubeHeight;
 
            boolean collide = topRect.contains(x, y)
                    || bottomRect.contains(x, y);
            if (collide) {
                hitSound.play();
                return true;
            }
 
            scoreRect.x = tubePosition.posX;
            if (scoreRect.contains(x, y)) {
                if (score < i) {
                    score = i;
                    pointSound.play();
                }
            }
 
 
        }
 
    }
    return false;
}

```
for collision detection, we used simple rectangle contain method.

#FlappyBirdStage class

Now we have all the actors ready, it’s time to put them on stage, and in stage’s act method, we check if reaches game’s end state (Fapy hit the tube or touches sky or touches ground)
Since most of logic were defined in individual Actors ,the stage class is a bit simple.


```java
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

```

Youtube video

[![IMAGE ALT TEXT HERE](http://img.youtube.com/vi/2lH9yNE-fig/0.jpg)](http://www.youtube.com/watch?v=2lH9yNE-fig)





