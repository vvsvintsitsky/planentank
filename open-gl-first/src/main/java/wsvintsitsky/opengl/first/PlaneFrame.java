package wsvintsitsky.opengl.first;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLAutoDrawable;

public class PlaneFrame implements GLEventListener {

	private static final GLU glu = new GLU();
	private float angle = 0.0f;
	private Float x;
	private Float y;
	private Float z;
	private Float yAxisMul;
	private Float scale;
	private Float coordinates[];
	private Float centerX;
	private Float centerY;
	private Float centerZ;
	private Integer planes;
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

	public PlaneFrame(Float x, Float y, Float z, Float yAxisMul, Float scale, float l, float h, float w, int planes, Plane plane) {
		super();
		this.plane = plane;
		this.x = x;
		this.y = y;
		this.z = z;
		this.scale = scale;
		this.centerX = x + l/2;
		this.centerY = y + h/2;
		this.centerZ = z + w/2;
		this.planes = planes;
		double step = (double)360/planes;
		double sin;
		double cos;
		this.coordinates = new Float[planes*3];
		for(int i = 0; i < planes; i++) {
			this.coordinates[i*3] = x;
			sin = Math.sin(Math.toRadians(step*i));
			cos = Math.cos(Math.toRadians(step*i));
			this.coordinates[i*3 + 1] = (float) (y + (1 + sin)*h/2);
			this.coordinates[i*3 + 2] = (float) (z + (1 + cos)*w/2);
		}
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();		
		
		gl.glLoadIdentity();
		
//		gl.glScalef(scale, scale, scale);
		
		gl.glPushMatrix();
		
		gl.glTranslatef(plane.getX(), plane.getY(), plane.getZ());
//		gl.glTranslatef(centerX, centerY, centerZ -3.0f);
		gl.glScalef(scale, scale, scale);
		gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
		
		gl.glTranslatef(-plane.getX(), -plane.getY(), -plane.getZ());
//		gl.glTranslatef(-centerX, -centerZ, -centerZ);		
		gl.glBegin(GL2.GL_QUAD_STRIP);
		gl.glColor3f(1.0f, 0f, 0f);
		
		for(int i = 0; i < planes; i++) {
			gl.glVertex3f(this.coordinates[i*3], this.coordinates[i*3 + 1], this.coordinates[i*3 + 2]);
			gl.glVertex3f(this.coordinates[i*3] + (centerX - x)*2, this.coordinates[i*3 + 1], this.coordinates[i*3 + 2]);
		}
		gl.glVertex3f(this.coordinates[0], this.coordinates[1], this.coordinates[2]);
		gl.glVertex3f(this.coordinates[0] + (centerX - x)*2, this.coordinates[1], this.coordinates[2]);
		gl.glEnd();
		
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(1.0f, 0f, 0f);
		for(int i = 0; i < planes; i++) {
			gl.glVertex3f(this.coordinates[i*3], this.coordinates[i*3 + 1], this.coordinates[i*3 + 2]);
		}
		gl.glEnd();
		
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(1.0f, 0f, 0f);
		for(int i = 0; i < planes; i++) {
			gl.glVertex3f(this.coordinates[i*3] + (centerX - x)*2, this.coordinates[i*3 + 1], this.coordinates[i*3 + 2]);
		}
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