package de.zcience.Z1.zengine.physics;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import de.zcience.Z1.game.components.JumpComponent;
import de.zcience.Z1.zengine.input.InputComponent;
import de.zcience.Z1.zengine.util.CompMappers;

public class MovementSystem extends IteratingSystem {


	public MovementSystem(int priority) {
		super(Family.all(MovementComponent.class , PhysicsBodyComponent.class, PositionComponent.class)
				.get(), priority);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MovementComponent movement = CompMappers.movement.get(entity);
		PhysicsBodyComponent physicsBody = CompMappers.physicsBody.get(entity);
		PositionComponent posComp = CompMappers.position.get(entity);
		InputComponent input = CompMappers.input.get(entity);		
		/*
		 * Controlling movement horizontally
		 */
		if (input != null) {
			// set the velocity to the direction vector given by input
			// multiplicated by the scalar of the movement speed
			movement.velocity.set(input.x * movement.speed,
					input.y * movement.speed);
		}
		if(movement.velocity.x < 0){
			posComp.direction = PositionComponent.FACING.LEFT;
		} else if(movement.velocity.x > 0){
			posComp.direction = PositionComponent.FACING.RIGHT;
		}
		physicsBody.getBody().setLinearVelocity(movement.velocity.x, physicsBody.getBody().getLinearVelocity().y);
	}
}
