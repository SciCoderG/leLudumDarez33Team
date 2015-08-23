package de.zcience.Z1.game;

import box2dLight.ConeLight;
import box2dLight.PointLight;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Animation.PlayMode;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Fixture;

import de.zcience.Z1.game.components.EnemyComponent;
import de.zcience.Z1.game.components.JumpComponent;
import de.zcience.Z1.game.components.PlayerComponent;
import de.zcience.Z1.game.components.ShootingComponent;
import de.zcience.Z1.game.components.StartPointComponent;
import de.zcience.Z1.zengine.assetloading.AssetLoader;
import de.zcience.Z1.zengine.camera.CameraSystem;
import de.zcience.Z1.zengine.input.InputComponent;
import de.zcience.Z1.zengine.physics.MovementComponent;
import de.zcience.Z1.zengine.physics.PhysicsBodyComponent;
import de.zcience.Z1.zengine.physics.PhysicsSystem;
import de.zcience.Z1.zengine.physics.PositionComponent;
import de.zcience.Z1.zengine.physics.util.PhysicsBodyDef;
import de.zcience.Z1.zengine.physics.util.PhysicsFixtureDef;
import de.zcience.Z1.zengine.rendering.LightSystem;
import de.zcience.Z1.zengine.rendering.components.AnimationComponent;
import de.zcience.Z1.zengine.rendering.components.BulletLightComponent;
import de.zcience.Z1.zengine.rendering.components.LightComponent;
import de.zcience.Z1.zengine.rendering.components.TextureComponent;
import de.zcience.Z1.zengine.util.GameConstants;

/**
 * 
 * TODO: EVERYTHING BEWARE, this class needs A LOT OF FIXING AND PUTTING CODE IN
 * THE RIGHT PLACES. you have been warned
 * 
 * @author David
 *
 */
public class EntityCreator {
	public static PooledEngine engine;
	public static PhysicsSystem physicsSystem;
	public static CameraSystem camSystem;
	public static LightSystem lightSystem;

	public static short LIGHT = 0x008;
	public static short WORLDOBJECT = 0x002;
	public static short HEARTH = 0x004;
	public static short PLAYER = 0x006;
	
	
	public static int enemyCounter = 0;

	public static Entity createFloorTile(float x, float y) {
		Entity entity = engine.createEntity();

		int width = GameConstants.getTileSizeX();
		int height = GameConstants.getTileSizeY();

		/*
		 * PhysicsBody
		 */
		PhysicsBodyComponent physicsBody = engine
				.createComponent(PhysicsBodyComponent.class);
		PhysicsBodyDef bodyDef = new PhysicsBodyDef(BodyType.StaticBody,
				physicsSystem).fixedRotation(true).position(x, y);
		physicsBody.init(bodyDef, physicsSystem, entity);

		PhysicsFixtureDef fixtureDef = new PhysicsFixtureDef(physicsSystem)
				.shapeBox(width, height).category(WORLDOBJECT);

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(physicsBody);

		entity.add(physicsBody);

		engine.addEntity(entity);
		return entity;
	}

