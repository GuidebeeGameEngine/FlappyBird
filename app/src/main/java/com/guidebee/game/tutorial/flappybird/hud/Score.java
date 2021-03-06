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
package com.guidebee.game.tutorial.flappybird.hud;

//--------------------------------- IMPORTS ------------------------------------
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;
import com.guidebee.game.ui.HorizontalGroup;
import com.guidebee.game.ui.Image;
import com.guidebee.game.ui.Table;
import com.guidebee.game.ui.drawable.TextureRegionDrawable;

import static com.guidebee.game.GameEngine.assetManager;

//[------------------------------ MAIN CLASS ----------------------------------]

/**
 * Game score HUD component.
 * @author James Shen <james.shen@guidebee.com>
 */
public class Score extends Table {

    private final Image goldCoin;
    private final Image thousands;
    private final Image hundreds;
    private final Image tens;
    private final Image units;


    private final TextureRegionDrawable[] numberDrawables;


    public Score() {
        TextureAtlas textureAtlas = assetManager.get("flappybird.atlas",
                TextureAtlas.class);
        goldCoin = new Image(textureAtlas.findRegion("coin"));
        TextureRegion numbers = textureAtlas.findRegion("numbers");
        numberDrawables = new TextureRegionDrawable[11];
        for (int i = 0; i < 10; i++) {
            numberDrawables[i]
                    = new TextureRegionDrawable(
                    new TextureRegion(numbers, i * 14, 0, 14, 14));
        }
        thousands = new Image(numberDrawables[0]);
        hundreds = new Image(numberDrawables[0]);
        tens = new Image(numberDrawables[0]);
        units = new Image(numberDrawables[0]);
        HorizontalGroup space = new HorizontalGroup();
        space.padLeft(10);

        add(goldCoin);
        add(space);
        add(thousands);
        add(hundreds);
        add(tens);
        add(units);
        setSize(200, 40);
        setPosition(20, 20);
    }


    public void setScore(int score) {
        int newScore = score % 10000;
        int thousandsNum = newScore / 1000;

        int hundredsNum = (newScore % 1000) / 100;
        int tensNum = (newScore % 100) / 10;
        int unitsNum = (newScore % 10);

        thousands.setDrawable(numberDrawables[thousandsNum]);
        hundreds.setDrawable(numberDrawables[hundredsNum]);
        tens.setDrawable(numberDrawables[tensNum]);
        units.setDrawable(numberDrawables[unitsNum]);
    }
}

