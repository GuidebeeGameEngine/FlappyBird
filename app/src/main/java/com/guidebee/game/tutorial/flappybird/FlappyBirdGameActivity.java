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

//--------------------------------- IMPORTS ------------------------------------
import android.os.Bundle;

import com.guidebee.game.Configuration;
import com.guidebee.game.activity.GameActivity;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Flappy bird Game Activity.
 * @author James Shen <james.shen@guidebee.com>
 */
public class FlappyBirdGameActivity extends GameActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Configuration config = new Configuration();

        config.useAccelerometer = false;
        config.useCompass = false;

        initialize(new FlappyBirdGamePlay(), config);
    }
}
