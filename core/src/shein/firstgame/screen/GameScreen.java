package shein.firstgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import shein.firstgame.base.BaseScreen;
import shein.firstgame.base.Font;
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
import shein.firstgame.sprite.StartNewGame;
import shein.firstgame.utils.EnemyEmitter;


public class GameScreen extends BaseScreen {

    private static final int STAR_COUNT = 25;
    private static final String FRAGS = "Frags:";
    private static final String HP = "HP:";
    private static final String LEVEL = "Level:";

    private enum State {PLAYING, PAUSE, GAME_OVER}

    private State state;
    private State prevState;

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
    private StartNewGame startNewGame;

    private Font font;
    private StringBuilder sbFrags;
    private StringBuilder sbHp;
    private StringBuilder sbLevel;

    private int frags;

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
        startNewGame = new StartNewGame(atlas, this);
        font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        sbFrags = new StringBuilder();
        sbHp = new StringBuilder();
        sbLevel = new StringBuilder();
        gameMusic.setVolume(0.7f);
        gameMusic.setLooping(true);
        gameMusic.play();
        state = State.PLAYING;
        prevState = State.PLAYING;
    }

    public void startNewGame(){
        state = State.PLAYING;

        frags = 0;
        mainShip.startNewGame(worldBounds);

        bulletPool.freeAllActiveSprite();
        enemyShipPool.freeAllActiveSprite();
        explosionPool.freeAllActiveSprite();
    }

    @Override
    public void render(float delta) {
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    @Override
    public void pause() {
        prevState = state;
        state = State.PAUSE;
        gameMusic.pause();
    }

    @Override
    public void resume() {
        state = prevState;
        gameMusic.play();
    }

    @Override
    public void resize(Rect worldBounds) {
        bg.resize(worldBounds);
        for (Star s: stars) {
            s.resize(worldBounds);
        }
        mainShip.resize(worldBounds);
        gameOver.resize(worldBounds);
        startNewGame.resize(worldBounds);
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
        if( state == State.PLAYING) {
            mainShip.touchDown(target, pointer);
        } else if(state == State.GAME_OVER) {
            startNewGame.touchDown(target, pointer);
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 target, int pointer) {
        if( state == State.PLAYING) {
            mainShip.touchUp(target, pointer);
        } else if(state == State.GAME_OVER) {
            startNewGame.touchUp(target, pointer);
        }
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
            enemyEmitter.generate(delta, frags);
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
                frags++;
                if(mainShip.isDestroyed()){
                    state = State.GAME_OVER;
                }
            }
            for (Bullet bullet: bulletList) {
                if(bullet.getOwner() != mainShip){
                    continue;
                }
                if(enemyShip.isBulletCollisoin(bullet)){
                    enemyShip.damage(bullet.getDamage());
                    if(enemyShip.isDestroyed()){
                        frags++;
                    }
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
                    state = State.GAME_OVER;
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
        } else if(state == State.GAME_OVER ){
            gameOver.draw(batch);
            startNewGame.draw(batch);
        }
        printInfo();
        batch.end();
    }

    private void printInfo(){
        sbFrags.setLength(0);
        float fragsPosX = worldBounds.getLeft() + 0.01f;
        float fragsPosY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbFrags.append(FRAGS).append(frags), fragsPosX, fragsPosY);
        sbHp.setLength(0);
        float hpPosX = worldBounds.pos.x ;
        float hpPosY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), hpPosX, hpPosY, Align.center);
        sbLevel.setLength(0);
        float levelPosX = worldBounds.getRight() - 0.01f;
        float levelPosY = worldBounds.getTop() - 0.01f;
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), levelPosX, levelPosY, Align.right);
    }
}
