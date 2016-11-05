package wsvintsitsky.opengl.battle.tank;

import java.util.ArrayList;
import java.util.List;

import com.jogamp.opengl.GL2;

public class Tank {

	private TankBody tankBody;
	private TankTurret tankTurret;
	private List<TankWheel> tankWheels = new ArrayList<TankWheel>();

	private float x;
	private float y;
	private float z;

	private float tankBodyAngleX;
	private float tankTurretAngleX;
	private float tankGunAngleY;

	private float scale;

	private boolean isMoving;

	private boolean isBlown;
	private boolean turretUpwards;
	private boolean finished;

	public float getX() {
		return x;
	}

	public void setX(float x) {
		if (!isBlown) {
			this.x = x;
		}
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		if (!isBlown) {
			this.y = y;
		}
	}

	public float getZ() {
		return z;
	}

	public void setZ(float z) {
		if (!isBlown) {
			this.z = z;
		}
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public float getTankBodyAngleX() {
		return tankBodyAngleX;
	}

	public void setTankBodyAngleX(float tankBodyAngleX) {
		this.tankBodyAngleX = tankBodyAngleX;
		this.tankBody.setAngleX(tankBodyAngleX);
	}

	public float getTankTurretAngleX() {
		return tankTurretAngleX;
	}

	public void setTankTurretAngleX(float tankTurretAngleX) {
		this.tankTurretAngleX = tankTurretAngleX;
		this.tankTurret.setAngleX(tankTurretAngleX);
	}

	public float getTankGunAngleY() {
		return tankGunAngleY;
	}

	public void setTankGunAngleY(float tankGunAngleY) {
		this.tankGunAngleY = tankGunAngleY;
		this.tankTurret.getTankGun().setAngleY(tankGunAngleY);
	}

	public boolean isMoving() {
		return isMoving;
	}

	public void setMoving(boolean isMoving) {
		this.isMoving = isMoving;
	}

	public boolean isBlown() {
		return isBlown;
	}

	public void setBlown(boolean isBlown) {
		this.isBlown = isBlown;
		this.isMoving = false;
	}

	public float getWidthOffset() {
		return 2*this.tankBody.getWidthOffset();
	}
	
	public float getLengthOffset() {
		return 2*this.tankBody.getLengthOffset();
	}

	public float getHeightOffset() {
		return 2*this.tankBody.getHeightOffset();
	}

	public float getTankGunX() {
		return this.tankBody.getX() + this.tankTurret.getX() + this.x;
	}

	public float getTankGunY() {
		return this.tankBody.getHeightOffset() + this.tankTurret.getHeightOffset() + this.y;
	}

	public float getTankGunZ() {
		return this.tankBody.getZ() + this.tankTurret.getZ() + this.z;
	}

	public Tank(float x, float y, float z, float scale) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.isBlown = false;
		this.turretUpwards = true;
		this.finished = false;
		this.isMoving = false;
		this.tankBody = new TankBody(0, 0, 0, 0.5f, 0.3f, 2f);
		this.tankTurret = new TankTurret(0, 0, 0, 0.6f, 0.2f, 0.8f, this);
		this.tankWheels.add(new TankWheel(0, 0, 0, 0.1f, 0.3f, 0.3f, 12, this));
		this.tankWheels.add(new TankWheel(0, 0, 0, 0.1f, 0.3f, 0.3f, 12, this));
		this.tankWheels.add(new TankWheel(0, 0, 0, 0.1f, 0.3f, 0.3f, 12, this));
		this.tankWheels.add(new TankWheel(0, 0, 0, 0.1f, 0.3f, 0.3f, 12, this));
		this.tankWheels.add(new TankWheel(0, 0, 0, 0.1f, 0.3f, 0.3f, 12, this));
		this.tankWheels.add(new TankWheel(0, 0, 0, 0.1f, 0.3f, 0.3f, 12, this));
	}

	public void draw(final GL2 gl) {
		gl.glPushMatrix();

		gl.glTranslatef(x, y, z);
		tankBody.draw(gl);

		gl.glTranslatef(0, tankBody.getHeightOffset() + tankTurret.getHeightOffset(), 0.2f);

		if (this.isBlown) {
			if (!finished) {
				if (turretUpwards) {
					if (tankTurret.getY() < getY() + 1f) {
						tankTurret.setX(tankTurret.getX() - 0.001f);
						tankTurret.setY(tankTurret.getY() + 0.0005f);
						tankTurret.setZ(tankTurret.getZ() + 0.0005f);
						tankTurret.setAngleX(tankTurretAngleX);
						tankTurret.setAngleY(tankTurretAngleX);
						tankTurret.setAngleZ(tankTurretAngleX);
						tankTurretAngleX += 0.01f;
					} else {
						turretUpwards = false;
					}
				} else {
					if (tankTurret.getY() > getY() + tankBody.getHeightOffset() + tankWheels.get(0).getHeightOffset()
							- 0.05) {
						tankTurret.setX(tankTurret.getX() - 0.001f);
						tankTurret.setY(tankTurret.getY() - 0.0005f);
						tankTurret.setZ(tankTurret.getZ() + 0.0005f);
						tankTurret.setAngleX(tankTurretAngleX);
						tankTurret.setAngleY(tankTurretAngleX);
						tankTurret.setAngleZ(tankTurretAngleX);
						tankTurretAngleX += 0.01f;
					} else {
						finished = true;
					}
				}
			}

		}
		tankTurret.draw(gl);
		gl.glTranslatef(-(tankBody.getWidthOffset() + tankWheels.get(0).getWidthOffset()),
				-(2 * tankBody.getHeightOffset() + tankTurret.getHeightOffset()) + 0.05f, -tankBody.getLengthOffset());
		tankWheels.get(0).draw(gl);
		gl.glTranslatef(0, 0, tankBody.getLengthOffset());
		tankWheels.get(1).draw(gl);
		gl.glTranslatef(0, 0, tankBody.getLengthOffset() - 0.4f);
		tankWheels.get(2).draw(gl);
		gl.glTranslatef(2 * (tankBody.getWidthOffset() + tankWheels.get(0).getWidthOffset()), 0, 0);
		tankWheels.get(3).draw(gl);
		gl.glTranslatef(0, 0, -tankBody.getLengthOffset() + 0.4f);
		tankWheels.get(4).draw(gl);
		gl.glTranslatef(0, 0, -(tankBody.getLengthOffset()));
		tankWheels.get(5).draw(gl);

		gl.glPopMatrix();
	}
}
