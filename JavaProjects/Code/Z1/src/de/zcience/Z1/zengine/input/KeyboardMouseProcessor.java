package de.zcience.Z1.zengine.input;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector3;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.game.Game;
import de.zcience.Z1.game.components.PlayerComponent;
import de.zcience.Z1.zengine.util.CompMappers;
import de.zcience.Z1.zengine.util.GameConstants;

public class KeyboardMouseProcessor implements InputProcessor {
	
	private void move(float plane, float up, float down) {
		for(Entity entity : EntityCreator.engine.getEntitiesFor(Family.all(InputComponent.class, PlayerComponent.class).get())) {
			InputComponent inputComponent = CompMappers.input.get(entity);
			inputComponent.x += plane;
		}
	}
	
	private void jump(boolean jump) {
		for(Entity entity : EntityCreator.engine.getEntitiesFor(Family.all(InputComponent.class, PlayerComponent.class).get())) {			InputComponent inputComponent = CompMappers.input.get(entity);
			inputComponent.jump = jump;
		}
	}
	
	/**
	 * TODO
	 * This does some important calculating between the screen coordinates and the box2d coordinates. definitely should not be here
	 * @param toShoot
	 * @param screenX
	 * @param screenY
	 */
	private void shoot(boolean toShoot, float screenX, float screenY){
		for(Entity entity : EntityCreator.engine.getEntitiesFor(Family.all(InputComponent.class, PlayerComponent.class).get())) {
			InputComponent inputComponent = CompMappers.input.get(entity);
			//PhysicsBodyComponent physicsBody = CompMappers.physicsBody.get(entity);
			
			/*
			 *TODO
			 * Here happens the magic of the calculation. Should be in CameraSystem
			 */
			Vector3 pos = EntityCreator.camSystem.getCamera().position.cpy();
			pos.scl(GameConstants.BOX2D_SCALE);
			pos.sub(Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight()/2.0f, 0);

			
			inputComponent.shoot = toShoot;
			inputComponent.shotDirection.set(pos.x + screenX, pos.y + Gdx.graphics.getHeight() - screenY);
		}
	}

	@Override
	public boolean keyDown(int keycode) {
		float plane = 0.0f;
		switch(keycode) {
			case Input.Keys.A: plane -= 1.0f; break;
			case Input.Keys.D: plane += 1.0f; break;
			case Input.Keys.W: this.jump(true); break;
			case Input.Keys.F5: Game.setDoDebugRendering(!Game.isDoDebugRendering());
		}
		this.move(plane, 0.0f, 0.0f);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		float plane = 0.0f;
		switch(keycode) {
			case Input.Keys.A: plane += 1.0f; break;
			case Input.Keys.D: plane -= 1.0f; break;
			case Input.Keys.W: this.jump(false); break;
		}
		this.move(plane, 0.0f, 0.0f);
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {			
		this.shoot(true, screenX, screenY);
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		this.shoot(false, screenX, screenY);
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		this.shoot(true, screenX, screenY);
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
