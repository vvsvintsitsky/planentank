package wsvintsitsky.opengl.first;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLAutoDrawable;

public class Rock implements GLEventListener {

	private static final GLU glu = new GLU();
	private float angle = 0.0f;
	private Float x;
	private Float y;
	private Float z;
	private Float size;
	private Float scale;

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

	public Float getSize() {
		return size;
	}

	public void setSize(Float size) {
		this.size = size;
	}

	
	public Rock(Float x, Float y, Float z, int width, int height, float scale) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.size = ((float)width/(float)height);
		this.scale = scale;
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();

		gl.glLoadIdentity();

		gl.glTranslatef(0.0f, 0.0f, -3.0f);
		
//		gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
		
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3f(0.1f, 0.1f, 0.1f);
		gl.glVertex3f((x+0.01f), (y+0.00f)*size, z);
		gl.glVertex3f((x+0.00f), (y+0.01f)*size, z);
		gl.glVertex3f((x+0.00f), (y+0.02f)*size, z);
		gl.glVertex3f((x+0.01f), (y+0.03f)*size, z);
		gl.glVertex3f((x+0.02f), (y+0.03f)*size, z);
		gl.glVertex3f((x+0.03f), (y+0.02f)*size, z);
		gl.glVertex3f((x+0.03f), (y+0.01f)*size, z);
		gl.glVertex3f((x+0.02f), (y+0.00f)*size, z);
		
		gl.glEnd();		
		gl.glFlush();

//		angle += 0.05f;
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
		 glu.gluPerspective(50.0f, h/2, 1.0, 1000.0);
		 gl.glMatrixMode(GL2.GL_MODELVIEW);
		 gl.glLoadIdentity();
	}
}