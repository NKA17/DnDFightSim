package fightClub;

import mechanics.effects.DamageEffect;
import mechanics.effects.Effect;
import models.Action;
import models.Damage;
import models.Spell;

import java.util.ArrayList;
import java.util.List;

public class ActionUtils {

    public static List<Action> expandSpellToHigherLevels(Spell spell){
        return expandSpellToHigherLevels(spell,9);
    }
    public static List<Action> expandSpellToHigherLevels(Spell spell, int maxLevel){
        List<Action> spells = new ArrayList<>();
        spells.add(spell);
        for(int i = 1; i < maxLevel + 1 - spell.getBaseLevel(); i++){
            Action copy = spellCopyHelper(spell,i);
            spells.add(copy);
        }

        return spells;
    }

    private static Action spellCopyHelper(Action action,int extraPower){
        if(!(action instanceof Spell)){
            return action.copy();
        }

        Spell copy = (Spell)action.copy();
        copy.setBaseLevel(copy.getBaseLevel()+extraPower);
        copy.setName(copy.getName()+String.format(" (Lvl. %d)",copy.getBaseLevel()));
        for(int j = 0; j < copy.getEffects().size(); j++){
            Effect e = copy.getEffects().get(j);
            if(e instanceof DamageEffect){
                DamageEffect de = (DamageEffect) ((DamageEffect)e).copy();
                de.getDamage().setDiceCount(de.getDamage().getDiceCount()+extraPower);
                copy.getEffects().remove(j);
                copy.getEffects().add(j,de);
            }
        }
        return copy;
    }
}
