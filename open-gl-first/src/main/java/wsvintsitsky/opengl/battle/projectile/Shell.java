package wsvintsitsky.opengl.battle.projectile;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import wsvintsitsky.opengl.battle.tank.Tank;
import wsvintsitsky.opengl.battle.texture.TextureLoader;
import wsvintsitsky.opengl.math.VectorMath;

public class Shell {

	private float x;
	private float y;
	private float z;
	private float widthOffset;
	private float heightOffset;
	private float lengthOffset;
	private float angleX;
	private float angleY;
	private float sinX;
	private float sinY;
	private float cosZ;
	private float distance = 0;
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

	public float getHeightOffset() {
		return heightOffset;
	}

	public float getLengthOffset() {
		return lengthOffset;
	}

	public float getAngleX() {
		return angleX;
	}

	public void setAngleX(float angleX) {
		this.angleX = angleX;
	}

	public float getAngleY() {
		return angleY;
	}

	public void setAngleY(float angleY) {
		this.angleY = angleY;
	}

	public float getDistance() {
		return distance;
	}

	public Shell(float x, float y, float z, float width, float height, float length, float angleX, float angleY) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.widthOffset = width / 2;
		this.heightOffset = height / 2;
		this.lengthOffset = length / 2;
		this.angleX = angleX;
		this.angleY = angleY;
		this.sinX = (float) Math.sin(Math.toRadians(angleX));
		this.sinY = (float) Math.sin(Math.toRadians(angleY));
		this.cosZ = (float) Math.cos(Math.toRadians(angleX));
	}

	public void draw(final GL2 gl) {
		this.texture = TextureLoader.getInstance().getTexture(4);

		gl.glTranslatef(getX(), getY(), getZ());

		gl.glPushMatrix();
		gl.glRotatef(angleX, 0.0f, 1.0f, 0.0f);
		gl.glRotatef(angleY, 1.0f, 0.0f, 0.0f);

		float vNormal[] = new float[3];
		float vP1[] = new float[3];
		float vP2[] = new float[3];
		float vP3[] = new float[3];

		gl.glDisable(GL2.GL_TEXTURE_2D);
		float[] rgba = { 1f, 1f, 1f };
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
		gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, rgba, 0);
		gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);

		texture.enable(gl);
		texture.bind(gl);

		// FRONT
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset;
		vP1[1] = -heightOffset;
		vP1[2] = -lengthOffset;
		vP2[0] = widthOffset;
		vP2[1] = -heightOffset;
		vP2[2] = -lengthOffset;
		vP3[0] = widthOffset;
		vP3[1] = heightOffset;
		vP3[2] = -lengthOffset;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
		gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
		gl.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-widthOffset, -heightOffset, -lengthOffset); // Bottom
																	// Left Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(widthOffset, -heightOffset, -lengthOffset); // Bottom
																	// Right Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(widthOffset, heightOffset, -lengthOffset); // Top Right Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-widthOffset, heightOffset, -lengthOffset);
		gl.glEnd();

		// BACK
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset;
		vP1[1] = -heightOffset;
		vP1[2] = lengthOffset;
		vP2[0] = widthOffset;
		vP2[1] = -heightOffset;
		vP2[2] = lengthOffset;
		vP3[0] = widthOffset;
		vP3[1] = heightOffset;
		vP3[2] = lengthOffset;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
		gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
		gl.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-widthOffset, -heightOffset, lengthOffset); // Bottom Left
																	// Of The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(widthOffset, -heightOffset, lengthOffset); // Bottom Right
																	// Of The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(widthOffset, heightOffset, lengthOffset); // Top Right Of
																// The Texture
																// and Quad
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-widthOffset, heightOffset, lengthOffset);
		gl.glEnd();

		// TOP
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset;
		vP1[1] = heightOffset;
		vP1[2] = -lengthOffset;
		vP2[0] = widthOffset;
		vP2[1] = heightOffset;
		vP2[2] = -lengthOffset;
		vP3[0] = widthOffset;
		vP3[1] = heightOffset;
		vP3[2] = lengthOffset;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
		gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
		gl.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-widthOffset, heightOffset, -lengthOffset); // Bottom Left
																	// Of The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(widthOffset, heightOffset, -lengthOffset); // Bottom Right
																	// Of The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(widthOffset, heightOffset, lengthOffset); // Top Right Of
																// The Texture
																// and Quad
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-widthOffset, heightOffset, lengthOffset);
		gl.glEnd();

		// BOTTOM
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset;
		vP1[1] = -heightOffset;
		vP1[2] = -lengthOffset;
		vP2[0] = widthOffset;
		vP2[1] = -heightOffset;
		vP2[2] = -lengthOffset;
		vP3[0] = widthOffset;
		vP3[1] = -heightOffset;
		vP3[2] = lengthOffset;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
		gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
		gl.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-widthOffset, -heightOffset, -lengthOffset); // Bottom
																	// Left Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(widthOffset, -heightOffset, -lengthOffset); // Bottom
																	// Right Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(widthOffset, -heightOffset, lengthOffset); // Top Right Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-widthOffset, -heightOffset, lengthOffset);
		gl.glEnd();

		// RIGHT
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = widthOffset;
		vP1[1] = -heightOffset;
		vP1[2] = -lengthOffset;
		vP2[0] = widthOffset;
		vP2[1] = -heightOffset;
		vP2[2] = lengthOffset;
		vP3[0] = widthOffset;
		vP3[1] = heightOffset;
		vP3[2] = lengthOffset;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
		gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
		gl.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(widthOffset, -heightOffset, -lengthOffset); // Bottom Left
																	// Of The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(widthOffset, -heightOffset, lengthOffset); // Bottom Right
																	// Of The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(widthOffset, heightOffset, lengthOffset); // Top Right Of
																// The Texture
																// and Quad
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(widthOffset, heightOffset, -lengthOffset);
		gl.glEnd();

		// LEFT
		gl.glBegin(GL2.GL_QUADS);
		vP1[0] = -widthOffset;
		vP1[1] = -heightOffset;
		vP1[2] = -lengthOffset;
		vP2[0] = -widthOffset;
		vP2[1] = -heightOffset;
		vP2[2] = lengthOffset;
		vP3[0] = -widthOffset;
		vP3[1] = heightOffset;
		vP3[2] = lengthOffset;
		VectorMath.getInstance().getNormalVector(vP1, vP2, vP3, vNormal);
		gl.glNormal3f(vNormal[0], vNormal[1], vNormal[2]);
		gl.glColor3ub((byte) 255, (byte) 255, (byte) 255);
		gl.glTexCoord2f(0.0f, 0.0f);
		gl.glVertex3f(-widthOffset, -heightOffset, -lengthOffset); // Bottom
																	// Left Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 0.0f);
		gl.glVertex3f(-widthOffset, -heightOffset, lengthOffset); // Bottom
																	// Right Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(1.0f, 1.0f);
		gl.glVertex3f(-widthOffset, heightOffset, lengthOffset); // Top Right Of
																	// The
																	// Texture
																	// and Quad
		gl.glTexCoord2f(0.0f, 1.0f);
		gl.glVertex3f(-widthOffset, heightOffset, -lengthOffset);
		gl.glEnd();

		gl.glDisable(GL2.GL_TEXTURE_2D);

		gl.glPopMatrix();

		this.x -= this.sinX * 0.02f;
		this.y += this.sinY * 0.02f;
		this.z -= this.cosZ * 0.02f;
		this.distance += 0.02f;
	}

	public boolean checkForHit(Tank tank) {
		boolean xCondition = this.getX() < tank.getX() + tank.getWidthOffset()
				&& this.getX() > tank.getX() - tank.getWidthOffset();
		boolean yCondition = this.getY() < tank.getY() + tank.getHeightOffset()
				&& this.getY() > tank.getY() - tank.getHeightOffset();
		boolean zCondition = this.getZ() < tank.getZ() + tank.getLengthOffset()
				&& this.getZ() > tank.getZ() - tank.getLengthOffset();
		System.out.println(xCondition + " " + yCondition + " " + zCondition);
		if (xCondition && yCondition && zCondition) {
			tank.setBlown(true);
			return true;
		} else {

			return false;
		}
	}
}
