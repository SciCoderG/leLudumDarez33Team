package de.zcience.Z1.game.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.ashley.utils.ImmutableArray;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.game.components.DeathComponent;
import de.zcience.Z1.game.components.StartPointComponent;
import de.zcience.Z1.zengine.physics.PhysicsBodyComponent;
import de.zcience.Z1.zengine.physics.PositionComponent;
import de.zcience.Z1.zengine.rendering.components.TextureComponent;
import de.zcience.Z1.zengine.util.CompMappers;
import de.zcience.Z1.zengine.util.GameConstants;

public class DeathSystem extends IteratingSystem {

	public DeathSystem(int priority) {
		super(Family.all(DeathComponent.class,
				TextureComponent.class).get(), priority);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		DeathComponent deathComp = CompMappers.death.get(entity);
		TextureComponent textureComp = CompMappers.texture.get(entity);		

		if (deathComp.timer > 0) {
			deathComp.timer -= deltaTime;
			textureComp.width *= 0.99f;
			textureComp.height *= 0.99f;
		} else {
			if (CompMappers.player.has(entity)) {
				//if the player is dying - remove the death component and put him back to his position
				
				entity.remove(DeathComponent.class);
				
				Family family = Family.all(StartPointComponent.class,
						PositionComponent.class).get();
				ImmutableArray<Entity> startpoints = EntityCreator.engine
						.getEntitiesFor(family);

				// get his startposition
				PositionComponent startPosition = CompMappers.position
						.get(startpoints.first());
				// reset the player to the startposition
				PhysicsBodyComponent physicsBody = CompMappers.physicsBody
						.get(entity);
				physicsBody.getBody().setTransform(startPosition.x / GameConstants.BOX2D_SCALE,
						startPosition.y/ GameConstants.BOX2D_SCALE, 0);
				// restore the texture width and height that has been decreased by death
				textureComp.width = textureComp.texture.getRegionWidth();
				textureComp.height = textureComp.texture.getRegionHeight();

			} else if(CompMappers.enemy.has(entity)){
				EntityCreator.engine.removeEntity(entity);
				EntityCreator.enemyCounter--;
			}
		}
	}

}
