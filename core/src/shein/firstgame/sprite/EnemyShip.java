package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;

public class EnemyShip extends Sprite {

    private Rect worldBounds;
    private Vector2 v = new Vector2(0.01f, 0);
//    private Vector2 v0 = new Vector2(0,0);
    private int i = 0;
    private int countLoop = 0;

    public EnemyShip(TextureAtlas atlas) {
        super(atlas.findRegion("enemy0"), 1, 2, 2);
    }

    @Override
    public void update(float delta) {
        move();

    }

    private void move() {
        pos.mulAdd(v, 0.3f);

        if(countLoop < 1){
            v.rotate(-1);
            i++;
            if(i % 360 == 0){
                countLoop++;
            }
        } else {
            v.set(0.01f, 0);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(0.10f);
        setTop(worldBounds.getTop());
    }
}
