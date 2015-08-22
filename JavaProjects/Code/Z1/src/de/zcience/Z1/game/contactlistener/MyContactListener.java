package de.zcience.Z1.game.contactlistener;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.game.components.DeathComponent;
import de.zcience.Z1.game.components.EnemyComponent;
import de.zcience.Z1.game.components.JumpComponent;
import de.zcience.Z1.game.components.PlayerComponent;
import de.zcience.Z1.game.components.ShootingComponent;
import de.zcience.Z1.zengine.physics.PhysicsBodyComponent;
import de.zcience.Z1.zengine.util.CompMappers;

public class MyContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		if (fixtureA.getUserData().equals("Jump")
				|| fixtureB.getUserData().equals("Jump")) {
			// if((fixtureA.getUserData().equals("enemy") &&
			// fixtureB.getUserData().equals("player")) ||
			// (fixtureA.getUserData().equals("player") &&
			// fixtureB.getUserData().equals("enemy"))) {
			// System.out.println("TOD");
			// }
			/* ignore player body */
			// if((fixtureA.getUserData().equals("jump") &&
			// !fixtureB.getUserData().equals("player")) ||
			// (!fixtureA.getUserData().equals("player") &&
			// fixtureB.getUserData().equals("jump"))) {
			Family family = Family.all(PlayerComponent.class).get();
			ImmutableArray<Entity> players = EntityCreator.engine
					.getEntitiesFor(family);

			for (Entity player : players) {
				JumpComponent jumpC = CompMappers.jump.get(player);
				if (jumpC != null) {
					jumpC.groundContacts++;
				}

			}
		} else {
			PhysicsBodyComponent physicsBodyComponentA = null;
			PhysicsBodyComponent physicsBodyComponentB = null;
			if (fixtureA.getUserData().getClass()
					.equals(PhysicsBodyComponent.class)) {
				physicsBodyComponentA = (PhysicsBodyComponent) fixtureA
						.getUserData();
			}
			if (fixtureB.getUserData().getClass()
					.equals(PhysicsBodyComponent.class)) {
				physicsBodyComponentB = (PhysicsBodyComponent) fixtureB
						.getUserData();
			}

			if (physicsBodyComponentA != null && physicsBodyComponentB != null) {
				Entity entityA = physicsBodyComponentA.getEntity();
				Entity entityB = physicsBodyComponentB.getEntity();

				// CHecking for the shooting stuff
				ShootingComponent shootingA = CompMappers.shooting.get(entityA);
				ShootingComponent shootingB = CompMappers.shooting.get(entityB);
				if (shootingA != null ^ shootingB != null) {
					if (shootingA != null) {
						if (shootingA.bouncesLeft > 0) {
							shootingA.bouncesLeft--;
						} else {
							EntityCreator.engine.removeEntity(entityA);
						}
						EnemyComponent enemyComp = CompMappers.enemy
								.get(entityB);
						if (enemyComp != null && !CompMappers.death.has(entityB)) {
							entityB.add(EntityCreator.engine
									.createComponent(DeathComponent.class));
						}

					} else {
						if (shootingB.bouncesLeft > 0) {
							shootingB.bouncesLeft--;
						} else {
							EntityCreator.engine.removeEntity(entityB);
						}
						EnemyComponent enemyComp = CompMappers.enemy
								.get(entityA);
						if (enemyComp != null && !CompMappers.death.has(entityA)) {
							entityA.add(EntityCreator.engine
									.createComponent(DeathComponent.class));
						}

					}
					// Checking for the Player hits Enemy stuff
				} else if (CompMappers.player.has(entityA)
						^ CompMappers.player.has(entityB)
						&& (CompMappers.enemy.has(entityA))
						^ CompMappers.enemy.has(entityB)) {

					Entity player = CompMappers.player.has(entityA) ? entityA
							: entityB;
					if(!CompMappers.death.has(player)){
						player.add(EntityCreator.engine.createComponent(DeathComponent.class));
					}
					
					
				}

			}
		}

	}

	@Override
	public void endContact(Contact contact) {
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		if (fixtureA.getUserData().equals("Jump")
				|| fixtureB.getUserData().equals("Jump")) {
			/* ignore player body */
			// if((fixtureA.getUserData().equals("jump") &&
			// !fixtureB.getUserData().equals("player")) ||
			// (!fixtureA.getUserData().equals("player") &&
			// fixtureB.getUserData().equals("jump"))){
			Family family = Family.all(PlayerComponent.class).get();
			ImmutableArray<Entity> players = EntityCreator.engine
					.getEntitiesFor(family);

			for (Entity player : players) {
				JumpComponent jumpC = CompMappers.jump.get(player);
				if (jumpC != null) {
					jumpC.groundContacts--;
				}
			}
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub

	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub

	}
}
