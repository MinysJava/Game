package shein.firstgame.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import shein.firstgame.base.SpritesPool;
import shein.firstgame.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    private TextureAtlas atlas;
    private BulletPool bulletPool;

    public EnemyShipPool(TextureAtlas atlas, BulletPool bulletPool) {
        this.atlas = atlas;
        this.bulletPool = bulletPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(atlas, bulletPool);
    }


}
