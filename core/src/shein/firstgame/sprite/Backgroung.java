package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;

public class Backgroung extends Sprite {

    public Backgroung(TextureRegion region) {
        super(region);

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(1f);
        this.pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer) {
        System.out.println("Background");
        return false;
    }
}
