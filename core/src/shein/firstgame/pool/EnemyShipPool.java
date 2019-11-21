package shein.firstgame.pool;

import shein.firstgame.base.SpritesPool;
import shein.firstgame.math.Rect;
import shein.firstgame.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {

    private Rect worldBounds;
    private BulletPool bulletPool;

    public EnemyShipPool(Rect worldBounds, BulletPool bulletPool) {
        this.worldBounds = worldBounds;
        this.bulletPool = bulletPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(bulletPool, worldBounds);
    }
}
