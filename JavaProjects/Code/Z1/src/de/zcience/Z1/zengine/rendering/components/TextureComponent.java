package de.zcience.Z1.zengine.rendering.components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Pool.Poolable;

public class TextureComponent extends Component implements Poolable {

	public TextureRegion texture = null;
	public float width, height;
	
	@Override
	public void reset() {
		// don't dispose! texture is handled by the Assetmanager!
		texture = null;
		width = height = 0;
	}

}
