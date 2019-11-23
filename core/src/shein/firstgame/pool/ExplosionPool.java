package shein.firstgame.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import shein.firstgame.base.SpritesPool;
import shein.firstgame.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {

    private TextureAtlas atlas;
    private Sound soundExp;

    public ExplosionPool(TextureAtlas atlas) {
        this.atlas = atlas;
        soundExp = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
    }

    @Override
    protected Explosion newObject() {
        return new Explosion(atlas, soundExp);
    }

    @Override
    public void dispose() {
        soundExp.dispose();
        super.dispose();
    }
}