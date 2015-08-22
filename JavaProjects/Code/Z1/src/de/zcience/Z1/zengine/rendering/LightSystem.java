package de.zcience.Z1.zengine.rendering;

import box2dLight.Light;
import box2dLight.RayHandler;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.physics.box2d.World;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.zengine.profiling.ProfilerGlobal;

public class LightSystem extends EntitySystem {

	public static RayHandler rayHandler;

	public LightSystem(int priority) {
		super(priority);
		Light.setContactFilter(EntityCreator.LIGHT, (short) 0, EntityCreator.WORLDOBJECT);
		LightSystem.rayHandler = new RayHandler(EntityCreator.physicsSystem.getWorld(), 200, 120);
		LightSystem.rayHandler.setCombinedMatrix(EntityCreator.camSystem
				.getCamera());
		LightSystem.rayHandler.setShadows(true);
		LightSystem.rayHandler.setAmbientLight(0.0f);
		LightSystem.rayHandler.setBlurNum(1);
	}

	@Override
	public void update(float deltaTime) {
//		ProfilerGlobal.startTime();
		rayHandler.setCombinedMatrix(EntityCreator.camSystem
				.getCamera().combined);
		rayHandler.updateAndRender();
		super.update(deltaTime);
//		ProfilerGlobal.endTime();
//		ProfilerGlobal.outMax("light-");
	}

	public static void setRayHandler(World world) {
		rayHandler.dispose();
		rayHandler = new RayHandler(world);
	}

}
