package shein.firstgame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import shein.firstgame.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture rc;
    private Vector2 pos;
    private Vector2 v;
    private Vector2 target;

    @Override
    public void show() {
        super.show();
        img = new Texture("bg2048.jpg");
        rc = new Texture("redCircle.png");

        pos = new Vector2(304, 224);
        v = new Vector2();
        target = new Vector2(0,0);


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

        if (pos.x == target.x || pos.x == 0 || pos.x == 608) {
            v.x = 0;
        }
        if (pos.y == target.y || pos.y == 0 || pos.y == 448) {
            v.y = 0;

        }
        pos.add(v);

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

        if (pos.x < target.x){
            pos.x = pos.x + 1;
            v.x = 1;
        } else if( pos.x > target.x){
            v.x = -1;
            pos.x = pos.x - 1;
        }
        if (pos.y < (Gdx.graphics.getHeight() - screenY - 16)){
            pos.y = pos.y + 1;
            v.y = 1;
        } else if (pos.y > (Gdx.graphics.getHeight() - screenY - 16)){
            pos.y = pos.y - 1;
            v.y = -1;
        }
//        pos.set(screenX - 16, Gdx.graphics.getHeight() - screenY - 16); Перемещает объект по координаиам клика мышки

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
            if (pos.y != 448) {
                pos.y = pos.y + 1;
            }
            v.y = v.y + 1;

        }
        if (keycode == 20){
            if (pos.y != 0) {
                pos.y = pos.y - 1;
            }
            v.y = v.y - 1;

        }
        if (keycode == 21){
            if (pos.x != 0) {
                pos.x = pos.x - 1;
            }
            v.x = v.x - 1;
        }
        if (keycode == 22){
            if (pos.x != 608) {
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
