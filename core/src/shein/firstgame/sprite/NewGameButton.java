package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import shein.firstgame.base.ScaledTouchUpButton;
import shein.firstgame.math.Rect;
import shein.firstgame.screen.GameScreen;

public class NewGameButton extends ScaledTouchUpButton {

    protected MainShip mainShip;

    public NewGameButton(TextureAtlas atlas, MainShip mainShip) {
        super(atlas.findRegion("button_new_game"));
        this.mainShip = mainShip;
    }

    @Override
    public void action() {
        mainShip.flushDestroy();


    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setRight(worldBounds.getRight() - 0.12f);
        setBottom(worldBounds.getBottom() + 0.6f);
    }
}
