package com.wusp.indicatorbox_library.Particle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wusp on 16/4/1.
 */
public class ParticleMotionController {
    //The No. of the particle is.
    private int number;
    private List<ParticleModifier> modifierList = new ArrayList<>();

    public ParticleMotionController(){

    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void addModifier(ParticleModifier modifier){
        if (modifier == null){
            return;
        }
        modifierList.add(modifier);
    }

    public List<ParticleModifier> getModifierList() {
        return modifierList;
    }
}
