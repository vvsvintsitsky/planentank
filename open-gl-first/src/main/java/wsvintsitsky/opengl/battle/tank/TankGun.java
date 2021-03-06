package wsvintsitsky.opengl.battle.tank;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import wsvintsitsky.opengl.battle.texture.TextureLoader;
import wsvintsitsky.opengl.math.VectorMath;

public class TankGun {

	private float x;
	private float y;
	private float z;
	private float widthOffset;
	private float heightOffset;
	private float lengthOffset;
	private float angleY;
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

	public float getWidthOffset() {
		return widthOffset;
	}

	public void setWidthOffset(float widthOffset) {
		this.widthOffset = widthOffset;
	}

	public float getHeightOffset() {
		return heightOffset;
	}

	public void setHeightOffset(float heightOffset) {
		this.heightOffset = heightOffset;
	}

	public float getLengthOffset() {
		return lengthOffset;
	}

	public void setLengthOffset(float lengthOffset) {
		this.lengthOffset = lengthOffset;
	}

	public float getAngleY() {
		return angleY;
	}

	public void setAngleY(float angleY) {
		if(angleY < 15 || angleY > -3) {
			this.angleY = angleY;
		}
	}

	public TankGun(float x, float y, float z, float width, float height, float length) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.widthOffset = width/2;
		this.heightOffset = height/2;
		this.lengthOffset = length;
		this.angleY = 0;
	}
	
	public void draw(final GL2 gl) {
		this.texture = TextureLoader.getInstance().getTexture(0);
		
		gl.glRotatef(angleY, 1.0f, 0.0f, 0.0f);
		
		float vNormal[] = new float[3];
		float vP1[] = new float[3];
		float vP2[] = new float[3];
		float vP3[] = new float[3];
		
		gl.glDisable(GL2.GL_TEXTURE_2D);
		float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);		
        
        texture.enable(gl);
        texture.bind(gl);
        
        // FRONT		
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset; vP1[1] = -heightOffset; vP1[2] = -lengthOffset;
		vP2[0] = widthOffset; vP2[1] = -heightOffset; vP2[2] = -lengthOffset;
		vP3[0] = widthOffset; vP3[1] = heightOffset; vP3[2] = -lengthOffset;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
        gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
        gl.glColor3ub((byte)255, (byte)255, (byte)255);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-widthOffset, -heightOffset, -lengthOffset);  // Bottom Left Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(widthOffset, -heightOffset, -lengthOffset);  // Bottom Right Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(widthOffset, heightOffset, -lengthOffset);  // Top Right Of The Texture and Quad
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-widthOffset, heightOffset, -lengthOffset);
		gl.glEnd();
		
		// BACK	
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset; vP1[1] = -heightOffset; vP1[2] = 0;
		vP2[0] = widthOffset; vP2[1] = -heightOffset; vP2[2] = 0;
		vP3[0] = widthOffset; vP3[1] = heightOffset; vP3[2] = 0;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
        gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
        gl.glColor3ub((byte)255, (byte)255, (byte)255);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-widthOffset, -heightOffset, 0);  // Bottom Left Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(widthOffset, -heightOffset, 0);  // Bottom Right Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(widthOffset, heightOffset, 0);  // Top Right Of The Texture and Quad
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-widthOffset, heightOffset, 0);
		gl.glEnd();
		
		// TOP		
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset; vP1[1] = heightOffset; vP1[2] = -lengthOffset;
		vP2[0] = widthOffset; vP2[1] = heightOffset; vP2[2] = -lengthOffset;
		vP3[0] = widthOffset; vP3[1] = heightOffset; vP3[2] = 0;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
        gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
        gl.glColor3ub((byte)255, (byte)255, (byte)255);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-widthOffset, heightOffset, -lengthOffset);  // Bottom Left Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(widthOffset, heightOffset, -lengthOffset);  // Bottom Right Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(widthOffset, heightOffset, 0);  // Top Right Of The Texture and Quad
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-widthOffset, heightOffset, 0);
		gl.glEnd();
		
		// BOTTOM	
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset; vP1[1] = -heightOffset; vP1[2] = -lengthOffset;
		vP2[0] = widthOffset; vP2[1] = -heightOffset; vP2[2] = -lengthOffset;
		vP3[0] = widthOffset; vP3[1] = -heightOffset; vP3[2] = 0;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
        gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
        gl.glColor3ub((byte)255, (byte)255, (byte)255);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-widthOffset, -heightOffset, -lengthOffset);  // Bottom Left Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(widthOffset, -heightOffset, -lengthOffset);  // Bottom Right Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(widthOffset, -heightOffset, 0);  // Top Right Of The Texture and Quad
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-widthOffset, -heightOffset, 0);
		gl.glEnd();
		
		// RIGHT		
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = widthOffset; vP1[1] = -heightOffset; vP1[2] = -lengthOffset;
		vP2[0] = widthOffset; vP2[1] = -heightOffset; vP2[2] = 0;
		vP3[0] = widthOffset; vP3[1] = heightOffset; vP3[2] = 0;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
        gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
        gl.glColor3ub((byte)255, (byte)255, (byte)255);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(widthOffset, -heightOffset, -lengthOffset);  // Bottom Left Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(widthOffset, -heightOffset, 0);  // Bottom Right Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(widthOffset, heightOffset, 0);  // Top Right Of The Texture and Quad
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(widthOffset, heightOffset, -lengthOffset);
		gl.glEnd();
		
		// LEFT	
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset; vP1[1] = -heightOffset; vP1[2] = -lengthOffset;
		vP2[0] = -widthOffset; vP2[1] = -heightOffset; vP2[2] = 0;
		vP3[0] = -widthOffset; vP3[1] = heightOffset; vP3[2] = 0;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
        gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
        gl.glColor3ub((byte)255, (byte)255, (byte)255);
		gl.glTexCoord2f(0.0f, 0.0f); gl.glVertex3f(-widthOffset, -heightOffset, -lengthOffset);  // Bottom Left Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 0.0f); gl.glVertex3f(-widthOffset, -heightOffset, 0);  // Bottom Right Of The Texture and Quad
		gl.glTexCoord2f(1.0f, 1.0f); gl.glVertex3f(-widthOffset, heightOffset, 0);  // Top Right Of The Texture and Quad
		gl.glTexCoord2f(0.0f, 1.0f); gl.glVertex3f(-widthOffset, heightOffset, -lengthOffset);
		gl.glEnd();
		
		
		gl.glDisable(GL2.GL_TEXTURE_2D);
	}
}
