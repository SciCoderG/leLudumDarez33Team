package de.zcience.Z1.zengine.physics;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * position in pixel. Important, 1pixel != 1meter in Box2D!
 * Position is the Center of the Body!!
 * @author David
 *
 */
public class PositionComponent extends Component implements Poolable{

	public enum FACING{
		UP, RIGHT, DOWN, LEFT;
	}
	
    public float x, y;
    public FACING direction;
    
    @Override
    public void reset() {
        x = y = 0;
    }

}
