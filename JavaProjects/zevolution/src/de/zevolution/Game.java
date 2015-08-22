package de.zevolution;

import org.lwjgl.opengl.GL11;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;

import de.zevolution.input.InputKeyboardSystem;
import de.zevolution.menu.MenuEntityCreator;
import de.zevolution.menu.MenuInputProcessor;
import de.zevolution.movement.MovementSystem;
import de.zevolution.physics.systems.PhysicsSystem;
import de.zevolution.physics.systems.UpdatePositionSystem;

/**
 * The Applicationlistener as specified by libgdx.
 * 
 * @author David
 *
 */
public class Game implements ApplicationListener {

	private PooledEngine engine;

	// Systems
	private InputKeyboardSystem inputKeyboardSystem;

	// Zu Testzwecken
	private OrthographicCamera ortho;
	private Box2DDebugRenderer debugRenderer;

	@Override
	public void create() {
		// creating the Ashley engine
		engine = new PooledEngine();
		EntityCreator.engine = engine;

		// initialise Box2D
		Box2D.init();

		// create and add systems
		addSystems();
		// create and add input processors
		setInputProcessors();

		// zu testzwecken
		ortho = new OrthographicCamera();
		ortho.setToOrtho(
				true,
				Gdx.graphics.getWidth()
						/ EntityCreator.physicsSystem.getScale(),
				Gdx.graphics.getHeight()
						/ EntityCreator.physicsSystem.getScale());
		debugRenderer = new Box2DDebugRenderer();

		MenuEntityCreator.createMainMenu();
	}

	private void addSystems() {

		// InputKeyboardSystem
		inputKeyboardSystem = new InputKeyboardSystem(
				GameConstants.INPUT_PRIORITY);
		engine.addSystem(inputKeyboardSystem);

		// add PhysicSystem
		PhysicsSystem physicsSystem = new PhysicsSystem(
				GameConstants.BOX2D_VELOCITY_ITERATIONS,
				GameConstants.BOX2D_POSITIONS_ITERATIONS,
				GameConstants.BOX2D_SCALE, GameConstants.PHYSICS_PRIORITY);

		engine.addSystem(physicsSystem);
		physicsSystem.setGravity(new Vector2(0, 0)); // erstmal keine gravity,
														// brauchen wir in
														// unserem Spiel nicht
		EntityCreator.physicsSystem = physicsSystem;

		// add MovementSystem
		engine.addSystem(new MovementSystem(GameConstants.PHYSICS_PRIORITY + 1));

		// add UpdatePositionSystem
		engine.addSystem(new UpdatePositionSystem(
				GameConstants.PHYSICS_PRIORITY + 2));
	}

	/**
	 * TODO: This should be done by a Manager (bspw. MenuManager
	 */
	private void setInputProcessors() {
		InputMultiplexer multi = new InputMultiplexer();

		// add the keyboard Input Processor
		multi.addProcessor(inputKeyboardSystem);

		Gdx.input.setInputProcessor(multi);
	}

	public PooledEngine getEngine() {
		return engine;
	}

	@Override
	public void dispose() {
		engine.getSystem(PhysicsSystem.class).getWorld().dispose();

	}

	@Override
	public void pause() {

	}

	@Override
	public void render() {
		engine.update(Gdx.graphics.getDeltaTime());
		// zu testzwecken
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(EntityCreator.physicsSystem.getWorld(),
				ortho.combined);
	}

	@Override
	public void resize(int arg0, int arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

}
