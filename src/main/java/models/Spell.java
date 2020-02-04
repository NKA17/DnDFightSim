package models;

import data.Copiable;
import mechanics.rules.UseRule;

import java.util.ArrayList;
import java.util.List;

public class Spell extends Cantrip {
    public Spell(String name) {
        super(name);
    }

    public void setBaseLevel(int level){
        this.baseLevel = level;
    }

    public Action copy(){
        Spell copy = new Spell(getName());
        copy.setModifiers(new ArrayList<>(getModifiers()));
        copy.setSubsequentActions(new ArrayList<>(getSubsequentActions()));
        copy.setCost(getCost());
        copy.setAddModifierToDamage(isAddModifierToDamage());
        copy.setAttempts(new ArrayList<>(getAttempts()));
        copy.setEffects(new ArrayList<>(getEffects()));
        copy.setUserAttribute(getUserAttribute());
        copy.setTargetAttribute(getTargetAttribute());
        copy.setBaseLevel(getBaseLevel());
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

    public String toString(){
        String s = super.toString();
        return s + " (Lvl"+baseLevel+")";
    }
}
