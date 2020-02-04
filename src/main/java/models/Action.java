package models;


import com.sun.deploy.security.ruleset.Rule;
import data.Copiable;
import mechanics.attempts.Attempt;
import mechanics.effects.Effect;
import mechanics.rules.UseRule;

import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private List<Attribute> modifiers = new ArrayList<>();
    private List<Action> subsequentActions = new ArrayList<>();
    private int cost = 1;
    private boolean addModifierToDamage = true;
    private List<UseRule> rules = new ArrayList<>();
    private List<Attempt> attempts = new ArrayList<>();
    private List<Effect> effects = new ArrayList<>();

    public Action copy(){
        Action copy = new Action(getName());
        copy.setModifiers(new ArrayList<>(getModifiers()));
        copy.setSubsequentActions(new ArrayList<>(getSubsequentActions()));
        copy.setCost(getCost());
        copy.setAddModifierToDamage(isAddModifierToDamage());
        copy.setAttempts(new ArrayList<>(getAttempts()));
        copy.setEffects(new ArrayList<>(getEffects()));
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

    public boolean canUse(Creature user, Creature target){
        return canUse(user,target,true);
    }
    public boolean canUse(Creature user, Creature target, boolean parentSuccess){
        boolean canUse = true;
        for(UseRule rule: rules){
            canUse &= rule.canUse(user,target,this, parentSuccess);
        }
        return canUse;
    }

    public boolean use(Creature user, Creature target){
        for(UseRule rule: rules){
            rule.use(user,target,this);
        }

        boolean success = true;
        for(Attempt attempt: attempts){
            success &= attempt.attempt(this,user,target);
        }

        if(success){
            for(Effect effect: effects){
                effect.perform(this,user,target);
            }
        }

        for(Action action: subsequentActions){
            if(action.canUse(user,target,success)){
                success &= action.use(user,target);
            }
        }

        return success;
    }

    public void resetRules(){
        for(UseRule rule: rules){
            rule.reset();
        }
    }


    public boolean isAddModifierToDamage() {
        return addModifierToDamage;
    }

    public void setAddModifierToDamage(boolean addModifierToDamage) {
        this.addModifierToDamage = addModifierToDamage;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Action> getSubsequentActions() {
        return subsequentActions;
    }

    public void setSubsequentActions(List<Action> subsequentActions) {
        this.subsequentActions = subsequentActions;
    }

    public List<Attribute> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<Attribute> modifiers) {
        this.modifiers = modifiers;
    }

    public Action(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UseRule> getRules() {
        return rules;
    }

    public void setRules(List<UseRule> rules) {
        this.rules = rules;
    }

    public List<Attempt> getAttempts() {
        return attempts;
    }

    public void setAttempts(List<Attempt> attempts) {
        this.attempts = attempts;
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public void setEffects(List<Effect> effects) {
        this.effects = effects;
    }

    public String toString(){
        return getName();
    }
}
