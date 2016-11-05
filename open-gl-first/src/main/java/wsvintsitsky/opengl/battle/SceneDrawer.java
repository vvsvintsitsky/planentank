package wsvintsitsky.opengl.battle;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;

import wsvintsitsky.opengl.battle.nature.Ground;
import wsvintsitsky.opengl.battle.nature.Rock;
import wsvintsitsky.opengl.battle.nature.Sky;
import wsvintsitsky.opengl.battle.plane.Plane;
import wsvintsitsky.opengl.battle.projectile.Shell;
import wsvintsitsky.opengl.battle.tank.TankBody;
import wsvintsitsky.opengl.battle.tank.TankGun;
import wsvintsitsky.opengl.battle.tank.Tank;
import wsvintsitsky.opengl.battle.tank.TankTurret;
import wsvintsitsky.opengl.battle.tank.TankWheel;
import wsvintsitsky.opengl.battle.texture.TextureLoader;
import wsvintsitsky.opengl.math.VectorMath;

import com.jogamp.opengl.GLAutoDrawable;

@SuppressWarnings({ "unused" })
public class SceneDrawer implements GLEventListener {

	private static final GLU glu = new GLU();

	private Ground ground;
	private Sky sky;
	private Plane plane;
	private Rock rock;

	private Tank tankOne;
	private Tank tankTwo;
	private Shell shell;

	private float angle;
	private int width;
	private int height;

	private float tankOneBodyAngleX = 0;
	private float tankOneTurretAngleX = 0;
	private float tankOneGunAngleY = 0;

	private float tankTwoX = 0;

	private float tankOneX = 0;
	private float tankOneZ = 3f;

	private GLProfile profile = GLProfile.get(GLProfile.GL2);

	private float PI = (float) Math.PI;

	float radius = -8.5f;

	float angleX = 0.5f, angleY = 0.1f;
	int xOrigin = -1, yOrigin = -1;
	float deltaAngleX = 0, deltaAngleY = 0;
	int current_time = 0;
	int shadowSize = 1024;
	private int[] textures = new int[1];

	float sPlane[] = { 1.0f, 0.0f, 0.0f, 0.0f };
	float tPlane[] = { 0.0f, 1.0f, 0.0f, 0.0f };
	float rPlane[] = { 0.0f, 0.0f, 1.0f, 0.0f };
	float qPlane[] = { 0.0f, 0.0f, 0.0f, 1.0f };

	float light0Pos[] = { -10.0f, 4.0f, 30f, 1.0f };
	float ligntVectorMult = 1;
	float lightSourceRadius = 0.3f;
	float ambientLight[] = { 0.7f, 0.7f, 0.7f, 1.0f };
	float diffuseLight[] = { 1.0f, 1.0f, 1.0f, 1.0f };
	float specularLight[] = { 1.0f, 1.0f, 1.0f, 1.0f };

	float lowAmbient[] = { 0.1f, 0.1f, 0.1f, 1.0f };
	float lowDiffuse[] = { 0.1f, 0.1f, 0.1f, 1.0f };
	float lowSpecular[] = { 0.1f, 0.1f, 0.1f, 1.0f };

	public SceneDrawer(int width, int height) {
		super();
		this.angle = 0.0f;
		this.width = width;
		this.height = height;
	}