	public static Entity createPlayer(float x, float y) {
		Entity entity = engine.createEntity();

		/* TextureComponent */
//		TextureComponent textureComponent = engine
//				.createComponent(TextureComponent.class);
//
//		textureComponent.texture = new TextureRegion(AssetLoader.getAssetManager().get("images/Amor2.png", Texture.class));
//		textureComponent.width = textureComponent.texture.getRegionWidth();
//		textureComponent.height = textureComponent.texture.getRegionHeight();
//		entity.add(textureComponent);

		AnimationComponent animComponent = engine.createComponent(AnimationComponent.class);
		animComponent.animation = AssetLoader.getAssetManager().get("images/sprite-animation1.png", Animation.class);
		animComponent.animation.setPlayMode(PlayMode.LOOP);
		animComponent.animation.setFrameDuration(0.5f);
		animComponent.width = animComponent.animation.getKeyFrame(0.0f).getRegionWidth();
		animComponent.height = animComponent.animation.getKeyFrame(0.0f).getRegionHeight();
		entity.add(animComponent);
		
		/*
		 * PhysicsBody
		 */
		float width = animComponent.width;
		float height = animComponent.height;
		PhysicsBodyComponent physicsBody = engine
				.createComponent(PhysicsBodyComponent.class);
		PhysicsBodyDef bodyDef = new PhysicsBodyDef(BodyType.DynamicBody,
				physicsSystem).fixedRotation(true).position(x, y);

		physicsBody.init(bodyDef, physicsSystem, entity);

		// Head
		PhysicsFixtureDef fixtureDef = new PhysicsFixtureDef(physicsSystem)
				.shapeCircle(height * 0.12f, new Vector2(0, height * 0.25f))
				.friction(0).category(PLAYER).mask(WORLDOBJECT);

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(physicsBody);

		// middle
		fixtureDef = new PhysicsFixtureDef(physicsSystem)
				.shapeBox(width * 0.2f, height * 0.6f,
						new Vector2(0, -height * 0.1f), 0).friction(0)
				.category(PLAYER).mask(WORLDOBJECT);
		fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(physicsBody);

		// bottom
		fixtureDef = new PhysicsFixtureDef(physicsSystem)
				.shapeCircle(height * 0.12f, new Vector2(0, -height * 0.4f))
				.friction(0).category(PLAYER).mask(WORLDOBJECT);

		fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(physicsBody);

		// jumpsensor
		fixtureDef = new PhysicsFixtureDef(physicsSystem)
				.shapeCircle(height / 10.0f, new Vector2(0, -height * 0.5f))
				.sensor(true).category(PLAYER).mask(WORLDOBJECT);

		fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData("Jump");

		entity.add(physicsBody);

		// InputComponent
		InputComponent inputC = engine.createComponent(InputComponent.class);
		inputC.shootTimerMax = 0.1f;
		entity.add(inputC);

		// PositionComponent
		PositionComponent positionComponet = engine
				.createComponent(PositionComponent.class);
		positionComponet.x = x;
		positionComponet.y = y;
		entity.add(positionComponet);

		// MovementComponent
		MovementComponent movementComponent = engine
				.createComponent(MovementComponent.class);
		movementComponent.speed = 4.0f;
		entity.add(movementComponent);

		// PlayerComponent
		entity.add(engine.createComponent(PlayerComponent.class));

		// JumpComponent
		JumpComponent jumpComp = engine.createComponent(JumpComponent.class);
		jumpComp.jumpForce.set(0.0f, 250.0f);
		entity.add(jumpComp);

		// LightComponent
		LightComponent lightCompo = engine
				.createComponent(LightComponent.class);
		lightCompo.light = new PointLight(LightSystem.rayHandler, 256,
				new Color(0.3f, 0.3f, 0.3f, 1f), 9, x, y);
		
		lightCompo.light.attachToBody(physicsBody.getBody());

		entity.add(lightCompo);

		engine.addEntity(entity);
		return entity;
	}

	public static Entity createStartPoint(float x, float y) {
		EntityCreator.createPlayer(x, y);

		Entity entity = engine.createEntity();

		/* Position */
		PositionComponent positionComponent = engine
				.createComponent(PositionComponent.class);

		positionComponent.x = x;
		positionComponent.y = y;

		entity.add(positionComponent);

		/* Unique start point component */
		entity.add(engine.createComponent(StartPointComponent.class));

		engine.addEntity(entity);
		return entity;
	}

	public static Entity createHeart(float x, float y, float shotDirectionX,
			float shotDirectionY) {
		Entity entity = engine.createEntity();

		// textureComponent
		TextureComponent textureComponent = engine
				.createComponent(TextureComponent.class);

		textureComponent.texture = new TextureRegion(AssetLoader.getAssetManager().get("images/herz.png", Texture.class));
		textureComponent.width = 32;
		textureComponent.height = 32;

		entity.add(textureComponent);

		float width = textureComponent.width;
		float height = textureComponent.height;

		// physicsBody
		PhysicsBodyComponent physicsBody = engine
				.createComponent(PhysicsBodyComponent.class);
		PhysicsBodyDef bodyDef = new PhysicsBodyDef(BodyType.DynamicBody,
				physicsSystem).fixedRotation(true).position(x, y)
				.gravityScale(0);

		physicsBody.init(bodyDef, physicsSystem, entity);

		PhysicsFixtureDef fixtureDef = new PhysicsFixtureDef(physicsSystem)
				.shapeCircle(height / 3).restitution(0.8f).category(HEARTH)
				.mask(WORLDOBJECT);

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(physicsBody);

		entity.add(physicsBody);

		// position Component
		PositionComponent positionComponent = engine
				.createComponent(PositionComponent.class);
		entity.add(positionComponent);

		// ShootingComponent
		ShootingComponent shootingComponent = engine
				.createComponent(ShootingComponent.class);
		shootingComponent.shotDirection.set(shotDirectionX, shotDirectionY);
		shootingComponent.origin.set(x, y);
		shootingComponent.shotSpeed = 10.0f;
		shootingComponent.bouncesLeft = 2.0f;
		entity.add(shootingComponent);

		// LightComponent
		BulletLightComponent lightCompo = engine
				.createComponent(BulletLightComponent.class);
		lightCompo.setLight(LightSystem.rayHandler, 8,
				new Color(0.7f, 0.0f, 0.0f, 1.0f), 1, x, y, physicsBody.getBody());
//		lightCompo.light = new PointLight(LightSystem.rayHandler, 8,
//				new Color(1.0f, 0.0f, 0.0f, 0.7f), 1, x, y);
//		lightCompo.light.attachToBody(physicsBody.getBody());
		entity.add(lightCompo);

		engine.addEntity(entity);
		return entity;
	}

