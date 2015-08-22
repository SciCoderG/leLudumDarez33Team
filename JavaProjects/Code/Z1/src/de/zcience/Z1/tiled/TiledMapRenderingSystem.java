package de.zcience.Z1.tiled;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.zengine.assetloading.MapLoader;
import de.zcience.Z1.zengine.util.GameConstants;

public class TiledMapRenderingSystem extends EntitySystem{

	private static OrthogonalTiledMapRenderer tiledMapRenderer;

	public TiledMapRenderingSystem() {
		tiledMapRenderer = new OrthogonalTiledMapRenderer(MapLoader.currentMap,
				1.0f / GameConstants.BOX2D_SCALE);
	}
	
	public static void setMap(TiledMap newMap){
		tiledMapRenderer.setMap(newMap);
	}
	
	@Override
	public void update(float deltaTime) {
//		ProfilerGlobal.startTime();
		tiledMapRenderer.setView(EntityCreator.camSystem.getCamera());
		tiledMapRenderer.render();
//		ProfilerGlobal.endTime();
//		ProfilerGlobal.outMax("tiled-");		
		super.update(deltaTime);
	}
}
