package shein.firstgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.BaseScreen;
import shein.firstgame.math.Rect;
import shein.firstgame.sprite.Backgroung;
import shein.firstgame.sprite.Star;
import shein.firstgame.sprite.StarShip;


public class GameScreen extends BaseScreen {

    private Texture img;
    private TextureAtlas atlas;
    private Backgroung bg;
    private Star[] stars;
    private int STAR_COUNT = 25;
    private StarShip starship;
    private TextureRegion main_ship;
    private Texture ship;

    @Override
    public void show() {
        super.show();
        img = new Texture("textures/bg.png");
        ship = new Texture("textures/mainAtlas.png");
        bg = new Backgroung(new TextureRegion(img));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        main_ship = new TextureRegion(ship,916, 95, 195, 287);
        starship = new StarShip(main_ship);
    }

    @Override
    public void render(float delta) {
        update(delta);
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        bg.resize(worldBounds);
        for (Star s: stars) {
            s.resize(worldBounds);
        }
        starship.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        atlas.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        starship.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        starship.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer) {
        starship.touchDown(target, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 target, int pointer) {
        starship.touchUp(target, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 target, int pointer) {
        starship.touchDragged(target, pointer);
        return false;
    }

    private void update (float delta){
        for (Star s: stars) {
            s.update(delta);
        }
        starship.update(delta);
    }

    private void draw(){
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        bg.draw(batch);
        for (Star s: stars) {
            s.draw(batch);
        }
        starship.draw(batch);
        batch.end();
    }
}
