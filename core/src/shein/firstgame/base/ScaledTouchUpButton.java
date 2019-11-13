package shein.firstgame.base;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public abstract class ScaledTouchUpButton extends Sprite{

    private int pointer;
    private boolean pressed;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 target, int pointer) {
        if(pressed || !isMe(target)){
            return false;
        }
        pressed = true;
        scale = 0.9f;
        this.pointer = pointer;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 target, int pointer) {
        if(this.pointer != pointer || !pressed){
            return false;
        }
        if(isMe(target)){
            action();
        }
        pressed = false;
        scale = 1f;

        return false;
    }

    public abstract void action();
}
