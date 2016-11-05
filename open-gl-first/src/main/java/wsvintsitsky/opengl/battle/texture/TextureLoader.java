package wsvintsitsky.opengl.battle.texture;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

public class TextureLoader {

	private GLProfile profile = GLProfile.get(GLProfile.GL2);

	private List<Texture> textures;

	private static final TextureLoader INSTANCE = new TextureLoader();

	private TextureLoader() {
		this.textures = new ArrayList<Texture>();
	}

	public static TextureLoader getInstance() {
		return INSTANCE;
	}

	public void loadTexture(final GL2 gl, String filename) {
		gl.glActiveTexture(GL2.GL_TEXTURE0);
		try {
			InputStream textureStream = this.getClass().getResourceAsStream(filename);
			TextureData textureData = TextureIO.newTextureData(profile, textureStream, false, "png");
			Texture texture = TextureIO.newTexture(textureData);
			texture.bind(gl);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_LINEAR);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
			gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
			gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
			this.textures.add(texture);
		} catch (IOException exc) {
			exc.printStackTrace();
			System.exit(1);
		}
	}

	public Texture getTexture(int position) {
		if (position >= 0 && position < this.textures.size()) {
			return this.textures.get(position);
		} else {
			throw new RuntimeException("No such texture!");
		}
	}
}
