package shein.firstgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import shein.firstgame.base.BaseScreen;
import shein.firstgame.math.Rect;
import shein.firstgame.pool.BulletPool;
import shein.firstgame.pool.EnemyShipPool;
import shein.firstgame.pool.ExplosionPool;
import shein.firstgame.sprite.Backgroung;
import shein.firstgame.sprite.Bullet;
import shein.firstgame.sprite.EnemyShip;
import shein.firstgame.sprite.GameOver;
import shein.firstgame.sprite.Star;
import shein.firstgame.sprite.MainShip;
import shein.firstgame.utils.EnemyEmitter;


public class GameScreen extends BaseScreen {

    private  static final int STAR_COUNT = 25;

    private Texture img;
    private TextureAtlas atlas;
    private Backgroung bg;
    private GameOver gameOver;

    private Star[] stars;
    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;

    private EnemyEmitter enemyEmitter;

    private Music gameMusic;

    @Override
    public void show() {
        super.show();
        gameMusic = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        img = new Texture("textures/bg.png");
        bg = new Backgroung(new TextureRegion(img));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        stars = new Star[STAR_COUNT];
        for (int i = 0; i < STAR_COUNT; i++) {
            stars[i] = new Star(atlas);
        }
        gameOver = new GameOver(atlas);
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyShipPool = new EnemyShipPool(worldBounds,explosionPool, bulletPool);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyShipPool, atlas, worldBounds);
        gameMusic.setVolume(0.7f);
        gameMusic.setLooping(true);
        gameMusic.play();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void resize(Rect worldBounds) {
        bg.resize(worldBounds);
        for (Star s: stars) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
    }

    @Override
    public void dispose() {
        img.dispose();
        atlas.dispose();
        gameMusic.dispose();
        mainShip.dispose();
        bulletPool.dispose();
        enemyShipPool.dispose();
        explosionPool.dispose();
        enemyEmitter.dispose();
        super.dispose();
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
        if(!mainShip.isDestroyed()) {
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
            explosionPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void checkCollisions(){
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemyShip: enemyShipList) {
            float minDistShip = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if(mainShip.pos.dst(enemyShip.pos) < minDistShip){
                mainShip.damage(enemyShip.getDamage());
                enemyShip.destroy();
            }
            for (Bullet bullet: bulletList) {
                if(bullet.getOwner() != mainShip){
                    continue;
                }
                if(enemyShip.isBulletCollisoin(bullet)){
                    enemyShip.damage(bullet.getDamage());
                    bullet.destroy();

                }
            }
        }
        for (Bullet bullet: bulletList) {
            if(bullet.getOwner() == mainShip){
                continue;
            }
            if(mainShip.isBulletCollisoin(bullet)){
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }
    }

    private void freeAllDestroyed(){
            bulletPool.freeAllDestroyedActiveSprite();
            enemyShipPool.freeAllDestroyedActiveSprite();
            explosionPool.freeAllDestroyedActiveSprite();
    }

    private void draw(){
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        bg.draw(batch);
        for (Star s: stars) {
            s.draw(batch);
        }
        if(!mainShip.isDestroyed()) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyShipPool.drawActiveSprites(batch);
            explosionPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
        }
        batch.end();
    }
}
