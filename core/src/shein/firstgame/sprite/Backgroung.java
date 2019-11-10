package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;

public class Backgroung extends Sprite {

    public Backgroung(TextureRegion region) {
        super(region);

        setHeightProportion(1f);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }
}
