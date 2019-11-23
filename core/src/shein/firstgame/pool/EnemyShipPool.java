package shein.firstgame.pool;

import shein.firstgame.base.SpritesPool;
import shein.firstgame.math.Rect;
import shein.firstgame.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private Rect worldBounds;
    private BulletPool bulletPool;
    private ExplosionPool explosionPool;

    public EnemyShipPool(Rect worldBounds, ExplosionPool explosionPool, BulletPool bulletPool) {
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
        this.bulletPool = bulletPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, explosionPool, worldBounds);
    }
}
