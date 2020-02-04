package mechanics.effects;

import data.Copiable;
import fightClub.FightUtils;
import models.Action;
import models.Attribute;
import models.Creature;
import models.Damage;

public class DamageEffect implements Effect, Copiable {
    private boolean addModifier;
    private Damage damage;

    public DamageEffect(Damage damage,boolean addModifier) {
        this.addModifier = addModifier;
        this.damage = damage;
    }


    public DamageEffect(Damage damage) {
        this.addModifier = true;
        this.damage = damage;
    }
    @Override
    public void perform(Action action, Creature self, Creature target) {
        int count = damage.getDiceCount();
        int size = damage.getDiceSize();

        int d = (count*size)/2;
        if(addModifier){
            Attribute best = FightUtils.getOptimalAttributeForAction(self,action);
            d += self.getAttributesBlock().getAttributeModifier(best);
        }

        if(target.getVulnerabilities().contains(damage.getDamageType())){
            d *= 2;
        }else if(target.getResistances().contains(damage.getDamageType())){
            d /= 2;
        }else if(target.getImmunities().contains(damage.getDamageType())){
            d = 0;
        }

        target.setHp(target.getHp()-d);

        System.out.println(self.getName()+" used "+action.getName()+" on "+target.getName()+" for "+d+" damage!");
    }

    public boolean isAddModifier() {
        return addModifier;
    }

    public void setAddModifier(boolean addModifier) {
        this.addModifier = addModifier;
    }

    public Damage getDamage() {
        return damage;
    }

    public void setDamage(Damage damage) {
        this.damage = damage;
    }

    @Override
    public Copiable copy() {
        DamageEffect copy = new DamageEffect((Damage)damage.copy());
        copy.setAddModifier(addModifier);
        return copy;
    }
}
