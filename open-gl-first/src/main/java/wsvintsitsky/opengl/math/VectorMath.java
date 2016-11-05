package wsvintsitsky.opengl.math;

public class VectorMath {

	private static final VectorMath INSTANCE = new VectorMath();
	
	private VectorMath() {
		
	}
	
	public static VectorMath getInstance() {
		return INSTANCE;
	}
	
	public float getVector2DLength(float pFirst, float pSecond) {
		return (float)Math.sqrt(pFirst*pFirst + pSecond*pSecond);
	}
	
	public void getNormalized2DVector(float[] vector2D) {
		float length = getVector2DLength(vector2D[0], vector2D[1]);
		vector2D[0] /= length;
		vector2D[1] /= length;
	}
	
	public float getVector2DCosX(float pFirst, float pSecond) {
		return pSecond/getVector2DLength(pFirst, pSecond);
	}
	
	void subtractVectors(float[] vFirst, float[] vSecond, float[] vResult) {
		vResult[0] = vFirst[0] - vSecond[0];
		vResult[1] = vFirst[1] - vSecond[1];
		vResult[2] = vFirst[2] - vSecond[2];
	}

	void getVectorCrossProduct(float[] vU, float[] vV, float[] vResult) {
		vResult[0] = vU[1] * vV[2] - vV[1] * vU[2];
		vResult[1] = -vU[0] * vV[2] + vV[0] * vU[2];
		vResult[2] = vU[0] * vV[1] - vV[0] * vU[1];
	}

	public void getNormalVector(float[] vP1, float[] vP2, float[] vP3, float[] vNormal) {
		float[] vV1, vV2;
		vV1 = new float[3];
		vV2 = new float[3];

		subtractVectors(vP2, vP1, vV1);
		subtractVectors(vP3, vP1, vV2);

		getVectorCrossProduct(vV1, vV2, vNormal);
	}

}
