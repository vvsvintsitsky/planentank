package wsvintsitsky.opengl.first;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLAutoDrawable;

public class PlaneWing implements GLEventListener {

	private static final GLU glu = new GLU();
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
		// this.coordinates[0] = x; this.coordinates[1] = y * yAxisMul;
		// this.coordinates[2] = z + w;
		// this.coordinates[3] = x; this.coordinates[4] = (y + h) * yAxisMul;
		// this.coordinates[5] = z + w;
		// this.coordinates[6] = x + l; this.coordinates[7] = (y + h) *
		// yAxisMul; this.coordinates[8] = z + w;
		// this.coordinates[9] = x + l; this.coordinates[10] = y * yAxisMul;
		// this.coordinates[11] = z + w;

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

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();

		gl.glLoadIdentity();

//		gl.glScalef(scale, scale, scale);
//		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();

		gl.glTranslatef(plane.getX(), plane.getY(), plane.getZ());
//		gl.glTranslatef(centerX, centerY, centerZ -3.0f);
		gl.glScalef(scale, scale, scale);
		gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
		
		gl.glTranslatef(-plane.getX(), -plane.getY(), -plane.getZ());
//		gl.glTranslatef(-centerX, -centerZ, -centerZ);

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

		gl.glFlush();
		gl.glPopMatrix();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {

	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		final GL2 gl = drawable.getGL().getGL2();
		if (height <= 0) {
			height = 1;
		}
		final float h = (float) width / (float) height;
		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glLoadIdentity();
		glu.gluPerspective(50.0f, h / 2, 1.0, 1000.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}
}