package models;


import mechanics.rules.UseRule;

import java.util.ArrayList;
import java.util.List;

public class Action {
    private String name;
    private List<Damage> damage = new ArrayList<>();
    private List<Damage> heal = new ArrayList<>();
    private List<Attribute> modifiers = new ArrayList<>();
    private List<Action> subsequentActions = new ArrayList<>();
    private int cost = 1;
    private boolean addModifierToDamage = true;
    private List<UseRule> rules = new ArrayList<>();

    public boolean canUse(Creature user, Creature target){
        boolean canUse = true;
        for(UseRule rule: rules){
            canUse &= rule.canUse(user,target,this);
        }
        return canUse;
    }

    public void use(Creature user, Creature target){
        for(UseRule rule: rules){
            rule.use(user,target,this);
        }
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

    public List<Damage> getDamage() {
        return damage;
    }

    public void setDamage(List<Damage> damage) {
        this.damage = damage;
    }

    public List<Damage> getHeal() {
        return heal;
    }

    public void setHeal(List<Damage> heal) {
        this.heal = heal;
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
}
