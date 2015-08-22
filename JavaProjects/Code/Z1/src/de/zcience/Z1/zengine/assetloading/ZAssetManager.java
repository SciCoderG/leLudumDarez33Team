package de.zcience.Z1.zengine.assetloading;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.Animation;

public class ZAssetManager extends AssetManager {
	
	public ZAssetManager(){
		this(new InternalFileHandleResolver());
	}
	
	public ZAssetManager(FileHandleResolver resolver){
		super(resolver);
		setLoader(Animation.class, new AnimationLoader(resolver));
	}
}
