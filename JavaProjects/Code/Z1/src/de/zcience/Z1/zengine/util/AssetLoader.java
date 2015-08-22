package de.zcience.Z1.zengine.util;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.game.Game;

/**
 * Use this class for loading Assets into the libgdx AssetManager
 * @author David
 *
 */
public class AssetLoader {

	private static AssetManager assetManager= new AssetManager();
	
	private AssetLoader(){
			
	}
	
	public static AssetManager getAssetManager() {
		return assetManager;
	}

	public static void setAssetManager(AssetManager assetManager) {
		AssetLoader.assetManager.dispose();
		AssetLoader.assetManager = assetManager;
	}
	
	public static void loadAll(){
		/* Load TiledMap */
		TiledMap map = new TmxMapLoader()
				.load("tilesets/example2.tmx");
		
		/* Load our Textures!*/
		assetManager.load("images/Amor2.png", Texture.class);
		assetManager.load("images/Enemy1_64pix.png", Texture.class);
		assetManager.load("images/herz.png", Texture.class);
		// lets the assetManager finish loading everything
		assetManager.finishLoading();
		
		/* MapLoader, generating our map */
		MapLoader.generateWorldFromTiledMap(EntityCreator.engine, map, EntityCreator.physicsSystem,
				EntityCreator.camSystem);
	}

	
}
