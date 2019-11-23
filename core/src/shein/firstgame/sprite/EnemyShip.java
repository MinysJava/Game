package shein.firstgame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Ship;
import shein.firstgame.math.Rect;
import shein.firstgame.pool.BulletPool;

public class EnemyShip extends Ship {

    private enum State { DESCENT, FIGHT}

    private State state;

    private Vector2 descentV = new Vector2(0, -0.15f);

    public EnemyShip(BulletPool bulletPool, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.worldBounds = worldBounds;
        this.v.set(v0);
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        switch (state){
            case DESCENT:
                reloadTimer = 0f;
                if (getTop() <= worldBounds.getTop()){
                    v.set(v0);
                    state = State.FIGHT;
                    reloadTimer = reloadInterval;
                }
                break;
            case FIGHT:
                if (getBottom() < worldBounds.getBottom()) {
                    destroy();
                }
                break;
        }
    }

    public void set(
            TextureRegion[] regions,
            Vector2 v0,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            Sound shootSound,
            float height,
            int hp
    ) {
        this.regions = regions;
        this.v0.set(v0);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.shootSound = shootSound;
        setHeightProportion(height);
        this.hp = hp;
        this.v.set(descentV);
        state = State.DESCENT;
    }
}
