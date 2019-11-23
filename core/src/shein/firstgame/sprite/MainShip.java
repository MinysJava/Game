package shein.firstgame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Ship;
import shein.firstgame.math.Rect;
import shein.firstgame.pool.BulletPool;
import shein.firstgame.pool.ExplosionPool;

public class MainShip extends Ship {

    private static final float BOTTOM_MARGIN = 0.03f;
    private static final int INVALID_POINTER = -1;

    private boolean pressedLeft;
    private boolean pressedRight;

    private int leftPointer = INVALID_POINTER;
    private int rightPointer = INVALID_POINTER;

    public MainShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        bulletRegion = atlas.findRegion("bulletMainShip");
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        v0.set(0.5f, 0);
        reloadInterval = 0.2f;
        bulletHeight = 0.01f;
        damage = 1;
        hp = 3;
        bulletV.set(0, 0.5f);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stop();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stop();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setBottom(worldBounds.getBottom() + BOTTOM_MARGIN);
    }

    public void dispose(){
        shootSound.dispose();
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

    public boolean isBulletCollisoin(Rect bullet){
        return !(
                bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getTop() < getBottom()
                || bullet.getBottom() > pos.y
                );
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
}
