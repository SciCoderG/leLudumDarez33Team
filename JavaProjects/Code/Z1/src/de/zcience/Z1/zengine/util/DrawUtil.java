package de.zcience.Z1.zengine.util;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DrawUtil {
	public static SpriteBatch batch = new SpriteBatch();
	
//	setProjectionMatrix(engine.getSystem(CameraSystem.class)
//			.getCombinedMatrix());
	public static void dispose(){
		batch.dispose();
	}
	
}
