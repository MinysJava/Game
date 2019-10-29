package shein.firstgame;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class ShotGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	TextureRegion region;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("bg2048.jpg");
		region = new TextureRegion(img, 20, 30, 50, 70);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(img, 0, 0, 700, 700);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}
}
