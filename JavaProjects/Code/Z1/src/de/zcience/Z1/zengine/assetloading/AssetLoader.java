package de.zcience.Z1.zengine.assetloading;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import de.zcience.Z1.game.EntityCreator;
import de.zcience.Z1.game.Game;
import de.zcience.Z1.zengine.assetloading.AnimationLoader.AnimationParameter;

/**
 * Use this class for loading Assets into the libgdx AssetManager
 * @author David
 *
 */
public class AssetLoader {

	private static ZAssetManager assetManager= new ZAssetManager();
	private static TmxMapLoader tmxMapLoader = new TmxMapLoader();
	
	private AssetLoader(){
			
	}
	
	public static AssetManager getAssetManager() {
		return assetManager;
	}

	public static void setAssetManager(ZAssetManager assetManager) {
		AssetLoader.assetManager.dispose();
		AssetLoader.assetManager = assetManager;
	}
	
	public static void loadAll(){
		/* Load TiledMap */
		TiledMap map = tmxMapLoader
				.load("tilesets/test1.tmx");
		
		/* Load our Textures!*/
		assetManager.load("images/Amor2.png", Texture.class);
		assetManager.load("images/Enemy1_64pix.png", Texture.class);
		assetManager.load("images/herz.png", Texture.class);
		
		/*
		 * This is terrible. Just read it out of somewhere. PLEASE
		 */
		AnimationParameter param1 = new AnimationParameter();
		param1.frameDuration = 0.025f;
		param1.frame_cols = 6;
		param1.frame_rows = 5;
		assetManager.load("images/sprite-animation1.png", Animation.class, param1);
		// lets the assetManager finish loading everything
		assetManager.finishLoading();
		
		/* MapLoader, generating our map */
		MapLoader.generateWorldFromTiledMap(EntityCreator.engine, map, EntityCreator.physicsSystem,
				EntityCreator.camSystem);
	}
	
}
