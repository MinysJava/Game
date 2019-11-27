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

    private enum State {PLAYING, PAUSE, GAMW_OVER}

    private State state;

    private Texture img;
    private TextureAtlas atlas;
    private Backgroung bg;

    private Star[] stars;
    private MainShip mainShip;

    private BulletPool bulletPool;
    private EnemyShipPool enemyShipPool;
    private ExplosionPool explosionPool;

    private EnemyEmitter enemyEmitter;

    private Music gameMusic;

    private GameOver gameOver;

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
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        enemyShipPool = new EnemyShipPool(worldBounds,explosionPool, bulletPool);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyEmitter = new EnemyEmitter(enemyShipPool, atlas, worldBounds);
        gameOver = new GameOver(atlas);
        gameMusic.setVolume(0.7f);
        gameMusic.setLooping(true);
        gameMusic.play();
        state = State.PLAYING;
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
        explosionPool.updateActiveSprites(delta);
        if(state == State.PLAYING){
            mainShip.update(delta);
            bulletPool.updateActiveSprites(delta);
            enemyShipPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void checkCollisions(){
        if(state != State.PLAYING){
            return;
        }
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (EnemyShip enemyShip: enemyShipList) {
            float minDistShip = enemyShip.getHalfWidth() + mainShip.getHalfWidth();
            if(mainShip.pos.dst(enemyShip.pos) < minDistShip){
                mainShip.damage(enemyShip.getDamage());
                enemyShip.destroy();
                if(mainShip.isDestroyed()){
                    state = State.GAMW_OVER;
                }
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
                if(mainShip.isDestroyed()){
                    state = State.GAMW_OVER;
                }
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
        explosionPool.drawActiveSprites(batch);
        if(state == State.PLAYING) {
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyShipPool.drawActiveSprites(batch);
        } else if(state == State.GAMW_OVER ){
            gameOver.draw(batch);
        }
        batch.end();
    }

    private void startNewGame(){
        state = State.PLAYING;

        mainShip.startNewGame(worldBounds);

        bulletPool.freeAllActiveSprite();
        enemyShipPool.freeAllActiveSprite();
        explosionPool.freeAllActiveSprite();

    }
}
