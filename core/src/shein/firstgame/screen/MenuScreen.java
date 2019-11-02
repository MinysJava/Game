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

    @Override
    public void show() {
        super.show();
        img = new Texture("bg2048.jpg");
        rc = new Texture("redCircle.png");

        pos = new Vector2(304, 224);
        v = new Vector2(0,0);

        if (Gdx.graphics.getWidth() > pos.x + rc.getWidth()){
            System.out.println(1);
        } else {
            System.out.println(2);
        }

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
        System.out.println(screenX + "; " + (Gdx.graphics.getHeight() - screenY));


        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        if(keycode == 19){
            v.y = v.y + 2;
        }
        if (keycode == 20){
            v.y = v.y - 2;
        }
        if (keycode == 21){
            v.x = v.x - 2;
        }
        if (keycode == 22){
            v.x = v.x + 2;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
         super.keyUp(keycode);
        if(keycode == 19){
            v.y = v.y - 2;
        }
        if (keycode == 20){
            v.y = v.y + 2;
        }
        if (keycode == 21){
            v.x = v.x + 2;
        }
        if (keycode == 22){
            v.x = v.x - 2;
        }
        return false;
    }
}
