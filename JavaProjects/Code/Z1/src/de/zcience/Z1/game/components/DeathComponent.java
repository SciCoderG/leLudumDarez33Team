package de.zcience.Z1.game.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Add this to anything that should die. Standard timer is one second
 * @author David
 *
 */
public class DeathComponent extends Component implements Poolable{

	public float timer = 1.0f;;
	
	@Override
	public void reset() {
		timer = 1.0f;
	}

}
