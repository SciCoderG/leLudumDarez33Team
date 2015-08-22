package de.zevolution.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

import de.zevolution.CompMappers;
import de.zevolution.EntityCreator;
import de.zevolution.physics.utils.Directions;



public class InputKeyboardSystem extends IteratingSystem implements InputProcessor{
	
	private boolean jump;
	private boolean up, down, left, right;
	
	

    public InputKeyboardSystem(int priority) {
        super(Family.all(InputComponent.class).get(), priority);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
    	InputComponent input = CompMappers.input.get(entity);
    	
    	// reset the component before putting new input into it
    	input.reset();
    	
    	input.jump = this.jump;
    	
    	if(up){
    		input.direction.add(Directions.UP);
    	}
    	if(down){
    		input.direction.add(Directions.DOWN);
    	}
    	if(left){
    		input.direction.add(Directions.LEFT);
    	}
    	if(right){
    		input.direction.add(Directions.RIGHT);
    	}
    	
    }

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Input.Keys.UP:
		case Input.Keys.W:
			up = true;
			jump = true;
			break;
		case Input.Keys.DOWN:
		case Input.Keys.S:
			down = true;
			break;
		case Input.Keys.LEFT:
		case Input.Keys.A:
			left = true;
			break;
		case Input.Keys.RIGHT:
		case Input.Keys.D:
			right = true;
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Input.Keys.UP:
		case Input.Keys.W:
			up = false;
			jump = false;
			break;
		case Input.Keys.DOWN:
		case Input.Keys.S:
			down = false;
			break;
		case Input.Keys.LEFT:
		case Input.Keys.A:
			left = false;
			break;
		case Input.Keys.RIGHT:
		case Input.Keys.D:
			right = false;
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
