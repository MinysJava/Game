package shein.firstgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private static final float V_LEN = 2f;

    private Texture img;
    private Texture rc;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 target;
    private Vector2 buff;

    @Override
    public void show() {
        super.show();
        img = new Texture("bg2048.jpg");
        rc = new Texture("redCircle.png");

        pos = new Vector2(304, 224);
        v = new Vector2();
        target = new Vector2(-15, -15);
        buff = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(img, 0, 0, 640, 480);
        batch.draw(rc, pos.x, pos.y, 32, 32);
        batch.end();
        buff.set(target);

        if (pos.x == target.x || pos.x <= -15 || pos.x >= 623) {
            v.x = 0;
        }
        if (pos.y == target.y || pos.y <= -15 || pos.y >= 463) {
            v.y = 0;
        }
        if (buff.sub(pos).len() > V_LEN){
            pos.add(v);
        } else {
            pos.set(target);
        }

    }

    @Override
    public void dispose() {
        super.dispose();
        batch.dispose();
        img.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        target.set(screenX - 16, Gdx.graphics.getHeight() - screenY - 16);

        v.set(target.cpy().sub(pos)).setLength(V_LEN);

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        super.touchDragged(screenX, screenY, pointer);
        pos.set(screenX - 16, Gdx.graphics.getHeight() - screenY - 16);
        v.set(0,0);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        if(keycode == 19){
            if (pos.y != 463) {
                pos.y = pos.y + 1;
            }
            v.y = v.y + 1;
        }
        if (keycode == 20){
            if (pos.y != -15) {
                pos.y = pos.y - 1;
            }
            v.y = v.y - 1;
        }
        if (keycode == 21){
            if (pos.x != -15) {
                pos.x = pos.x - 1;
            }
            v.x = v.x - 1;
        }
        if (keycode == 22){
            if (pos.x != 623) {
                pos.x = pos.x + 1;
            }
            v.x = v.x + 1;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
         super.keyUp(keycode);
        if(keycode == 19){
            v.y = v.y - 1;
        }
        if (keycode == 20){
            v.y = v.y + 1;
        }
        if (keycode == 21){
            v.x = v.x + 1;
        }
        if (keycode == 22){
            v.x = v.x - 1;
        }
        return false;
    }
}
