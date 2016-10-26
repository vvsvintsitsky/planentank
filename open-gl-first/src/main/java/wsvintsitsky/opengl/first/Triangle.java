package wsvintsitsky.opengl.first;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GLAutoDrawable;

public class Triangle implements GLEventListener {

	private static final GLU glu = new GLU();
	private Float[] coordinates;
	private float angle = 0.0f;

	public Triangle(Float... args) {
		super();
		this.coordinates = args;
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();

		gl.glLoadIdentity();
		
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glPushMatrix();
		
		gl.glTranslatef(0.125f, 0.25f, -3.0f);
		gl.glRotatef(angle, 0.0f, 1.0f, 0.0f);
		gl.glTranslatef(-0.125f, -0.25f, -0.0f);
		
		gl.glBegin(GL2.GL_TRIANGLES);
		gl.glColor3f(1.0f, 0f, 0f);
		gl.glVertex3f(coordinates[0], coordinates[1], coordinates[2]);
		gl.glVertex3f(coordinates[3], coordinates[4], coordinates[5]);
		gl.glVertex3f(coordinates[6], coordinates[7], coordinates[8]);

		gl.glEnd();
		gl.glFlush();
		
		gl.glPopMatrix();
		
		angle += 0.05f;
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