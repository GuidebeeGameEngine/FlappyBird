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




