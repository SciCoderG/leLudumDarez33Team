package de.zcience.Z1.zengine.input;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public class InputComponent extends Component implements Pool.Poolable {
	
	public float x = 0.0f;
	public float y = 0.0f;
	
	public boolean jump = false;
	public boolean shoot = false;
	
	public float shootTimer = 0f;
	public float shootTimerMax = 0f;
	public Vector2 shotDirection = new Vector2();
	
	@Override
	public void reset() {
		this.x = 0.0f;
		this.y = 0.0f;
		this.shootTimer = shootTimerMax = 0f;
		
		this.jump = shoot = false;
		this.shotDirection.setZero();
	}

}
