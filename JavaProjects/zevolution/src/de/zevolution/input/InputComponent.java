package de.zevolution.input;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool.Poolable;

/**
 * Most likely you will assign this component only to the player
 * @author David
 *
 */
public class InputComponent extends Component implements Poolable{

	public Vector2 direction = new Vector2();
	public boolean jump;
	
    @Override
    public void reset() {
    	direction.setZero();
    	jump = false;
    }
    
    
    
}
