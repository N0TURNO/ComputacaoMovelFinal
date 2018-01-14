package com.undergroundstudios.bulletpaphell.sprites.bosses;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.undergroundstudios.bulletpaphell.BulletPapHell;
import com.undergroundstudios.bulletpaphell.sprites.BattleShip;
import com.undergroundstudios.bulletpaphell.sprites.Projectile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by diogo on 09/01/2018.
 */

public class Boss extends BattleShip {

    public static final float SHOOT_TIME = 0.01f;
    public static final float SHOOT_TIME_2 = 0.7f;
    public static final float ATTACK_MODE = 5;
    public static final float PROJECTILE_SPEED = 200;
    public static final float PROJECTILE_SPEED_2 = 400;

    public static final float PROJECTILE_DAMAGE= 5f;
    public static final float PROJECTILE_DAMAGE_2= 20f;

    private Vector2 position;
    private Vector2 velocity;

    private double angle = 0;
    private double angleIncrease = 10;
    private float shootTimer, attackTimer,shootTimer2;
    private boolean isShooting, isMoving;

    private Vector2 projectileVelocity;

    private ArrayList<Projectile> projectiles;
    private Texture boss;
    private Rectangle hitBox;

    private Texture healthBar;
    private Vector2 healthBarPosition;

    private Sound shot1,shot2;

    public Boss(float x, float y, String textura, int health, String texturaVida, Vector2 healthBarPosition) {
        super(x,y,textura,health,texturaVida,healthBarPosition);

        position = new Vector2(x, y);
        velocity = new Vector2(2, 0);
        shootTimer = attackTimer = 0;
        isShooting = false;
        isMoving = true;

        boss = new Texture(textura);
        projectiles = new ArrayList<Projectile>();

        healthBar = new Texture(texturaVida);
        this.healthBarPosition = healthBarPosition;

        hitBox = new Rectangle(position.x,position.y,boss.getWidth(),boss.getHeight());

        shot1 = Gdx.audio.newSound(Gdx.files.internal("shot.wav"));
        shot2 = Gdx.audio.newSound(Gdx.files.internal("shot2.ogg"));
    }

    public void update(float dt) {

        //Time setter
        if(isShooting)
            shootTimer += dt;
        if(!isShooting)
            shootTimer2 += dt;
        attackTimer += dt;

        //Change Attack Mode
        if(attackTimer >= ATTACK_MODE && isMoving && !isShooting){
            isMoving =false;
            isShooting = true;

            attackTimer-=ATTACK_MODE;
        }

        if(attackTimer >= ATTACK_MODE && !isMoving && isShooting){
            isMoving = true;
            isShooting = false;

            attackTimer-=ATTACK_MODE;
        }

        //Shooting algorithm
        angle += angleIncrease;

        if(shootTimer >= SHOOT_TIME && isShooting) {

            for(int i = 0; i < 4; i++) {
                projectileVelocity = ChangeVelocity((float) Math.toRadians(angle + i*90), PROJECTILE_SPEED);

                projectiles.add(new Projectile(position.x + (boss.getWidth() / 2), position.y + boss.getHeight(),
                        projectileVelocity, PROJECTILE_DAMAGE, "bossProjectile1.png"));

            }
            shootTimer -=SHOOT_TIME;
        }

        if(shootTimer2 >= SHOOT_TIME_2 && !isShooting){

            projectileVelocity = new Vector2(0, PROJECTILE_SPEED_2);

            shot1.play(0.05f);

            projectiles.add(new Projectile(position.x + (boss.getWidth() / 10), position.y + boss.getHeight(),
                    projectileVelocity, PROJECTILE_DAMAGE_2, "bossProjectile2.png"));

            projectiles.add(new Projectile(position.x + boss.getWidth() - (boss.getWidth() / 10), position.y + boss.getHeight(),
                    projectileVelocity, PROJECTILE_DAMAGE_2, "bossProjectile2.png"));

            shootTimer2 -=SHOOT_TIME_2;
        }

        //Remove projectiles out fo bounds and update
        updateProjectiles(dt,projectiles);

        //Boss Movement
        if(position.x <= BulletPapHell.STRIPE){
            position.x = BulletPapHell.STRIPE;
            velocity.x *= -1;
        }
        if(position.x + boss.getWidth() >= BulletPapHell.WIDTH){
            position.x = BulletPapHell.WIDTH - boss.getWidth();
            velocity.x *= -1;
        }

        if(isMoving)
            position.add(velocity);

        hitBox.setPosition(position);
    }

    public void draw(SpriteBatch sb){
        sb.draw(boss, position.x,position.y);
        sb.draw(healthBar,healthBarPosition.x,healthBarPosition.y);

        for (Projectile projectile : projectiles){
            projectile.draw(sb);
        }
    }

    public void dispose(){
        boss.dispose();
        healthBar.dispose();
    }

    public Vector2 ChangeVelocity(float x,float y)
    {
        return(new Vector2((float)Math.sin(x) * y,(float)Math.cos(x) * y));
    }

    @Override
    public void battleManage(BattleShip enemy)
    {
        battleManager(enemy,projectiles);
    }

    @Override
    public Rectangle getHitBox() {
        return hitBox;
    }
}