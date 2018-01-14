package com.undergroundstudios.bulletpaphell.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/**
 * Created by diogo on 11/01/2018.
 */

public class BattleShip {

    private ArrayList<Projectile> projectiles;
    private Texture boss;
    private float health;
    private Rectangle hitBox;

    private int maxHealth;
    private Texture healthBar;
    private Vector2 healthBarPosition;
    private float percentagemVida;


    public BattleShip(float x, float y, String textura, int health, String texturaVida, Vector2 healthBarPosition) {
        this.health = health;
        boss = new Texture(textura);
        projectiles = new ArrayList<Projectile>();

        healthBar = new Texture(texturaVida);
        maxHealth = health;
        percentagemVida = healthBar.getHeight()/maxHealth;
        this.healthBarPosition = healthBarPosition;
    }

    public void updateProjectiles(float dt, ArrayList<Projectile> projectiles){
        //Remove projectiles out fo bounds and update
        ArrayList<Projectile> projectilesToRemove = new ArrayList<Projectile>();
        for (Projectile projectile : projectiles){
            projectile.update(dt);

            if(projectile.remove)
                projectilesToRemove.add(projectile);
        }
        projectiles.removeAll(projectilesToRemove);
    }

    public void battleManager(BattleShip enemy, ArrayList<Projectile> projectiles){

        ArrayList<Projectile> projectilesToRemove = new ArrayList<Projectile>();
        for (Projectile projectile:projectiles){
            if(projectile.getHitBox().overlaps(enemy.getHitBox())){
                enemy.setHealth(enemy.getHealth() - projectile.getDamage());
                projectilesToRemove.add(projectile);
                enemy.setHealthBarPosition(updateBarraVida(projectile.getDamage(),enemy.getPercentagemVida(), enemy.getHealthBarPosition()));
            }
        }
        projectiles.removeAll(projectilesToRemove);

    }

    public void battleManage(BattleShip enemy){}

    public Vector2 updateBarraVida(float vida , float percentagemBarra, Vector2 position){
        return (position.add(0,vida*percentagemBarra));
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public Vector2 getHealthBarPosition() {
        return healthBarPosition;
    }

    public float getPercentagemVida() {
        return percentagemVida;
    }

    public void setHealthBarPosition(Vector2 healthBarPosition) {
        this.healthBarPosition = healthBarPosition;
    }
}
