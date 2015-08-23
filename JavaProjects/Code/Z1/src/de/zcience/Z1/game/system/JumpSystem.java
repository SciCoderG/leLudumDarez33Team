package de.zcience.Z1.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

import de.zcience.Z1.game.components.JumpComponent;
import de.zcience.Z1.zengine.input.InputComponent;
import de.zcience.Z1.zengine.physics.PhysicsBodyComponent;
import de.zcience.Z1.zengine.util.CompMappers;

public class JumpSystem extends IteratingSystem {

	public JumpSystem(int priority) {
		super(Family.all(InputComponent.class, PhysicsBodyComponent.class,
				JumpComponent.class).get(), priority);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		
		JumpComponent jumpComp = CompMappers.jump.get(entity);
		boolean jumping = jumpComp.jump;
		if(!jumping){
			jumping = CompMappers.input.get(entity).jump || jumping;
		}
		
		if(jumpComp.groundContacts>0 && jumping){
			PhysicsBodyComponent physicsComp = CompMappers.physicsBody.get(entity);
			physicsComp.getBody().applyForceToCenter(jumpComp.jumpForce, true);
		}
		
//		if (entity.getComponent(JumpComponent.class).force > 0.0f) {
//			entity.getComponent(PhysicsBodyComponent.class)
//					.getBody()
//					.applyForceToCenter(0.0f,
//							entity.getComponent(JumpComponent.class).force,
//							true);
//			entity.getComponent(JumpComponent.class).force = (entity
//					.getComponent(JumpComponent.class).force
//					- entity.getComponent(JumpComponent.class).forceDown > 0) ? entity
//					.getComponent(JumpComponent.class).force
//					- entity.getComponent(JumpComponent.class).forceDown : 0.0f;
//		}
//		// Jump if true
//
//		if (entity.getComponent(JumpComponent.class).groundContacts > 0
//				&& entity.getComponent(InputComponent.class).jump) {
//			float force = 900.0f;
//			entity.getComponent(JumpComponent.class).force = force;
//			entity.getComponent(JumpComponent.class).forceDown = force / 40;
//		}
	}
}
