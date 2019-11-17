package shein.firstgame.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;
import shein.firstgame.pool.BulletPool;

public class EnemyShip extends Sprite {

    private Rect worldBounds;
    private Vector2 v = new Vector2(0.01f, 0);
    private int i = 0;
    private int countLoop = 0;

    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private Vector2 bulletV = new Vector2(0, -0.5f);
    private Sound shootSound;

    public EnemyShip(TextureAtlas atlas, BulletPool bulletPool) {
        super(atlas.findRegion("enemy0"), 1, 2, 2);
        this.bulletPool = bulletPool;
        bulletRegion = atlas.findRegion("bulletEnemy");
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
    }

    public void set( Rect worldBounds){
        this.worldBounds = worldBounds;
        setHeightProportion(0.15f);
        setTop(worldBounds.getTop());
    }

    @Override
    public void update(float delta) {
        move();
        shoot();
        if (isOutside(worldBounds)){
            destroy();
        }
    }

    private void shoot() {
            if (i % 90 == 0) {
                Bullet bullet = bulletPool.obtain();
                bullet.set(this, bulletRegion, pos, bulletV, 0.01f, worldBounds, 1, shootSound);
            }
        }

    private void move() {
        pos.mulAdd(v, 0.3f);
        if(countLoop < 1){
            v.rotate(-1);
            i++;
            if(i % 360 == 0){
                countLoop++;
            }
        } else {
            v.set(0.01f, 0);
            i++;
        }
    }
}
