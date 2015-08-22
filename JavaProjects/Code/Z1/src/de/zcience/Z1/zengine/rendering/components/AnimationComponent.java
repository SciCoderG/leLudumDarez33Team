package de.zcience.Z1.zengine.rendering.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.utils.Pool.Poolable;

public class AnimationComponent extends Component implements Poolable{

	public float stateTime = 0.0f;
	public float width = -1.0f, height = -1.0f;
	public Animation animation = null;
	@Override
	public void reset() {
		animation = null;	
		stateTime = 0.0f;
		width = height = -1.0f;
	}

}
