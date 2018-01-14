package com.undergroundstudios.bulletpaphell.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.undergroundstudios.bulletpaphell.BulletPapHell;
import com.undergroundstudios.bulletpaphell.sprites.Button;

/**
 * Created by diogo on 13/01/2018.
 */

public class EndScreenState extends State {

    private float timePassed;
    private float shipHealth;
    private float bossHealth;
    private float timePoints,shipPoint,bossPoints,totalPoints;

    private Texture background;
    private BitmapFont bitmapFont;
    private String text;
    private boolean oneCycle = true;

    private Button back;

    public EndScreenState(GameStateManager gsm) {
        super(gsm);
        cam.setToOrtho(true, BulletPapHell.WIDTH,BulletPapHell.HEIGHT);
        background = new Texture("backgroundEnd.jpg");

        back = new Button("back_button.png",new Vector2(30,30));

        bitmapFont = new BitmapFont(Gdx.files.internal("font.fnt"),true);
        text="a";
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()) {
            if (back.getButtonArea().contains(Gdx.input.getX(), Gdx.input.getY())) {

                gsm.set(new MenuState(gsm));
            }
        }
    }

    @Override
    public void update(float dt) {

        handleInput();

        if(oneCycle)
        updateStats();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,0,0);

        bitmapFont.draw(sb,text,75,200);

        back.draw(sb);
        sb.end();
    }

    @Override
    public void dispose() {

    }

    public void updateStats(){
        if(bossHealth >= shipHealth) {
            text = "You Lost";
        }
        else {

            timePoints = - (timePassed * 50);
            shipPoint = shipHealth * 20;
            bossPoints = 5000;
            totalPoints = timePoints + shipPoint + bossPoints;

            text = "Congratulations!!" +
                    "\n" +
                    "\nTime Point : " + (int)timePoints +
                    "\nHealth Points : " + (int)shipPoint +
                    "\nBoss Points : " + (int)bossPoints +
                    "\n" +
                    "\nTotal points : " + (int)totalPoints;

        }
        oneCycle = false;
    }

    public void setTimePassed(float timePassed) {
        this.timePassed = timePassed;
    }

    public void setShipHealth(float shipHealth) {
        this.shipHealth = shipHealth;
    }

    public void setBossHealth(float bossHealth) {
        this.bossHealth = bossHealth;
    }
}
