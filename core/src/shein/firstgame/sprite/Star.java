package shein.firstgame.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.Sprite;
import shein.firstgame.math.Rect;
import shein.firstgame.math.Rnd;

public class Star extends Sprite {

    private Vector2 v = new Vector2();
    private Rect worldBounds;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));
        setHeightProportion(Rnd.nextFloat(0.01f, 0.0005f));
        float vy = Rnd.nextFloat(-0.005f, -0.001f);
        float vx = Rnd.nextFloat(-0.0005f, 0.0005f);
        v.set(vx, vy);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX, posY);
    }

    @Override
    public void update(float delta) {
        pos.add(v);
        checkBounds();
    }

    private void checkBounds(){
        if(getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
        if(getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
        if(getBottom() > worldBounds.getTop()) setTop(worldBounds.getTop());
        if(getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
    }
}
