package de.zcience.Z1.zengine.camera;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import de.zcience.Z1.game.components.PlayerComponent;
import de.zcience.Z1.zengine.physics.PositionComponent;
import de.zcience.Z1.zengine.util.CompMappers;
import de.zcience.Z1.zengine.util.GameConstants;

/**
 * CameraSystem. Follows the entity with the most recently added PlayerComponent
 * 
 * @author David
 *
 */
public class CameraSystem extends EntitySystem implements EntityListener {

	/**
	 * target that should be followed by the CameraSystem. Camera will be
	 * smoothed out by giving it the behavior of a spring
	 */
	private Entity target;

	private OrthographicCamera camera;
	private Vector2 camVelocity = new Vector2();
	private float dampConst, springConst, massCoefficient, cameraSmoothnessX, cameraSmoothnessY;

	public Vector2 viewpoint = new Vector2();

	public CameraSystem(int priority) {
		super(priority);
		camera = new OrthographicCamera();
		resizeCameraViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		setSpringConst(20f);
		setMass(2.0f);
		setCameraSmoothness(0.5f, 2.0f);
	}

	public void setSpringConst(float springConst) {
		// choose the springConst by your own will
		this.springConst = springConst;
		// use critical damping to counteract oscillating of the camera
		this.dampConst = (float) (2.0 * Math.sqrt(springConst));
	}

	public void setMass(float mass) {
		// because division is slow, we will just compute it once and store it
		// for use in force calculation
		this.massCoefficient = 1.0f / mass;
	}
	
	public void setCameraSmoothness(float cameraSmoothnessX, float cameraSmoothnessY){
		this.cameraSmoothnessX = cameraSmoothnessX;
		this.cameraSmoothnessY = cameraSmoothnessY;
	}

	@Override
	public void update(float deltaTime) {
		if (target != null) {
			PositionComponent posComp = CompMappers.position.get(target);
			

			if (posComp != null) {
				Vector2 force = new Vector2();
				Vector2 camPos = getCameraPosition();

				force.x = ((springConst * (posComp.x - camPos.x)) - (dampConst * camVelocity.x)) * massCoefficient;
				force.y = ((springConst * (posComp.y - camPos.y)) - (dampConst * camVelocity.y)) * massCoefficient;
				// integrate camVelocity
				camVelocity.x += force.x * deltaTime;
				camVelocity.y += force.y * deltaTime;
				// integrate position
				float integrateX = camVelocity.x * deltaTime;
				float integrateY = camVelocity.y * deltaTime;
				if(Math.abs(integrateX) > cameraSmoothnessX || Math.abs(integrateY) > cameraSmoothnessY){
					camPos.x +=  integrateX;
					camPos.y +=  integrateY;
				}	
				setCameraPosition(camPos.x, camPos.y);
			}
		}
		camera.update();
	}

	@Override
	public void addedToEngine(Engine engine) {
		engine.addEntityListener(Family.all(PlayerComponent.class, PositionComponent.class).get(), this);
	}

	@Override
	public void entityAdded(Entity entity) {
		if (target == null) {
			target = entity;
			camera.update(true);
		}
	}

	@Override
	public void entityRemoved(Entity entity) {
		target = null;
	}

	/**
	 *
	 * @return position in pixel coordinates
	 */
	public Vector2 getCameraPosition() {
		return new Vector2(camera.position.x * GameConstants.BOX2D_SCALE,
				camera.position.y * GameConstants.BOX2D_SCALE);
	}

	/* CameraControls */
	/**
	 * Resizes the Camera Viewport. It will automatically be downscaled by the
	 * BOX2D_SCALE factor
	 * 
	 * @param width
	 * @param height
	 */
	public void resizeCameraViewport(int width, int height) {
		camera.setToOrtho(GameConstants.YDOWN, width / GameConstants.BOX2D_SCALE, height / GameConstants.BOX2D_SCALE);
		camera.update(true);
	}

	public OrthographicCamera getCamera() {
		return camera;
	}

	/**
	 * Sets the Camera Position to the given position, automatically scales the
	 * position by the BOX2D_SCALE factor.
	 * 
	 * @param x
	 *            vertical Position
	 * @param y
	 *            horizontal Position
	 */
	public void setCameraPosition(float x, float y) {
		camera.position.x = x / GameConstants.BOX2D_SCALE;
		camera.position.y = y / GameConstants.BOX2D_SCALE;

		// camera.translate(x, y);
		camera.update(true);
	}

	public Matrix4 getCombinedMatrix() {
		return camera.combined;
	}

}
