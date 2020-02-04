package fightClub;

import mechanics.effects.DamageEffect;
import mechanics.effects.Effect;
import models.*;

import java.util.List;

public class FightUtils {

    public static double getChanceToHit(Action action, Creature creature, Creature target){
        Attribute optimalAttribute = Attribute.NA;
        for(Attribute att: action.getModifiers()){
            if(optimalAttribute == Attribute.NA ||
                    creature.getAttributesBlock().getAttributeModifier(att)
                            >creature.getAttributesBlock().getAttributeModifier(optimalAttribute)){
                optimalAttribute = att;
            }
        }

        int bonus = creature.getProficiencyBonus() + creature.getAttributesBlock().getAttributeModifier(optimalAttribute);

        double effectiveAC = target.getAc() - bonus;
        double chanceToHit = (20.0 - effectiveAC) / 20.0;

        return Math.max(chanceToHit, 0);
    }

    public static Attribute getOptimalAttributeForAction(Creature user, Action action){
        Attribute optimalAttribute = Attribute.NA;
        for(Attribute att: action.getModifiers()){
            if(optimalAttribute == Attribute.NA ||
                    user.getAttributesBlock().getAttributeModifier(att)
                            >user.getAttributesBlock().getAttributeModifier(optimalAttribute)){
                optimalAttribute = att;
            }
        }
        return optimalAttribute;
    }

    public static Action getHighestDamageAction(List<Action> actions, Creature target){
        Action highest = null;
        int highestAve = 0;
        for(Action action: actions){
            if(highest == null){
                highest = action;
                highestAve = getAvePotentialDamage(action, target.getVulnerabilities(),
                        target.getResistances(),target.getImmunities());
            }else{
                int damage = getAvePotentialDamage(action, target.getVulnerabilities(),
                        target.getResistances(),target.getImmunities());

                if(damage > highestAve){
                    highestAve = damage;
                    highest = action;
                }
            }
        }

        return highest;
    }

    public static Action getHighestDamageAction(Creature creature, Creature target){
        return getHighestDamageAction(creature.getActions(),target);
    }


    public static int getAvePotentialDamage(Action action, Creature target){
        return getAvePotentialDamage(action, target.getVulnerabilities(),
                target.getResistances(),target.getImmunities());
    }

    public static int getAvePotentialDamage(Action action, Creature attacker, Creature target){
        int damage =  getAvePotentialDamage(action, target.getVulnerabilities(),
                target.getResistances(),target.getImmunities());
        Attribute use = getOptimalAttributeForAction(attacker,action);

        if(use==Attribute.NA || !action.isAddModifierToDamage()){
            return damage;
        }
        return damage + attacker.getAttributesBlock().getAttributeModifier(use);
    }

    public static int getAvePotentialDamage(Action action,
                                      List<DamageType> vulnerabilities,
                                      List<DamageType> resistances,
                                      List<DamageType> immunities){
        int total = 0;

        for(Effect e: action.getEffects()){
            if(e instanceof DamageEffect){
                Damage d = ((DamageEffect)e).getDamage();
                int damage = d.average();
                if(vulnerabilities.contains(d.getDamageType())){
                    damage *= 2;
                }else if(resistances.contains(d.getDamageType())){
                    damage /= 2;
                }else if(immunities.contains(d.getDamageType())){
                    damage = 0;
                }
                total += damage;
            }
        }

        return total;
    }
}
