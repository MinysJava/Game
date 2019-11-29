package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import shein.firstgame.base.ScaledTouchUpButton;
import shein.firstgame.math.Rect;
import shein.firstgame.screen.GameScreen;

public class StartNewGame extends ScaledTouchUpButton {

    private GameScreen gameScreen;

    public StartNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
    }

    @Override
    public void action() {
        gameScreen.startNewGame();

    }
}
