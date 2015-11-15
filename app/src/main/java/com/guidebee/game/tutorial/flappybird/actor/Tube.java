package com.guidebee.game.tutorial.flappybird.actor;

import com.guidebee.game.scene.Actor;
import com.guidebee.utils.Pool;


abstract public class Tube  extends Actor implements Pool.Poolable {
    protected int tubeHeight;
    protected int posX;
    protected boolean inUse=false;

    public void setAttr(int x,int height){
        posX=x;
        tubeHeight=height;
    }


    public void setPosX(int x){
        posX=x;
    }

    public int getPosX(){
        return posX;
    }

    public Tube(String name,int x, int height){
        super(name);
        tubeHeight=height;
        posX=x;
    }

    @Override
    public void reset() {
        inUse=false;
    }

    public boolean isInUse(){
        return inUse;
    }

    public void setInUse(boolean inUse){
        this.inUse=inUse;
    }
}
