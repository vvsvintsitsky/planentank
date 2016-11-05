package wsvintsitsky.opengl.battle.plane;

import com.jogamp.opengl.GL2;

public class Plane {
	
	private PlaneFrame planeFrame;
	private PlaneTailWing planeTailWingR;
	private PlaneTailWing planeTailWingL;
	private PlaneWing planeWingR;
	private PlaneWing planeWingL;
	private PlaneCabin planeCabin;
	
	private float angle = 0.0f;
	private Float x;
	private Float y;
	private Float z;
	private Float yAxisMul;
	private Float scale;
	private Float length;
	private Float height;
	private Float width;
	
	public Float getScale() {
		return scale;
	}

	public void setScale(Float scale) {
		this.scale = scale;
		this.planeCabin.setScale(scale);
		this.planeFrame.setScale(scale);
		this.planeTailWingR.setScale(scale);
		this.planeTailWingL.setScale(scale);
		this.planeWingR.setScale(scale);
		this.planeWingL.setScale(scale);
	}

	public float getAngle() {
		return angle;
	}

	public void setAngle(float angle) {
		this.angle = angle;
		this.planeCabin.setAngle(angle);
		this.planeFrame.setAngle(angle);
		this.planeTailWingR.setAngle(angle);
		this.planeTailWingL.setAngle(angle);
		this.planeWingR.setAngle(angle);
		this.planeWingL.setAngle(angle);
	}

	public Float getX() {
		return x;
	}

	public void setX(Float x) {
		this.x = x;
		this.planeCabin.setX(x);
		this.planeFrame.setX(x);
		this.planeTailWingR.setX(x);
		this.planeTailWingL.setX(x);
		this.planeWingR.setX(x);
		this.planeWingL.setX(x);
	}

	public Float getY() {
		return y;
	}

	public void setY(Float y) {
		this.y = y;
		this.planeCabin.setY(y);
		this.planeFrame.setY(y);
		this.planeTailWingR.setY(y);
		this.planeTailWingL.setY(y);
		this.planeWingR.setY(y);
		this.planeWingL.setY(y);
	}

	public Float getZ() {
		return z;
	}

	public void setZ(Float z) {
		this.z = z;
		this.planeCabin.setZ(z);
		this.planeFrame.setZ(z);
		this.planeTailWingR.setZ(z);
		this.planeTailWingL.setZ(z);
		this.planeWingR.setZ(z);
		this.planeWingL.setZ(z);
	}
	
	public Float getyAxisMul() {
		return yAxisMul;
	}

	public void setyAxisMul(Float yAxisMul) {
		this.yAxisMul = yAxisMul;
	}
	
	public Float getCenterX() {
		return (x + length)/2;
	}
	
	public Float getCenterY() {
		return (y + height)/2;
	}
	
	public Float getCenterZ() {
		return (z + width)/2;
	}

	public Plane(Float x, Float y, Float z, int width, int height, Float scale, float l, float h, float w) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = l;
		this.height = h;
		this.width = w;
		this.yAxisMul = (float) (width / height);
		this.scale = scale;
		this.planeFrame = new PlaneFrame(x, y, z, yAxisMul, scale, l, h*2/3 , w*2/16, 12, this);
		this.planeTailWingR = new PlaneTailWing(x + l/16, y + h/6, z + w*2/16, yAxisMul, scale, l*3/16, h/6, 0.03f, true, this);
		this.planeTailWingL = new PlaneTailWing(x + l/16, y + h/6, z, yAxisMul, scale, l*3/16, h/6, 0.03f, false, this);
		this.planeWingR = new PlaneWing(x + l*7/16, y + h/6, z + w*2/16, yAxisMul, scale, l*6/16, h/6, 0.07f, true, this);
		this.planeWingL = new PlaneWing(x + l*7/16, y + h/6, z, yAxisMul, scale, l*6/16, h/6, 0.07f, false, this);
		this.planeCabin = new PlaneCabin(x + l*10/16, y + h*3/6, z, yAxisMul, scale, l*4/16, h/3, w);
		
	}
	
	public void draw(final GL2 gl) {
		this.planeFrame.draw(gl);
		this.planeTailWingL.draw(gl);
		this.planeTailWingR.draw(gl);
		this.planeWingL.draw(gl);
		this.planeWingR.draw(gl);
	}
}