	private void regenerateShadowMap(final GL2 gl) {
		float lightModelview[] = new float[16];
		float lightProjection[] = new float[16];

		gl.glMatrixMode(GL2.GL_PROJECTION);
		gl.glGetFloatv(GL2.GL_PROJECTION_MATRIX, lightProjection, 0);

		// Switch to light's point of view
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
		glu.gluLookAt(light0Pos[0], light0Pos[1], light0Pos[2], 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
		gl.glGetFloatv(GL2.GL_MODELVIEW_MATRIX, lightModelview, 0);

		gl.glViewport(0, 0, shadowSize, shadowSize);

		// Clear the window with current clearing color
		gl.glClear(GL2.GL_DEPTH_BUFFER_BIT);

		// All we care about here is resulting depth values
		gl.glShadeModel(GL2.GL_FLAT);
		gl.glDisable(GL2.GL_LIGHTING);
		gl.glDisable(GL2.GL_COLOR_MATERIAL);
		gl.glDisable(GL2.GL_NORMALIZE);
		gl.glColorMask(false, false, false, false);

		// Overcome imprecision
		gl.glEnable(GL2.GL_POLYGON_OFFSET_FILL);

		// Draw objects in the scene
		drawBattle(gl);

		// Copy depth values into depth texture
		gl.glCopyTexImage2D(GL2.GL_TEXTURE_2D, 0, GL2.GL_DEPTH_COMPONENT, 0, 0, shadowSize, shadowSize, 0);

		// Restore normal drawing state
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glColorMask(true, true, true, true);
		gl.glDisable(GL2.GL_POLYGON_OFFSET_FILL);

		// Set up texture matrix for shadow map projection
		gl.glMatrixMode(GL2.GL_TEXTURE);
		gl.glLoadIdentity();

		gl.glTranslatef(0.5f, 0.5f, 0.5f);
		gl.glScalef(0.5f, 0.5f, 0.5f);

		gl.glMultMatrixf(lightProjection, 0);
		gl.glMultMatrixf(lightModelview, 0);
	}

	@Override
	public void display(GLAutoDrawable drawable) {
		final GL2 gl = drawable.getGL().getGL2();

		tankOne.setX(tankOneX);
		tankOne.setZ(tankOneZ);
		tankOne.setTankBodyAngleX(tankOneBodyAngleX);
		tankOne.setTankTurretAngleX(tankOneTurretAngleX);
		tankOne.setTankGunAngleY(tankOneGunAngleY);

		tankTwo.setTankBodyAngleX(90);
		tankTwo.setX(tankTwoX);

		drawWithShadows(gl);
	}

	private void drawWithShadows(final GL2 gl) {
		regenerateShadowMap(gl);

		gl.glViewport(0, 0, width, height);

		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();

		gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);

		float eyeX = radius * -(float) Math.sin(Math.toRadians(tankOne.getTankBodyAngleX() + tankOne.getTankTurretAngleX()))
				* (float) Math.cos(Math.toRadians(angleY + deltaAngleY));
		float eyeY = radius * (float) Math.sin(Math.toRadians(tankOne.getTankGunAngleY()));
		float eyeZ = -radius * (float) Math.cos(Math.toRadians(tankOne.getTankBodyAngleX() + tankOne.getTankTurretAngleX()))
				* (float) Math.cos(Math.toRadians(angleY + deltaAngleY));

		glu.gluLookAt(eyeX + tankOne.getX(), eyeY, eyeZ + tankOne.getZ(), - (eyeX - tankOne.getX()), -eyeY, -(eyeZ - tankOne.getZ()), 0, 1, 0);

		gl.glPushMatrix();
		gl.glTranslatef(light0Pos[0], light0Pos[1], light0Pos[2]);
		gl.glColor3ub((byte) 255, (byte) 255, (byte) 40);
		glu.gluSphere(glu.gluNewQuadric(), lightSourceRadius, 30, 30);
		gl.glPopMatrix();

		// ------------------------------------------------------------------

		// Track light position
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0Pos, 0);

