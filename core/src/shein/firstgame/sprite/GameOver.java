package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;

public class GameOver extends Sprite {

    public GameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setRight(worldBounds.getRight() - 0.1f);
        setBottom(worldBounds.getBottom() + 0.8f);
    }
}