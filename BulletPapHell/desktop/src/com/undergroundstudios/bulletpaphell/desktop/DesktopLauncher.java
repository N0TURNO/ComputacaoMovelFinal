package com.undergroundstudios.bulletpaphell.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.undergroundstudios.bulletpaphell.BulletPapHell;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = BulletPapHell.WIDTH;
		config.height =BulletPapHell.HEIGHT;
		config.title = BulletPapHell.TITLE;
		new LwjglApplication(new BulletPapHell(), config);
	}
}
