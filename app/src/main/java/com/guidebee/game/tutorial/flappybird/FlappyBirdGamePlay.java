package com.guidebee.game.tutorial.flappybird;

import com.guidebee.game.GamePlay;
import com.guidebee.game.audio.Music;
import com.guidebee.game.audio.Sound;
import com.guidebee.game.graphics.TextureAtlas;
import com.guidebee.game.graphics.TextureRegion;

import static com.guidebee.game.GameEngine.assetManager;


public class FlappyBirdGamePlay extends GamePlay {
    @Override
    public void create() {
        loadAssets();
        FlappyBirdScene screen=new FlappyBirdScene();
        setScreen(screen);
    }

    @Override
    public void dispose(){
        assetManager.dispose();
    }

    private void loadAssets(){
        assetManager.load("flappybird.atlas", TextureAtlas.class);
        assetManager.load("music.mp3", Music.class);
        assetManager.load("sfx_wing.ogg",Sound.class);
        assetManager.load("sfx_die.ogg",Sound.class);
        assetManager.load("sfx_hit.ogg",Sound.class);
        assetManager.load("sfx_point.ogg",Sound.class);
        assetManager.finishLoading();
        TextureAtlas textureAtlas=assetManager.get("flappybird.atlas",TextureAtlas.class);
        TextureRegion groundTextRegion =textureAtlas.findRegion("ground");
        Configuration.groundHeight=groundTextRegion.getRegionHeight();
    }
}