	public static Entity createEnemy(float x, float y) {
		Entity entity = engine.createEntity();

		enemyCounter++;
		
		// EnemyComponent
		entity.add(engine.createComponent(EnemyComponent.class));

		/* Texture */
		TextureComponent textureComponent = engine
				.createComponent(TextureComponent.class);

		textureComponent.texture = new TextureRegion(AssetLoader.getAssetManager().get("images/Enemy1_64pix.png", Texture.class));
		textureComponent.width = textureComponent.texture.getRegionWidth();
		textureComponent.height = textureComponent.texture.getRegionHeight();

		entity.add(textureComponent);

		/*
		 * PhysicsBody
		 */
		float width = textureComponent.width;
		float height = textureComponent.height;
		PhysicsBodyComponent physicsBody = engine
				.createComponent(PhysicsBodyComponent.class);
		PhysicsBodyDef bodyDef = new PhysicsBodyDef(BodyType.DynamicBody,
				physicsSystem).fixedRotation(true).position(x, y)
				.gravityScale(10.0f);
		physicsBody.init(bodyDef, physicsSystem, entity);

		// Body
		PhysicsFixtureDef fixtureDef = new PhysicsFixtureDef(physicsSystem)
				.shapeBox(width * 0.2f, height ).category(WORLDOBJECT);

		Fixture fixture = physicsBody.createFixture(fixtureDef);
		fixture.setUserData(physicsBody);

		entity.add(physicsBody);

		/* Position */
		PositionComponent positionComponent = engine
				.createComponent(PositionComponent.class);

		positionComponent.x = x;
		positionComponent.y = y;

		entity.add(positionComponent);

		// LightComponent
		LightComponent lightCompo = engine
				.createComponent(LightComponent.class);
		lightCompo.light = new PointLight(LightSystem.rayHandler, 8,
				new Color(0.2f, 0.2f, 0.2f, 1f),3, x, y);
		//lightCompo.light.setContactFilter(LIGHT, (short) 0, WORLDOBJECT);

		lightCompo.light.attachToBody(physicsBody.getBody());
		lightCompo.light.setXray(true);

		entity.add(lightCompo);

		engine.addEntity(entity);
		return entity;
	}

	public static Entity createConeLight(float x, float y, int rays,
			Color color, float distance, float directionDegree, float coneDegree) {
		Entity entity = engine.createEntity();

		// lightComponent
		LightComponent lightCompo = engine
				.createComponent(LightComponent.class);
		lightCompo.light = new ConeLight(LightSystem.rayHandler, rays, color,
				distance, x, y, directionDegree, coneDegree);
		entity.add(lightCompo);

		// positionComponent
		PositionComponent position = engine
				.createComponent(PositionComponent.class);
		position.x = x;
		position.y = y;
		entity.add(position);

		// ConeLight coneLight = new ConeLight(LightSystem.rayHandler, 100,
		// Color.WHITE, 10, x, y, 270, 45);
		return entity;
	}

	public static Entity createPointLight(float x, float y, int rays,
			Color color, float distance) {
		Entity entity = engine.createEntity();

		// positionComponent
		PositionComponent position = engine
				.createComponent(PositionComponent.class);
		position.x = x;
		position.y = y;
		entity.add(position);

		// lightComponent
		LightComponent lightCompo = engine
				.createComponent(LightComponent.class);
		lightCompo.light = new PointLight(LightSystem.rayHandler, rays, color,
				distance, x, y);
		entity.add(lightCompo);

		return entity;
	}
}
