package com.wusp.indicatorbox_library.Particle;

public class ScaleModifier implements ParticleModifier {
	private float mInitialValue;
	private float mFinalValue;
	private float mValueIncrement;

	public ScaleModifier (float initialValue, float finalValue) {
		mInitialValue = initialValue;
		mFinalValue = finalValue;
		mValueIncrement = mFinalValue - mInitialValue;
	}

	public ScaleModifier() {
	}

	@Override
	public void setState(Particle particle, float interpolatorValue) {
		if (interpolatorValue <= 0) {
			particle.setScale(mInitialValue);
		} else if (interpolatorValue * mValueIncrement >= mFinalValue) {
			particle.setScale(mFinalValue);
		} else {
			particle.setScale(mInitialValue + mValueIncrement * interpolatorValue);
		}
	}

	public float getmInitialValue() {
		return mInitialValue;
	}

	public void setmInitialValue(float mInitialValue) {
		this.mInitialValue = mInitialValue;
	}

	public float getmFinalValue() {
		return mFinalValue;
	}

	public void setmFinalValue(float mFinalValue) {
		this.mFinalValue = mFinalValue;
	}

	public float getmValueIncrement() {
		return mValueIncrement;
	}

	public void setmValueIncrement(float mValueIncrement) {
		this.mValueIncrement = mValueIncrement;
	}
}
