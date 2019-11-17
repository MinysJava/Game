package shein.firstgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.BaseScreen;
import shein.firstgame.math.Rect;
import shein.firstgame.pool.BulletPool;
import shein.firstgame.pool.EnemyShipPool;
import shein.firstgame.sprite.Backgroung;
import shein.firstgame.sprite.EnemyShip;
import shein.firstgame.sprite.Star;
import shein.firstgame.sprite.MainShip;


public class GameScreen extends BaseScreen {

    private Texture img;
    private TextureAtlas atlas;
    private Backgroung bg;
    private Star[] stars;
    private int STAR_COUNT = 25;
    private MainShip mainShip;
    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private Rect worldBounds;
    private int i = 0;



    @Override
    public void show() {
        super.show();
        img = new Texture("textures/bg.png");
        bg = new Backgroung(new TextureRegion(img));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool);
        enemyShipPool = new EnemyShipPool(atlas, bulletPool);
//        enemy0();

    }

    @Override
    public void render(float delta) {
        update(delta);

        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        bg.resize(worldBounds);
        for (Star s: stars) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
//        enemyShipPool.resize(worldBounds);
    }

    @Override
    public void dispose() {
        super.dispose();
        img.dispose();
        atlas.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return false;
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer) {
        mainShip.touchDown(target, pointer);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 target, int pointer) {
        mainShip.touchUp(target, pointer);
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 target, int pointer) {
        mainShip.touchDragged(target, pointer);
        return false;
    }

    private void update (float delta){
        for (Star s: stars) {
            s.update(delta);
        }
        mainShip.update(delta);
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
        if (i % 60 == 0){
            enemy0();
            i++;
        }

    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprite();
        enemyShipPool.freeAllDestroyedActiveSprite();
    }

    private void draw(){
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        bg.draw(batch);
        for (Star s: stars) {
            s.draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);
        batch.end();
    }

    private void enemy0(){
        EnemyShip enemyShip = enemyShipPool.obtain();
        enemyShip.set(worldBounds);
    }
}
