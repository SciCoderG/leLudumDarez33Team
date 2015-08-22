package de.zcience.Z1;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import de.zcience.Z1.game.Game;

public class Main {
	public static void main(String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "LudumDare_DT_2015";
        config.width = 1024;
        config.height = 576;
        config.vSyncEnabled=true;
        //config.resizable = false;
        new LwjglApplication(new Game(), config);
    }
}
