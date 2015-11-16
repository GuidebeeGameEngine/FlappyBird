/*******************************************************************************
 * Copyright 2015 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
//--------------------------------- PACKAGE ------------------------------------
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
