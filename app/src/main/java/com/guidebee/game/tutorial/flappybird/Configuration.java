package com.guidebee.game.tutorial.flappybird;
import static com.guidebee.game.GameEngine.*;

public class Configuration {
    public  static int SCREEN_WIDTH=800;
    public  static int SCREEN_HEIGHT=480;

    static{
        SCREEN_WIDTH=graphics.getWidth();
        SCREEN_HEIGHT=graphics.getHeight();
    }
}
