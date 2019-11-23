package shein.firstgame.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;

public class Explosion extends Sprite {

    private float animateInterval = 0.01f;
    private float animateTimer;

    private Sound soundExp;

    public Explosion(TextureAtlas atlas, Sound soundExp) {
        super(atlas.findRegion("explosion"), 9, 9, 74);
        this.soundExp = soundExp;
    }

    @Override
    public void update(float delta) {
        animateTimer += delta;
        if (animateTimer >= animateInterval) {
            frame++;
            if (frame == regions.length) {
                destroy();
            }
        }
    }

    public void set(Vector2 pos, float height) {
        this.pos.set(pos);
        setHeightProportion(height);
        soundExp.play(0.5f);
    }

    @Override
    public void destroy() {
        frame = 0;
        super.destroy();
    }
}
