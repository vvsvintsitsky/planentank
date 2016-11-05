package wsvintsitsky.opengl.battle.nature;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import wsvintsitsky.opengl.battle.texture.TextureLoader;
import wsvintsitsky.opengl.math.VectorMath;

public class Ground {

	public static final float HEIGHT = -1f;
	
	private float x;
	private float y;
	private float z;
	private float lengthOffset;
	private float widthOffset;
	private Texture texture;
	
	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		this.z = z;
	}

	public float getLengthOffset() {
		return lengthOffset;
	}

	public void setLengthOffset(float lengthOffset) {
		this.lengthOffset = lengthOffset;
	}

	public float getWidthOffset() {
		return widthOffset;
	}

	public void setWidthOffset(float widthOffset) {
		this.widthOffset = widthOffset;
	}

	public Ground(float x, float y, float z, float length, float width) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.lengthOffset = length/2;
		this.widthOffset = width/2;
	}
	
	public void draw(final GL2 gl) {
		this.texture = TextureLoader.getInstance().getTexture(1);
		
		gl.glPushMatrix();
		
		float vNormal[] = new float[3];
		float vP1[] = new float[3];
		float vP2[] = new float[3];
		float vP3[] = new float[3];
		
		gl.glDisable(GL2.GL_TEXTURE_2D);
		float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT_AND_BACK, GL2.GL_DIFFUSE, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT_AND_BACK, GL2.GL_SHININESS, 0.5f);
        
		texture.enable(gl);
        texture.bind(gl);		
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = x - lengthOffset; vP1[1] = y; vP1[2] = z - widthOffset;
		vP2[0] = x + lengthOffset; vP2[1] = y; vP2[2] = z - widthOffset;
		vP3[0] = x + lengthOffset; vP3[1] = y; vP3[2] = z + widthOffset;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
        gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
        gl.glColor3ub((byte)255, (byte)255, (byte)255);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(x - lengthOffset, y, z - widthOffset);  // Bottom Left Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(x + lengthOffset, y, z - widthOffset);  // Bottom Right Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(x + lengthOffset, y, z + widthOffset);  // Top Right Of The Texture and Quad
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(x - lengthOffset, y, z + widthOffset);
		gl.glEnd();
		gl.glDisable(GL2.GL_TEXTURE_2D);
		
		gl.glPopMatrix();
	}

}