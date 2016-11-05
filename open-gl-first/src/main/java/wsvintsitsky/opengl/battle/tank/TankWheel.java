package wsvintsitsky.opengl.battle.tank;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;

import wsvintsitsky.opengl.battle.texture.TextureLoader;

public class TankWheel {

	private float angleX = 0.0f;
	private float x;
	private float y;
	private float z;
	private float widthOffset;
	private float heightOffset;
	private float lengthOffset;
	private float coordinates[];
	private int planes;
	private double step;
	private float texStep;
	private Tank tank;
	
	private Texture texture;

	public float getAngleX() {
		return angleX;
	}

	public void setAngleX(float angle) {
		this.angleX = angle;
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

	public TankWheel(float x, float y, float z, float width, float height, float length, int planes, Tank tank) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.widthOffset = width/2;
		this.heightOffset = height/2;
		this.lengthOffset = length/2;
		this.tank = tank;
		this.planes = planes;
		this.step = (double)360/planes;
		this.texStep = (float)1/planes;
		double sin;
		double cos;
		this.coordinates = new float[planes*3];
		for(int i = 0; i < planes; i++) {
			this.coordinates[i*3] = x - widthOffset;
			sin = Math.sin(Math.toRadians(step*i));
			cos = Math.cos(Math.toRadians(step*i));
			this.coordinates[i*3 + 1] = y + (float)sin*heightOffset;
			this.coordinates[i*3 + 2] = z + (float)cos*lengthOffset;
		}
		this.angleX = 0;
	}

	public void draw(final GL2 gl) {	
		double sin;
		double cos;
		this.texture = TextureLoader.getInstance().getTexture(3);
		
		gl.glTranslatef(getX(), getY(), getZ());
		
		gl.glPushMatrix();
		if(tank.isMoving()) {
			gl.glRotatef(-angleX, 1.0f, 0.0f, 0.0f);
		}
		
	
		float[] rgba = {1f, 1f, 1f};
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT, rgba, 0);
        gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_DIFFUSE, rgba, 0);
        gl.glMaterialf(GL2.GL_FRONT, GL2.GL_SHININESS, 0.5f);	
		
        texture.enable(gl);
        texture.bind(gl);
        
        
		gl.glBegin(GL2.GL_QUAD_STRIP);
		gl.glColor3ub((byte)255, (byte)255, (byte)255);
		for(int i = 0; i < planes; i++) {
			gl.glTexCoord2f(0, texStep*i);
			gl.glVertex3f(this.coordinates[i*3], this.coordinates[i*3 + 1], this.coordinates[i*3 + 2]);
			gl.glTexCoord2f(0.25f, texStep*i);
			gl.glVertex3f(this.coordinates[i*3] + widthOffset*2, this.coordinates[i*3 + 1], this.coordinates[i*3 + 2]);
		}
		gl.glTexCoord2f(0, 1.0f);
		gl.glVertex3f(this.coordinates[0], this.coordinates[1], this.coordinates[2]);
		gl.glTexCoord2f(0.25f, 1.0f);
		gl.glVertex3f(this.coordinates[0] + widthOffset*2, this.coordinates[1], this.coordinates[2]);
		gl.glEnd();
				
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3ub((byte)255, (byte)255, (byte)255);
		for(int i = 0; i < planes; i++) {
			sin = Math.sin(Math.toRadians(step*i));
			cos = Math.cos(Math.toRadians(step*i));
			gl.glTexCoord2f(0.5f + 0.5f*(float)cos, 0.5f + 0.5f*(float)sin);
			gl.glVertex3f(this.coordinates[i*3], this.coordinates[i*3 + 1], this.coordinates[i*3 + 2]);
		}
		gl.glEnd();
				
		gl.glBegin(GL2.GL_POLYGON);
		gl.glColor3ub((byte)255, (byte)255, (byte)255);
		for(int i = 0; i < planes; i++) {
			sin = Math.sin(Math.toRadians(step*i));
			cos = Math.cos(Math.toRadians(step*i));
			gl.glTexCoord2f(0.5f + 0.5f*(float)cos, 0.5f + 0.5f*(float)sin);
			gl.glVertex3f(this.coordinates[i*3] + widthOffset*2, this.coordinates[i*3 + 1], this.coordinates[i*3 + 2]);
		}
		gl.glEnd();
				
		
		gl.glDisable(GL2.GL_TEXTURE_2D);
		
		gl.glPopMatrix();
		angleX += 0.05f;
	}

}