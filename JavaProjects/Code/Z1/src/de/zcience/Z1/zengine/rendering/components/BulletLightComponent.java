package de.zcience.Z1.zengine.rendering.components;

import box2dLight.PointLight;
import box2dLight.RayHandler;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.physics.box2d.Body;

import de.zcience.Z1.zengine.rendering.LightSystem;

/**
 * Use this class for lighting up bullets. It will speed up the bullet-creation!
 * @author David
 *
 */
public class BulletLightComponent extends LightComponent{

	public void setLight(RayHandler rayhandler, int rays, Color color, float distance, float x, float y, Body body){
		if(light!= null){
			light.setActive(true);
			light.setColor(color);
			light.setDistance(distance);
			
		}else{
			light = new PointLight(LightSystem.rayHandler, 8,
					new Color(0.7f, 0.0f, 0.0f, 1.0f), 1, x, y);
		}
		light.setXray(true);
		light.attachToBody(body);
	}
	
	@Override
	public void reset() {
		light.setActive(false);
	}
}
