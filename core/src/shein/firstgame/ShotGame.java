package shein.firstgame;

import com.badlogic.gdx.Game;
import shein.firstgame.screen.MenuScreen;

public class ShotGame extends Game {

	@Override
	public void create() {
		setScreen(new MenuScreen());
	}
}
