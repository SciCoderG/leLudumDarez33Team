package de.zcience.Z1.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool;

/**
 * Add this to anything that should jump
 * @author David
 *
 */
public class JumpComponent extends Component implements Pool.Poolable{

	public float force = 0.0f;
	public float forceDown = 0.0f;
	public boolean jump = false;
	public int groundContacts = 0;
	
	@Override
	public void reset() {
		// TODO Auto-generated method stub
	}
}
