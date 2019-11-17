package shein.firstgame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;
import shein.firstgame.pool.BulletPool;

public class MainShip extends Sprite {

    private static final float BOTTOM_MARGIN = 0.03f;
    private static final int INVALID_POINTER = -1;

    private final Vector2 v0 = new Vector2(0.5f, 0);
    private final Vector2 v = new Vector2();

    private Rect worldBounds;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV = new Vector2(0, 0.5f);
    private Sound shootSound;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        checkBounds();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer) {
        if (target.x < worldBounds.pos.x){
            if(leftPointer != INVALID_POINTER) return false;
            leftPointer = pointer;
            moveLeft();
        } else {
            if(rightPointer != INVALID_POINTER) return false;
            rightPointer = pointer;
            moveRight();
        }
        action();
        return false;
    }

    @Override
    public boolean touchUp(Vector2 target, int pointer) {
        if(pointer == leftPointer){
            leftPointer = INVALID_POINTER;
            if(rightPointer != INVALID_POINTER){
                moveRight();
            } else {
                stop();
            }
        } else if(pointer == rightPointer){
            rightPointer = INVALID_POINTER;
            if(leftPointer != INVALID_POINTER){
                moveLeft();
            } else {
                stop();
            }
        }
        return false;
    }

    @Override
    public void keyDown(int keycode) {
       switch (keycode){
           case Input.Keys.D:
           case Input.Keys.RIGHT:
               moveRight();
               pressedRight = true;
               break;
           case Input.Keys.A:
           case Input.Keys.LEFT:
               moveLeft();
               pressedLeft = true;
               break;
           case Input.Keys.UP:
               shoot();
               break;
       }
    }

    @Override
    public void keyUp(int keycode) {
        switch (keycode){
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft){
                    moveLeft();
                } else {
                    stop();
                }
                break;
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if(pressedRight){
                    moveRight();
                } else {
                    stop();
                }
                break;
        }
    }

    private void checkBounds(){
        if(getLeft() < worldBounds.getLeft() - getHalfWidth()){
            pos.set(worldBounds.getLeft(), getBottom() + getHalfHeight());
            v.set(0,0);
        }
        if (getRight() > worldBounds.getRight() + getHalfWidth()){
            pos.set(worldBounds.getRight(), getBottom() + getHalfHeight());
            v.set(0,0);
        }
    }

    private void moveRight(){
        v.set(v0);
    }

    private void moveLeft(){
        v.set(v0).rotate(180);
    }

    private void stop(){
        v.setZero();
    }

    private void action(){
    }

    private void shoot(){
        Bullet bullet = bulletPool.obtain();
        bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1, shootSound);
    }
}