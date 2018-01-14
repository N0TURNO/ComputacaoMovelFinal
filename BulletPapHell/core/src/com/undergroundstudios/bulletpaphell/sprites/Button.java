package com.undergroundstudios.bulletpaphell.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by diogo on 12/01/2018.
 */

public class Button {

    private Texture buttonTexture;
    private Vector2 buttonPosition;
    private Rectangle buttonArea;

    public Button(String buttonTexture, Vector2 buttonPosition) {

        this.buttonTexture = new Texture(buttonTexture);
        this.buttonPosition = new Vector2(buttonPosition.x - this.buttonTexture.getWidth()/2,buttonPosition.y-this.buttonTexture.getHeight()/2);

        buttonArea = new Rectangle(this.buttonPosition.x,this.buttonPosition.y,this.buttonTexture.getWidth(),this.buttonTexture.getHeight());
    }

    public void draw(SpriteBatch sb){
        sb.draw(buttonTexture,buttonPosition.x,buttonPosition.y);
    }

    public void dispose(){
        buttonTexture.dispose();
    }

    public Rectangle getButtonArea() {
        return buttonArea;
    }
}
