package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;

public class Logo extends Sprite {

    private static final float V_LEN = 0.01f;

    private Vector2 v;
    private Vector2 touch ;
    private Vector2 buff;

    public Logo(TextureRegion region) {
        super(region);

        v = new Vector2();
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
        }
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer) {
        touch.set(target.x - getHalfWidth(), target.y - getHalfHeight());
        v.set(touch.cpy().sub(pos)).setLength(V_LEN);
        System.out.println((getLeft() + getHalfWidth()));

        return false;
    }

    @Override
    public boolean touchDragged(Vector2 target, int pointer) {

        pos.set(target.x - getHalfWidth(), target.y - getHalfHeight());
        v.set(0,0);
        return false;
    }

//    @Override
//    public boolean keyDown(int keycode) {
//        if(keycode == 19){
//            touch.add(0, 0.01f);
//            v.add(0, 0.01f);
//        }
//        if (keycode == 20){
//            v.add(0, -0.01f);
//        }
//        if (keycode == 21){
//            v.add(-0.01f, 0);
//        }
//        if (keycode == 22){
//            v.add(0.01f, 0);
//        }
//        return false;
//    }
//
//    @Override
//    public boolean keyUp(int keycode) {
//        if(keycode == 19){
//            v.add(0, -0.01f);
//        }
//        if (keycode == 20){
//            v.add(0, 0.01f);
//        }
//        if (keycode == 21){
//            v.add(0.01f, 0);
//        }
//        if (keycode == 22){
//            v.add(-0.01f, 0);
//        }
//        return false;
//    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(
                regions[frame],
                pos.x, pos.y,
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }
}
