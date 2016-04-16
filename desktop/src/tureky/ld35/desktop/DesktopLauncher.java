package tureky.ld35.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import turkey.ld35.GameCore;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "LudumDare 35";
		config.width = 1280;
		config.height = 768;
		new LwjglApplication(new GameCore(), config);
	}
}
