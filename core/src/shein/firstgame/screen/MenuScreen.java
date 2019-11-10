package shein.firstgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.BaseScreen;
import shein.firstgame.math.Rect;
import shein.firstgame.sprite.Backgroung;
import shein.firstgame.sprite.Logo;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture rc;
    private Backgroung bg;
    private Logo logo;


    @Override
    public void show() {
        super.show();
        img = new Texture("bg2048.jpg");
        rc = new Texture("redCircle.png");
        bg = new Backgroung(new TextureRegion(img));
        logo = new Logo(new TextureRegion(rc));
        logo.setHeightProportion(0.1f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        bg.draw(batch);
        logo.draw(batch);
        logo.update(1);
        batch.end();

    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        rc.dispose();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        bg.resize(worldBounds);
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer) {
        System.out.println("MenuScreen - touchDown, targetX = " + target.x + " targetY = " + target.y);
        logo.touchDown(target, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 target, int pointer) {
        logo.touchDragged(target, pointer);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        logo.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        logo.keyUp(keycode);
        return false;
    }
}