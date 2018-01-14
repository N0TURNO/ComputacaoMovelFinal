package com.undergroundstudios.bulletpaphell.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.undergroundstudios.bulletpaphell.BulletPapHell;

/**
 * Created by diogo on 10/01/2018.
 */

public class Projectile {

    private Vector2 position;
    private Vector2 velocity;
    private Texture projectile;
    public boolean remove = false;

    private Rectangle hitBox;
    private float damage;

    public Projectile(float x,float y, Vector2 velocity, float damage, String projectile) {
        position = new Vector2(x,y);
        this.velocity = velocity;
        this.damage = damage;
        this.projectile = new Texture(projectile);

        position.add(-this.projectile.getWidth()/2,-this.projectile.getHeight()/2);
        hitBox = new Rectangle(position.x,position.y,this.projectile.getWidth(),this.projectile.getHeight());
    }

    public void update(float dt){
        position.add(velocity.x * dt,velocity.y *dt);

        hitBox.setPosition(position);

        if(position.y < 0 || position.y > BulletPapHell.HEIGHT || position.x <0 || position.x > BulletPapHell.WIDTH)
            remove = true;
    }

    public void draw (SpriteBatch sp)
    {
        sp.draw(projectile,position.x, position.y);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public float getDamage() {
        return damage;
    }
}
