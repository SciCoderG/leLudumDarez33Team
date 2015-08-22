package de.zcience.Z1.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.math.Vector2;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.game.components.ShootingComponent;
import de.zcience.Z1.zengine.input.InputComponent;
import de.zcience.Z1.zengine.physics.PhysicsBodyComponent;
import de.zcience.Z1.zengine.physics.PositionComponent;
import de.zcience.Z1.zengine.util.CompMappers;

public class ShootingSystem extends IteratingSystem {

	public ShootingSystem(int priority) {
		super(Family.all(PositionComponent.class, PhysicsBodyComponent.class)
				.one(InputComponent.class, ShootingComponent.class).get(),
				priority);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		InputComponent input = CompMappers.input.get(entity);
		ShootingComponent shooting = CompMappers.shooting.get(entity);
		PhysicsBodyComponent physicsBody = CompMappers.physicsBody.get(entity);
		/*
		 * is it the player? (does he have an input?)
		 */
		if (input != null) {
			if (input.shoot && input.shootTimer <= 0) {
				// TODO make this not hardcoded
				input.shootTimer = input.shootTimerMax;
				EntityCreator.createHeart(physicsBody.getPosition().x,
						physicsBody.getPosition().y, input.shotDirection.x,
						input.shotDirection.y);

			} else if (input.shoot) {
				input.shootTimer -= deltaTime;
			} else if (input.shootTimer >= 0) {
				input.shootTimer -= deltaTime;
			}
		} else if (shooting != null && !shooting.receivedImpulse) { // or is it
																	// a heart?
			Vector2 direction = shooting.shotDirection.cpy();
			direction.sub(shooting.origin).nor();

//			physicsBody.getBody().applyLinearImpulse(
//					direction.scl(shooting.shotSpeed),
//					physicsBody.getBody().getWorldCenter(), true);
			 physicsBody.getBody().setLinearVelocity(direction.scl(shooting.shotSpeed));
			shooting.receivedImpulse = true;
		} else if (shooting != null) {
			if (shooting.timer > 0) {
				shooting.timer -= deltaTime;
			} else {
				EntityCreator.engine.removeEntity(entity);
			}

		}

	}

}
