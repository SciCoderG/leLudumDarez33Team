package de.zcience.Z1.zengine.rendering.components;

import box2dLight.PositionalLight;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

public class LightComponent extends Component implements Pool.Poolable{

	public PositionalLight light;
	
    @Override
    public void reset() {
    	light.remove();
    	light.dispose();
    	light = null;
    }
}
