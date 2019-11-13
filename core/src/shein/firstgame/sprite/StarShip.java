package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;

public class StarShip extends Sprite {

    private static final float V_LEN = 0.005f;

    private Rect worldBounds;
    private Vector2 touch;
    private Vector2 v;
    private Vector2 buff;


    public StarShip(TextureRegion region) {
        super(region);
        setHeightProportion(0.10f);
        v = new Vector2(0,0);
        touch = new Vector2();
        buff = new Vector2();
    }

    @Override
    public void update(float delta) {
        buff.set(touch);
        if (buff.sub(pos).len() > V_LEN){
            pos.add(v);
        } else {
            pos.set(touch);
            v.set(0,0);
        }
        checkBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setBottom(worldBounds.getBottom());
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer) {
        action();
        touch.set(target.x, worldBounds.getBottom() + getHalfHeight());
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 target, int pointer) {
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 target, int pointer) {
        pos.set(target.x ,worldBounds.getBottom() + getHalfHeight());
        v.set(0,0);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == 21){
            touch.set(worldBounds.getLeft(), worldBounds.getBottom() + getHalfHeight());
            v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        }
        if (keycode == 22){
            touch.set(worldBounds.getRight(), worldBounds.getBottom() + getHalfHeight());
            v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == 21 || keycode == 22){
            touch.set(getLeft(),0);
            v.set(0,0);
        }
        return false;
    }

    private void checkBounds(){
        if(getLeft() < worldBounds.getLeft()){
            pos.set(worldBounds.getLeft() + getHalfWidth(), worldBounds.getBottom() + getHalfHeight());
            v.set(0,0);
        }
        if (getRight() > worldBounds.getRight()){
            pos.set(worldBounds.getRight() - getHalfWidth(), worldBounds.getBottom() + getHalfHeight());
            v.set(0,0);
        }
    }

    private void action(){
    }
}
