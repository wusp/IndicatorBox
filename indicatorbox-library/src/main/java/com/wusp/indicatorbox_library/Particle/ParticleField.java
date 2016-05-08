package com.wusp.indicatorbox_library.Particle;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import java.util.ArrayList;

/**
 * Created by wusp on 16/4/1.
 */
public class ParticleField extends FrameLayout {
    private int flagType = -1;
    /**At least we need these variants to form a particle motion, and the variants cannot be a default value.*/
    private ArrayList<Particle> mParticles;
    public ParticleField(Context context) {
        super(context);
    }
    public ParticleField(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public ParticleField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ParticleField(ViewGenerator viewGenerator){
        this(viewGenerator.getContext(), viewGenerator.getAttributeSet());
        //Init particle
        mParticles = new ArrayList<>();
        if (viewGenerator.getParticleInitializers() != null && viewGenerator.getParticleInitializers().size() > 0) {
            for (int i = 0; i < viewGenerator.getParticleInitializers().size(); i++) {
                mParticles.add(new Particle(viewGenerator.getParticleInitializers().get(i)));
            }
        }else{
            //No custom bitmap, just draw a default circle.
            for (int i = 0; i < viewGenerator.particleNumbers; i++){
                mParticles.add(new Particle());
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        synchronized (mParticles){
            for (Particle p : mParticles){
                p.draw(canvas);
            }
        }
    }

    public void onUpdate(float interpolatorValue){
        if (interpolatorValue >= 0 && interpolatorValue <= 1.0f){
            for (Particle particle : mParticles){
                particle.update(interpolatorValue);
            }
            postInvalidate();
        }
    }

    public ArrayList<Particle> getmParticles() {
        return mParticles;
    }

    /**This is used to generator a particle field(View) can be add to a custom view and cooperate with that directly.*/
    public static class ViewGenerator{
        //To indicate which type is.
        private int type;
        //Context
        private Context context;
        //Attributes
        private AttributeSet attributeSet;
        //ParticleInitializer is used to create fundamental particles.
        private ArrayList<ParticleInitializer> particleInitializers;
        private int particleNumbers;

        public ViewGenerator() {
        }

        public ParticleField generate(){
            return new ParticleField(this);
        }

        public Context getContext() {
            return context;
        }

        public ViewGenerator setContext(Context context) {
            this.context = context;
            return this;
        }

        public AttributeSet getAttributeSet() {
            return attributeSet;
        }

        public ViewGenerator setAttributeSet(AttributeSet attributeSet) {
            this.attributeSet = attributeSet;
            return this;
        }

        public int getType() {
            return type;
        }

        public ViewGenerator setType(int type) {
            this.type = type;
            return this;
        }

        public ArrayList<ParticleInitializer> getParticleInitializers() {
            return particleInitializers;
        }

        public ViewGenerator setParticleInitializers(ArrayList<ParticleInitializer> particleInitializers) {
            this.particleInitializers = particleInitializers;
            return this;
        }

        public ViewGenerator setParticleNumbers(int numbers){
            particleNumbers = numbers;
            return this;
        }
    }
}
