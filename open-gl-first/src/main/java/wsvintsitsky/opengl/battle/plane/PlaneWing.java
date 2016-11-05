package wsvintsitsky.opengl.battle.plane;

import com.jogamp.opengl.GL2;

public class PlaneWing {

	private float angle = 0.0f;
	private Float x;
	private Float y;
	private Float z;
	private Float yAxisMul;
	private Float scale;
	private Float[] coordinates;
	private Float centerX;
	private Float centerY;
	private Float centerZ;
	private Plane plane;
	
	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
	}

	public Float getZ() {
		return z;
	}

	public void setZ(Float z) {
		this.z = z;
	}

	public Float getyAxisMul() {
		return yAxisMul;
	}

	public void setyAxisMul(Float yAxisMul) {
		this.yAxisMul = yAxisMul;
	}

	public Float getCenterX() {
		return centerX;
	}

	public void setCenterX(Float centerX) {
		this.centerX = centerX;
	}

	public Float getCenterY() {
		return centerY;
	}

	public void setCenterY(Float centerY) {
		this.centerY = centerY;
	}

	public Float getCenterZ() {
		return centerZ;
	}

	public void setCenterZ(Float centerZ) {
		this.centerZ = centerZ;
	}

	public PlaneWing(Float x, Float y, Float z, Float yAxisMul, Float scale, float l, float h, float w,
			boolean isRight, Plane plane) {
		super();
		this.plane = plane;
		short sign;
		if (isRight) {
			sign = 1;
		} else {
			sign = -1;
		}
		this.x = x;
		this.y = y;
		this.z = z;
		this.scale = scale;
		this.coordinates = new Float[24];
		this.centerX = x + l / 2;
		this.centerY = y + h / 2;
		this.centerZ = z + w / 2;

		this.coordinates[0] = x;
		this.coordinates[1] = y * yAxisMul;
		this.coordinates[2] = z;
		this.coordinates[3] = x;
		this.coordinates[4] = y * yAxisMul;
		this.coordinates[5] = z + w * sign;
		this.coordinates[6] = x + l;
		this.coordinates[7] = y * yAxisMul;
		this.coordinates[8] = z;
		this.coordinates[9] = x + l;
		this.coordinates[10] = y * yAxisMul;
		this.coordinates[11] = z + w * sign;
		this.coordinates[12] = x;
		this.coordinates[13] = (y + h) * yAxisMul;
		this.coordinates[14] = z;
		this.coordinates[15] = x;
		this.coordinates[16] = (y + h) * yAxisMul;
		this.coordinates[17] = z + w * sign;
		this.coordinates[18] = x + l;
		this.coordinates[19] = (y + h) * yAxisMul;
		this.coordinates[20] = z;
		this.coordinates[21] = x + l;
		this.coordinates[22] = (y + h) * yAxisMul;
		this.coordinates[23] = z + w * sign;
	}

	public void draw(final GL2 gl) {
		gl.glPushMatrix();

		gl.glTranslatef(plane.getX(), plane.getY(), plane.getZ());
		gl.glScalef(scale, scale, scale);
		gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
		
		gl.glTranslatef(-plane.getX(), -plane.getY(), -plane.getZ());

		gl.glBegin(GL2.GL_QUAD_STRIP);
		gl.glColor3f(0.0f, 0.5f, 0.5f);
		gl.glVertex3f(this.coordinates[0], this.coordinates[1], this.coordinates[2]);
		gl.glVertex3f(this.coordinates[3], this.coordinates[4], this.coordinates[5]);
		gl.glVertex3f(this.coordinates[6], this.coordinates[7], this.coordinates[8]);
		gl.glVertex3f(this.coordinates[9], this.coordinates[10], this.coordinates[11]);
		gl.glVertex3f(this.coordinates[21], this.coordinates[22], this.coordinates[23]);
		gl.glVertex3f(this.coordinates[18], this.coordinates[19], this.coordinates[20]);
		gl.glVertex3f(this.coordinates[15], this.coordinates[16], this.coordinates[17]);
		gl.glVertex3f(this.coordinates[12], this.coordinates[13], this.coordinates[14]);
		gl.glVertex3f(this.coordinates[3], this.coordinates[4], this.coordinates[5]);
		gl.glVertex3f(this.coordinates[0], this.coordinates[1], this.coordinates[2]);
		gl.glEnd();

		gl.glBegin(GL2.GL_QUADS);
		gl.glColor3f(0.0f, 0.5f, 0.5f);
		gl.glVertex3f(this.coordinates[0], this.coordinates[1], this.coordinates[2]);
		gl.glVertex3f(this.coordinates[6], this.coordinates[7], this.coordinates[8]);
		gl.glVertex3f(this.coordinates[12], this.coordinates[13], this.coordinates[14]);
		gl.glVertex3f(this.coordinates[18], this.coordinates[19], this.coordinates[20]);

		gl.glVertex3f(this.coordinates[3], this.coordinates[4], this.coordinates[5]);
		gl.glVertex3f(this.coordinates[9], this.coordinates[10], this.coordinates[11]);
		gl.glVertex3f(this.coordinates[21], this.coordinates[22], this.coordinates[23]);
		gl.glVertex3f(this.coordinates[15], this.coordinates[16], this.coordinates[17]);
		gl.glEnd();

		gl.glPopMatrix();
	}
}