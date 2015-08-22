package de.zcience.Z1.zengine.rendering;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.zengine.physics.MovementComponent;
import de.zcience.Z1.zengine.physics.PositionComponent;
import de.zcience.Z1.zengine.rendering.components.AnimationComponent;
import de.zcience.Z1.zengine.rendering.components.TextureComponent;
import de.zcience.Z1.zengine.util.CompMappers;
import de.zcience.Z1.zengine.util.DrawUtil;
import de.zcience.Z1.zengine.util.GameConstants;

public class TextureRenderer extends IteratingSystem {

	public TextureRenderer(int priority) {
		super(Family.all(PositionComponent.class).one(TextureComponent.class, AnimationComponent.class).get(),
				priority);
	}

	@Override
	public void update(float deltaTime) {
		DrawUtil.batch.begin();
		super.update(deltaTime);
		DrawUtil.batch.end();
	}

	@Override
	/**
	 * TODO: Das ist hier vollkommener Schwachsinn. Irgendwie muss es möglich
	 * sein einfach alles in Kamera Koordinaten zu projizieren. Die Berechnungen
	 * werden dann von der GPU übernommen --> SPEEEEEEEEEDBOOOOOOOOOOOST
	 */
	protected void processEntity(Entity entity, float deltaTime) {
		TextureComponent texComp = CompMappers.texture.get(entity);
		PositionComponent posComp = CompMappers.position.get(entity);
		AnimationComponent animComp;

		TextureRegion texture = null;
		float width = 0.0f, height = 0.0f;

		if (texComp != null) {
			texture = texComp.texture;
			width = texComp.width;
			height = texComp.height;
		} else {
			animComp = CompMappers.animation.get(entity);
			if (animComp != null) {
				animComp.stateTime += deltaTime;
				texture = animComp.animation.getKeyFrame(animComp.stateTime);
				width = animComp.width;
				height = animComp.height;
			}
		}

		// if needed, flip the texture
		if (posComp.direction == PositionComponent.FACING.LEFT && !texture.isFlipX()) {
			texture.flip(true, false);
		} else if (posComp.direction == PositionComponent.FACING.RIGHT && texture.isFlipX()) {
			texture.flip(true, false);
		}

		PositionComponent position = CompMappers.position.get(entity);
		OrthographicCamera camera = EntityCreator.camSystem.getCamera();

		float transX = camera.position.x - (camera.viewportWidth / 2);
		float transY = camera.position.y - (camera.viewportHeight / 2);
		transX *= GameConstants.BOX2D_SCALE;
		transY *= GameConstants.BOX2D_SCALE;

		transX = position.x - transX - (width / 2);
		transY = position.y - transY - (height / 2);

		DrawUtil.batch.draw(texture, transX, transY, width, height);
	}
}
