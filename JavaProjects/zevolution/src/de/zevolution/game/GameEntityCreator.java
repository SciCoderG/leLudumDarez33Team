package de.zevolution.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.utils.Array;

import de.zevolution.EntityCreator;
import de.zevolution.input.InputComponent;
import de.zevolution.menu.MenuInputProcessor;
import de.zevolution.movement.MovementComponent;
import de.zevolution.physics.components.PhysicsBodyComponent;
import de.zevolution.physics.utils.PhysicsBodyDef;
import de.zevolution.physics.utils.PhysicsFixtureDef;

public class GameEntityCreator extends EntityCreator {

	public static List<Entity> loadLevel() {
		unLoadMenus();
		
		List<Entity> toReturn = new ArrayList<Entity>();
		Entity player = createPlayer(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() - 100.0f);
		toReturn.add(player);

		return toReturn;
	}

	private static Entity createPlayer(float x, float y) {
		Entity entity = engine.createEntity();
		
		// Creating the PhysicsBodyComponent
		float width = 100.0f;
		float height = 100.0f;

		PhysicsBodyComponent physicsBody = engine
				.createComponent(PhysicsBodyComponent.class);
		PhysicsBodyDef bodyDef = new PhysicsBodyDef(BodyType.DynamicBody,
				physicsSystem).fixedRotation(true).position(x, y);
		physicsBody.init(bodyDef, physicsSystem, entity);
		
		PhysicsFixtureDef fixtureDef = new PhysicsFixtureDef(physicsSystem).shapeBox(width, height);
		
		physicsBody.createFixture(fixtureDef);
		
		entity.add(physicsBody);
		
		//Creating the InputComponent
		InputComponent input = engine.createComponent(InputComponent.class);
		entity.add(input);
		
		// Creating the MovementComponent
		MovementComponent movement = engine.createComponent(MovementComponent.class);
		movement.speed = 2.0f;
		entity.add(movement);
		
		engine.addEntity(entity);
		return entity;
	}
	
	/**
	 * Get rid of any entities constructed by menus, unnecessary InputProcessors and Systems
	 * TODO: the menuInputProcessor will later be given by a Manager class --> you don't have
	 * to iterate through the InputProcessors of the InputMultiplexer anymore to remove the Menu
	 * InputProcessor
	 */
	private static void unLoadMenus(){
		// first: remove all entities
		engine.removeAllEntities();
		
		// get the input processors
		InputMultiplexer multi =  (InputMultiplexer) Gdx.input.getInputProcessor();
		Array<InputProcessor> processors = multi.getProcessors();
		// remove the MenuInputProcessor
		for(InputProcessor toRemove : processors){
			if(toRemove.getClass().equals(MenuInputProcessor.class)){
				multi.removeProcessor(toRemove);
			}
		}
	}
}
