package de.zevolution.movement;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import de.zevolution.CompMappers;
import de.zevolution.input.InputComponent;
import de.zevolution.physics.components.PhysicsBodyComponent;

public class MovementSystem extends IteratingSystem {


	public MovementSystem(int priority) {
		super(Family.all(MovementComponent.class, PhysicsBodyComponent.class)
				.get(), priority);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		MovementComponent movement = CompMappers.movement.get(entity);
		PhysicsBodyComponent physicsBody = CompMappers.physicsBody.get(entity);
		InputComponent input = CompMappers.input.get(entity);

		if (input != null) {
			// set the velocity to the direction vector given by input
			// multiplicated by the scalar of the movement speed
			movement.velocity.set(input.direction.x * movement.speed,
					input.direction.y * movement.speed);
		}
		physicsBody.getBody().setLinearVelocity(movement.velocity);
		
	}

}
