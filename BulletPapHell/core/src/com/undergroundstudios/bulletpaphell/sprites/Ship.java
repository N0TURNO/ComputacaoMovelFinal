package com.undergroundstudios.bulletpaphell.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.undergroundstudios.bulletpaphell.BulletPapHell;

import java.util.ArrayList;

/**
 * Created by diogo on 09/01/2018.
 */

public class Ship extends BattleShip {

    public static final float SHOOT_TIME = 0.25f;
    public static final float PROJECTILE_SPEED = -500f;
    public static final float PROJECTILE_DAMAGE= 0.5f;


    private Vector2 position;
    private Vector2 velocity;

    private float speed = 5;
    private float maxspeed = 5;
    private Texture ship;
    private float shootTimer;
    private Vector2 projectileVelocity;
    private float limitLeft,limitRight;

    private Rectangle hitBox;

    private ArrayList<Projectile> projectiles;

    private Texture healthBar;
    private Vector2 healthBarPosition;

    private Sound shot;

    public Ship(float x, float y, String textura, int health, String texturaVida, Vector2 healthBarPosition,float limitLeft, float limitRight){
        super(x,y,textura,health,texturaVida,healthBarPosition);

        shot = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));

        position = new Vector2(x, y);
        velocity = new Vector2(0,0);
        ship = new Texture(textura);

        shootTimer = 0;
        projectiles = new ArrayList<Projectile>();

        healthBar = new Texture(texturaVida);
        this.healthBarPosition = healthBarPosition;

        hitBox = new Rectangle(position.x,position.y,ship.getWidth(),ship.getHeight());

        this.limitLeft = limitLeft;
        this.limitRight = limitRight;
    }

    public void update(float dt){

        //Projéteis

        shootTimer += dt;

        if(shootTimer >= SHOOT_TIME){

            projectileVelocity = new Vector2(0, PROJECTILE_SPEED);


            shot.play(0.05f);
            projectiles.add(new Projectile(position.x + (ship.getWidth() / 10), position.y,
                    projectileVelocity, PROJECTILE_DAMAGE, "shipProjectile1.png"));

            projectiles.add(new Projectile(position.x + ship.getWidth() - (ship.getWidth() / 10), position.y,
                    projectileVelocity, PROJECTILE_DAMAGE, "shipProjectile1.png"));

            shootTimer -=SHOOT_TIME;
        }

        //Remove projectiles out fo bounds and update
        updateProjectiles(dt,projectiles);

        //Bounds do Ecrã
        if(position.x <= limitLeft)
            position.x = limitLeft;

        if(position.x + ship.getWidth() >= limitRight)
            position.x = limitRight - ship.getWidth();
    }

    public void move(float positionFinger,float screenMiddle){

        if(positionFinger > screenMiddle && velocity.x < maxspeed)
            velocity.add(5,0);

        if(positionFinger < screenMiddle && velocity.x > -maxspeed)
            velocity.add(-5,0);


        position.add(velocity);

        hitBox.setPosition(position);
    }

    public float magnitude(float x,float y){
        return (float)Math.sqrt(x * x + y*y);
    }

    public Vector2 normalize(Vector2 vector){
        float magnitude = magnitude(vector.x,vector.y);

        vector.x /= magnitude;
        vector.y /= magnitude;

        return vector;
    }

    public void draw(SpriteBatch sb){
        sb.draw(ship, position.x,position.y);
        sb.draw(healthBar,healthBarPosition.x,healthBarPosition.y);

        for (Projectile projectile : projectiles){
            projectile.draw(sb);
        }
    }

    @Override
    public void battleManage(BattleShip enemy)
    {
        battleManager(enemy,projectiles);
    }

    public void dispose(){
        ship.dispose();
        healthBar.dispose();
    }

    public void resetVelocity(){
        velocity.x = 0;
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
}
