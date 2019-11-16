package shein.firstgame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;

public class Bullet extends Sprite {

    private Rect worldBounds;

    private final Vector2 v = new Vector2();
    private int damage;
    private Sprite owner;
    private Sound shootSound;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    public  void set(
            Sprite owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage,
            Sound shootSound
    ){
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        this.worldBounds = worldBounds;
        this.damage = damage;
        this.shootSound = shootSound;
        setHeightProportion(height);
        shootSound.play(0.1f);

    }

    public int getDamage() {
        return damage;
    }

    public Sprite getOwner() {
        return owner;
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);

        if (isOutside(worldBounds)){
            destroy();
        }
    }
}
