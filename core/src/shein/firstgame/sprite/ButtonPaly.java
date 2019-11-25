package shein.firstgame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import shein.firstgame.base.ScaledTouchUpButton;
import shein.firstgame.math.Rect;
import shein.firstgame.screen.GameScreen;

public class ButtonPaly extends ScaledTouchUpButton {

    private Game game;

    public ButtonPaly(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void action() {
        game.setScreen(new  GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }
}
