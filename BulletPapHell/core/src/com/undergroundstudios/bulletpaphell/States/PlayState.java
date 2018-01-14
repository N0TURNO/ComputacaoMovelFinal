package com.undergroundstudios.bulletpaphell.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.undergroundstudios.bulletpaphell.BulletPapHell;
import com.undergroundstudios.bulletpaphell.sprites.Button;
import com.undergroundstudios.bulletpaphell.sprites.Ship;
import com.undergroundstudios.bulletpaphell.sprites.bosses.Boss;

/**
 * Created by diogo on 08/01/2018.
 */

public class PlayState extends State {

    private Ship ship;
    private Boss boss;
    private Texture background;
    private Texture pausedBackground;
    private boolean moving = false;
    private boolean playing = true;
    private Button pauseButton;
    private float timePassed;

    private Music music;

    public PlayState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(true, BulletPapHell.WIDTH,BulletPapHell.HEIGHT);

        ship = new Ship(cam.viewportWidth/2 ,cam.viewportHeight -cam.viewportHeight/10,"ship.png",100,"healthShip.png",new Vector2(30,cam.viewportHeight/2),BulletPapHell.STRIPE,cam.viewportWidth);
        boss = new Boss(cam.viewportWidth/2,cam.viewportHeight/30,"boss1.png",100,"healthBoss.png",new Vector2(10,cam.viewportHeight/2));
        background = new Texture("background.png");
        pausedBackground = new Texture("pauseBackground.png");
        timePassed = 0;

        pauseButton = new Button("pause_button.png", new Vector2(30,30));

        music = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        music.setLooping(true);
        music.setVolume(0.01f);
        music.play();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){

            if(pauseButton.getButtonArea().contains(Gdx.input.getX(),Gdx.input.getY())){
                playing = false;
            }
            else if (playing){
                moving = true;
                ship.move(Gdx.input.getX(),cam.viewportWidth/2);
            }
            else if(!playing)
                playing = true;
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
        moving = false;

        if (playing){
            timePassed += dt;

            ship.update(dt);
            boss.update(dt);

            ship.battleManage(boss);
            boss.battleManage(ship);

            //If a ship dies
            if(boss.getHealth() <= 0 || ship.getHealth() <= 0){
                EndScreenState end = new EndScreenState(gsm);
                end.setBossHealth(boss.getHealth());
                end.setShipHealth(ship.getHealth());
                end.setTimePassed(timePassed);

                gsm.set(end);
                playing = false;
            }

            if(moving == false){
                ship.resetVelocity();
            }
        }

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background,0,0);

        boss.draw(sb);
        ship.draw(sb);

        pauseButton.draw(sb);

        if(!playing)
            sb.draw(pausedBackground,0,0);

        sb.end();
    }

    @Override
    public void dispose() {
        ship.dispose();
        boss.dispose();
        background.dispose();
        pauseButton.dispose();
        pausedBackground.dispose();
        music.dispose();
    }
}
