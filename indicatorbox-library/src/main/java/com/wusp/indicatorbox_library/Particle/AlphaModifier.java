package com.wusp.indicatorbox_library.Particle;

public class AlphaModifier implements ParticleModifier {
	private int mInitialValue;
	private int mFinalValue;
	private float mValueIncrement;

	public AlphaModifier(int initialValue, int finalValue) {
		mInitialValue = initialValue;
		mFinalValue = finalValue;
		mValueIncrement = mFinalValue - mInitialValue;
	}

	public AlphaModifier() {
	}

	@Override
	public void setState(Particle particle, float interpolatorValue) {
		if (interpolatorValue <= 0) {
			particle.setAlpha(mInitialValue);
		} else if (interpolatorValue * mValueIncrement >= mFinalValue ) {
			particle.setAlpha(mFinalValue);
		} else {
			particle.setAlpha((int) (mInitialValue + mValueIncrement * interpolatorValue));
		}		
	}

	public int getmInitialValue() {
		return mInitialValue;
	}

	public void setmInitialValue(int mInitialValue) {
		this.mInitialValue = mInitialValue;
	}

	public int getmFinalValue() {
		return mFinalValue;
	}

	public void setmFinalValue(int mFinalValue) {
		this.mFinalValue = mFinalValue;
	}

	public float getmValueIncrement() {
		return mValueIncrement;
	}

	public void setmValueIncrement(float mValueIncrement) {
		this.mValueIncrement = mValueIncrement;
	}
}
