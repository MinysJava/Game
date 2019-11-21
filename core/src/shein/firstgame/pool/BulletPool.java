package shein.firstgame.pool;

import shein.firstgame.base.SpritesPool;
import shein.firstgame.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {

    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}
