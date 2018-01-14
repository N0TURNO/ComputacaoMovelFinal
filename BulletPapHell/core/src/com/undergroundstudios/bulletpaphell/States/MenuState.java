package com.undergroundstudios.bulletpaphell.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.undergroundstudios.bulletpaphell.BulletPapHell;
import com.undergroundstudios.bulletpaphell.sprites.Button;

/**
 * Created by diogo on 03/01/2018.
 */

public class MenuState extends State {

    private Texture background;

    private Button playButton;

    public MenuState(GameStateManager gsm)
    {
        super(gsm);
        cam.setToOrtho(true, BulletPapHell.WIDTH,BulletPapHell.HEIGHT);
        background = new Texture("mainMenuBackground2.png");
        playButton = new Button("play_button.png", new Vector2(cam.viewportWidth/2,cam.viewportHeight/2 + 100));
        cam.position.set(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2, 0);
    }

    @Override
    public void handleInput() {
        if(Gdx.input.isTouched()){

            if(playButton.getButtonArea().contains(Gdx.input.getX(),Gdx.input.getY()))
            gsm.set(new PlayState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,0,0);
        playButton.draw(sb);
        sb.end();
    }

    @Override
    public void dispose(){
        background.dispose();
        playButton.dispose();
    }
}
