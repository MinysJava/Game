package shein.firstgame.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.math.Rect;
import shein.firstgame.math.Rnd;
import shein.firstgame.pool.EnemyShipPool;
import shein.firstgame.sprite.EnemyShip;

public class EnemyEmitter {

    private static final float SMALL_BULLET_HEIGHT = 0.01f;
    private static final float SMALL_BULLET_VY = -0.3f;
    private static final int SMALL_BULLET_DAMAGE = 1;
    private static final float SMALL_RELOAD_INTERVAL = 3f;
    private static final float SMALL_HEIGHT = 0.1f;
    private static final int SMALL_HP = 1;

    private static final float MIDDLE_BULLET_HEIGHT = 0.02f;
    private static final float MIDDLE_BULLET_VY = -0.25f;
    private static final int MIDDLE_BULLET_DAMAGE = 5;
    private static final float MIDDLE_RELOAD_INTERVAL = 4f;
    private static final float MIDDLE_HEIGHT = 0.15f;
    private static final int MIDDLE_HP = 5;

    private static final float BIG_BULLET_HEIGHT = 0.03f;
    private static final float BIG_BULLET_VY = -0.2f;
    private static final int BIG_BULLET_DAMAGE = 10;
    private static final float BIG_RELOAD_INTERVAL = 2f;
    private static final float BIG_HEIGHT = 0.2f;
    private static final int BIG_HP = 10;

    private static final float GENERATE_INTERVAL = 4f;

    private float generateInterval = GENERATE_INTERVAL;
    private float generateTimer;

    private EnemyShipPool enemyShipPool;
    private Rect worldBounds;

    private TextureRegion[] enemySmallRegions;
    private TextureRegion[] enemyMiddleRegions;
    private TextureRegion[] enemyBigRegions;
    private TextureRegion bulletRegion;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private Vector2 enemyMiddleV = new Vector2(0, -0.03f);
    private Vector2 enemyBigV = new Vector2(0, -0.005f);

    private int level = 1;

    private Sound shootSound;

    public EnemyEmitter(EnemyShipPool enemyShipPool, TextureAtlas atlas, Rect worldBounds) {
        this.enemyShipPool = enemyShipPool;
        this.worldBounds = worldBounds;
        enemySmallRegions = Regions.split(atlas.findRegion("enemy0"), 1, 2, 2);
        enemyMiddleRegions = Regions.split(atlas.findRegion("enemy1"), 1, 2, 2);
        enemyBigRegions = Regions.split(atlas.findRegion("enemy2"), 1, 2, 2);
        bulletRegion = atlas.findRegion("bulletEnemy");
        shootSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
    }

    public void generate(float delta,int frags) {
        level = frags / 10 + 1;
        generateInterval = GENERATE_INTERVAL / level ;
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyShipPool.obtain();
            float type = (float) Math.random();
            if (type < 0.5f) {
                enemyShip.set(
                        enemySmallRegions,
                        enemySmallV,
                        bulletRegion,
                        SMALL_BULLET_HEIGHT,
                        SMALL_BULLET_VY,
                        SMALL_BULLET_DAMAGE,
                        SMALL_RELOAD_INTERVAL,
                        shootSound,
                        SMALL_HEIGHT,
                        SMALL_HP
                );
            } else if (type < 0.8f) {
                enemyShip.set(
                        enemyMiddleRegions,
                        enemyMiddleV,
                        bulletRegion,
                        MIDDLE_BULLET_HEIGHT,
                        MIDDLE_BULLET_VY,
                        MIDDLE_BULLET_DAMAGE,
                        MIDDLE_RELOAD_INTERVAL,
                        shootSound,
                        MIDDLE_HEIGHT,
                        MIDDLE_HP
                );
            } else {
                enemyShip.set(
                        enemyBigRegions,
                        enemyBigV,
                        bulletRegion,
                        BIG_BULLET_HEIGHT,
                        BIG_BULLET_VY,
                        BIG_BULLET_DAMAGE,
                        BIG_RELOAD_INTERVAL,
                        shootSound,
                        BIG_HEIGHT,
                        BIG_HP
                );
            }
            enemyShip.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemyShip.getHalfWidth(), worldBounds.getRight() - enemyShip.getHalfWidth());
            enemyShip.setBottom(worldBounds.getTop());
        }
    }

    public void dispose() {
        shootSound.dispose();
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
