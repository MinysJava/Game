package shein.firstgame.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import shein.firstgame.base.ScaledTouchUpButton;
import shein.firstgame.math.Rect;
import shein.firstgame.screen.GameScreen;

public class ButtonPaly extends ScaledTouchUpButton {

    private Game game;
    Sound soundButton;

    public ButtonPaly(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
        soundButton = Gdx.audio.newSound(Gdx.files.internal("sounds/button.mp3"));
    }

    @Override
    public void action() {
        soundButton.play();
        game.setScreen(new  GameScreen());
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        setLeft(worldBounds.getLeft() + 0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }
}