		// Because there is no support for an "ambient"
		// shadow compare fail value, we'll have to
		// draw an ambient pass first...
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, lowAmbient, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, lowDiffuse, 0);

		// Draw objects in the scene
		drawBattle(gl);

		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambientLight, 0);
		gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, diffuseLight, 0);

		// Set up shadow comparison
		gl.glEnable(GL2.GL_TEXTURE_2D);
		gl.glTexEnvi(GL2.GL_TEXTURE_ENV, GL2.GL_TEXTURE_ENV_MODE, GL2.GL_MODULATE);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_COMPARE_MODE, GL2.GL_COMPARE_R_TO_TEXTURE);

		// Enable alpha test so that shadowed fragments are discarded
		gl.glAlphaFunc(GL2.GL_GREATER, 0.9f);
		gl.glEnable(GL2.GL_ALPHA_TEST);

		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);

		// Set up the eye plane for projecting the shadow map on the scene
		gl.glEnable(GL2.GL_TEXTURE_GEN_S);
		gl.glEnable(GL2.GL_TEXTURE_GEN_T);
		gl.glEnable(GL2.GL_TEXTURE_GEN_R);
		gl.glEnable(GL2.GL_TEXTURE_GEN_Q);
		gl.glTexGenfv(GL2.GL_S, GL2.GL_EYE_PLANE, sPlane, 0);
		gl.glTexGenfv(GL2.GL_T, GL2.GL_EYE_PLANE, tPlane, 0);
		gl.glTexGenfv(GL2.GL_R, GL2.GL_EYE_PLANE, rPlane, 0);
		gl.glTexGenfv(GL2.GL_Q, GL2.GL_EYE_PLANE, qPlane, 0);

		// Draw objects in the scene
		drawBattle(gl);

		gl.glDisable(GL2.GL_ALPHA_TEST);
		gl.glDisable(GL2.GL_TEXTURE_2D);
		gl.glDisable(GL2.GL_TEXTURE_GEN_S);
		gl.glDisable(GL2.GL_TEXTURE_GEN_T);
		gl.glDisable(GL2.GL_TEXTURE_GEN_R);
		gl.glDisable(GL2.GL_TEXTURE_GEN_Q);

	}

	private void drawBattle(final GL2 gl) {
		gl.glActiveTexture(GL2.GL_TEXTURE0);

		ground.draw(gl);
		gl.glPushMatrix();
		if(shell != null) {
			if(!shell.checkForHit(tankTwo) && shell.getDistance() < 50) {
				shell.draw(gl);
			} else {
				shell = null;
			}
		}
		
		gl.glPopMatrix();
		gl.glPushMatrix();
		tankOne.draw(gl);
		tankTwo.draw(gl);
		gl.glPopMatrix();
		
		gl.glActiveTexture(GL2.GL_TEXTURE1);
	}

	@Override
	public void init(GLAutoDrawable gLDrawable) {
		final GL2 gl = gLDrawable.getGL().getGL2();

		light0Pos[0] *= ligntVectorMult;
		light0Pos[1] *= ligntVectorMult;
		light0Pos[2] *= ligntVectorMult;
		lightSourceRadius *= ligntVectorMult;

		gl.glClearColor(0.55f, 0.92f, 0.89f, 1.0f);
		gl.glClearDepth(1.0f);
		gl.glEnable(GL.GL_DEPTH_TEST);
		gl.glDepthFunc(GL.GL_LEQUAL);
		gl.glPolygonOffset(4.0f, 0.0f);
		gl.glShadeModel(GL2.GL_SMOOTH);
		gl.glEnable(GL2.GL_LIGHTING);
		gl.glEnable(GL2.GL_COLOR_MATERIAL);
		gl.glEnable(GL2.GL_NORMALIZE);
		gl.glEnable(GL2.GL_LIGHT0);
		gl.glLightModelf(GL2.GL_LIGHT_MODEL_TWO_SIDE, GL2.GL_TRUE);

		gl.glActiveTexture(GL2.GL_TEXTURE1);
		gl.glGenTextures(1, textures, 0);
		gl.glBindTexture(GL2.GL_TEXTURE_2D, textures[0]);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_S, GL2.GL_CLAMP);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_TEXTURE_WRAP_T, GL2.GL_CLAMP);
		gl.glTexParameteri(GL2.GL_TEXTURE_2D, GL2.GL_DEPTH_TEXTURE_MODE, GL2.GL_INTENSITY);

		gl.glTexGeni(GL2.GL_S, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		gl.glTexGeni(GL2.GL_T, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		gl.glTexGeni(GL2.GL_R, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);
		gl.glTexGeni(GL2.GL_Q, GL2.GL_TEXTURE_GEN_MODE, GL2.GL_EYE_LINEAR);

		loadTextures(gl);
		this.ground = new Ground(0, Ground.HEIGHT, 0, 2000, 2000);
		this.tankOne = new Tank(tankOneX, -0.75f, tankOneZ, 0);
		this.tankTwo = new Tank(tankTwoX, -0.75f, -3, 0);
		tankTwo.setMoving(false);
	}

	private void loadTextures(final GL2 gl) {
		TextureLoader.getInstance().loadTexture(gl, "armorQuad.png");
		TextureLoader.getInstance().loadTexture(gl, "ground.png");
		TextureLoader.getInstance().loadTexture(gl, "sky.png");
		TextureLoader.getInstance().loadTexture(gl, "wheel.png");
		TextureLoader.getInstance().loadTexture(gl, "shell.png");
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
		glu.gluPerspective(35.0f, h, 1.0, 1000.0);
		gl.glMatrixMode(GL2.GL_MODELVIEW);
		gl.glLoadIdentity();
	}

	@Override
	public void dispose(GLAutoDrawable arg0) {

	}

	public void keyPressed(KeyEvent e) {
		double sin;
		double cos;
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			tankOneTurretAngleX += 0.4f;
			break;
		case KeyEvent.VK_RIGHT:
			tankOneTurretAngleX -= 0.4f;
			break;
		case KeyEvent.VK_UP:
			if (tankOneGunAngleY < 10) {
				tankOneGunAngleY += 0.2f;
			}
			break;
		case KeyEvent.VK_DOWN:
			if (tankOneGunAngleY > -3) {
				tankOneGunAngleY -= 0.2f;
			}
			break;
		case KeyEvent.VK_A:
			tankOneBodyAngleX += 0.4f;
			tankOne.setMoving(true);
			break;
		case KeyEvent.VK_D:
			tankOneBodyAngleX -= 0.4f;
			tankOne.setMoving(true);
			break;
		case KeyEvent.VK_W:
			sin = Math.sin(Math.toRadians(tankOneBodyAngleX));
			cos = Math.cos(Math.toRadians(tankOneBodyAngleX));
			tankOneX -= 0.02f * sin;
			tankOneZ -= 0.02f * cos;
			tankOne.setMoving(true);
			break;
		case KeyEvent.VK_S:
			sin = Math.sin(Math.toRadians(tankOneBodyAngleX));
			cos = Math.cos(Math.toRadians(tankOneBodyAngleX));
			tankOneX += 0.01f * sin;
			tankOneZ += 0.01f * cos;
			tankOne.setMoving(true);
			break;
		case KeyEvent.VK_SPACE:
			fire();
			break;
		case KeyEvent.VK_R:
			this.tankTwo = new Tank(tankTwoX, -0.75f, -3, 0);
			break;
		}

	}

	private void fire() {
		this.shell = new Shell(tankOne.getTankGunX(), tankOne.getTankGunY(), tankOne.getTankGunZ(), 0.06f, 0.06f, 0.3f, tankOne.getTankBodyAngleX() + tankOne.getTankTurretAngleX(), tankOne.getTankGunAngleY());
	}

	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_A:
			tankOne.setMoving(false);
			break;
		case KeyEvent.VK_D:
			tankOne.setMoving(false);
			break;
		case KeyEvent.VK_W:
			tankOne.setMoving(false);
			break;
		case KeyEvent.VK_S:
			tankOne.setMoving(false);
			break;
		}
	}

	public void keyTyped(KeyEvent e) {

	}
}