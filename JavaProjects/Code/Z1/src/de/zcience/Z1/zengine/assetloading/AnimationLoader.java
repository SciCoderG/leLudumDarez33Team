package de.zcience.Z1.zengine.assetloading;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * This is black magic. Don't even ask.
 * 
 * @author David_000
 *
 */
public class AnimationLoader extends AsynchronousAssetLoader<Animation, AnimationLoader.AnimationParameter> {

	static public class AnimationLoaderInfo {
		String filename;
		TextureData data;
		Texture texture;
		Animation animation;
		float frameDuration;
		int frame_cols, frame_rows;
	};

	AnimationLoaderInfo info = new AnimationLoaderInfo();

	public AnimationLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, AnimationParameter parameter) {
		info.filename = fileName;
		if (parameter == null || parameter.textureData == null) {
			Pixmap pixmap = null;
			Format format = null;
			boolean genMipMaps = false;
			info.texture = null;
			info.animation = null;

			if (parameter != null) {
				format = parameter.format;
				genMipMaps = parameter.genMipMaps;
				info.texture = parameter.texture;
				info.animation = parameter.animation;
				info.frameDuration = parameter.frameDuration;
				info.frame_cols = parameter.frame_cols;
				info.frame_rows = parameter.frame_rows;
			}

			info.data = TextureData.Factory.loadFromFile(file, format, genMipMaps);
		} else {
			info.data = parameter.textureData;
			info.texture = parameter.texture;
			info.animation = parameter.animation;
			info.frameDuration = parameter.frameDuration;
			info.frame_cols = parameter.frame_cols;
			info.frame_rows = parameter.frame_rows;
		}
		if (!info.data.isPrepared())
			info.data.prepare();

	}

	@Override
	public Animation loadSync(AssetManager manager, String fileName, FileHandle file, AnimationParameter parameter) {
		if (info == null)
			return null;
		Texture texture = info.texture;
		if (texture != null) {
			texture.load(info.data);
		} else {
			texture = new Texture(info.data);
		}
		if (parameter != null) {
			texture.setFilter(parameter.minFilter, parameter.magFilter);
			texture.setWrap(parameter.wrapU, parameter.wrapV);
		}
		TextureRegion[][] frameMatrix = TextureRegion.split(texture, texture.getWidth() / info.frame_cols,
				texture.getHeight() / info.frame_rows);
		
		TextureRegion[] frames = new TextureRegion[info.frame_cols * info.frame_rows];
		
		int index = 0;
        for (int i = 0; i < info.frame_rows; i++) {
            for (int j = 0; j < info.frame_cols; j++) {
                frames[index++] = frameMatrix[i][j];
            }
        }

		Animation animation = new Animation(info.frameDuration, frames);
		return animation;
	}

	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, AnimationParameter parameter) {
		return null;
	}

	static public class AnimationParameter extends AssetLoaderParameters<Animation> {

		public float frameDuration = 0.5f;
		public int frame_cols = 2, frame_rows = 2;
		/**
		 * the format of the final Texture. Uses the source images format if
		 * null
		 **/
		public Format format = null;
		/** whether to generate mipmaps **/
		public boolean genMipMaps = false;
		/** The texture to put the {@link TextureData} in, optional. **/
		public Texture texture = null;
		/** The animation to put the texture in, optional. **/
		public Animation animation = null;
		/**
		 * TextureData for textures created on the fly, optional. When set, all
		 * format and genMipMaps are ignored
		 */
		public TextureData textureData = null;
		public TextureFilter minFilter = TextureFilter.Nearest;
		public TextureFilter magFilter = TextureFilter.Nearest;
		public TextureWrap wrapU = TextureWrap.ClampToEdge;
		public TextureWrap wrapV = TextureWrap.ClampToEdge;
	}
}
