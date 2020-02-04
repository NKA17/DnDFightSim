package models;

import data.Copiable;
import mechanics.rules.UseRule;

import java.util.ArrayList;
import java.util.List;

public class Cantrip extends Action{

    private Attribute userAttribute;
    private Attribute targetAttribute;
    protected int baseLevel = 0;

    @Override
    public Action copy() {
        Cantrip copy = new Cantrip(getName());
        copy.setModifiers(new ArrayList<>(getModifiers()));
        copy.setSubsequentActions(new ArrayList<>(getSubsequentActions()));
        copy.setCost(getCost());
        copy.setAddModifierToDamage(isAddModifierToDamage());
        copy.setAttempts(new ArrayList<>(getAttempts()));
        copy.setEffects(new ArrayList<>(getEffects()));
        copy.setUserAttribute(getUserAttribute());
        copy.setTargetAttribute(getTargetAttribute());
        List<UseRule> rules = new ArrayList<>();
        for(UseRule rule: getRules()){
            if(rule instanceof Copiable){
                rules.add((UseRule)((Copiable) rule).copy());
            }else {
                rules.add(rule);
            }
        }
        copy.setRules(rules);
        return copy;
    }

    public Cantrip(String name) {
        super(name);
    }

    public int getBaseLevel() {
        return baseLevel;
    }

    public Attribute getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(Attribute userAttribute) {
        this.userAttribute = userAttribute;
    }

    public Attribute getTargetAttribute() {
        return targetAttribute;
    }

    public void setTargetAttribute(Attribute targetAttribute) {
        this.targetAttribute = targetAttribute;
    }
}
