package mechanics.effects;

import fightClub.FightUtils;
import models.Action;
import models.Attribute;
import models.Creature;
import models.Damage;

public class HealSelfEffect implements Effect {

    private boolean addModifier;
    private Damage damage;

    public HealSelfEffect(Damage damage,boolean addModifier) {
        this.addModifier = addModifier;
        this.damage = damage;
    }

    public HealSelfEffect(Damage damage) {
        this.addModifier = true;
        this.damage = damage;
    }

    @Override
    public void perform(Action action, Creature self, Creature target) {
        int count = damage.getDiceCount();
        int size = damage.getDiceSize();

        int d = count*size;
        if(addModifier){
            Attribute best = FightUtils.getOptimalAttributeForAction(self,action);
            d += self.getAttributesBlock().getAttributeModifier(best);
        }

        self.setHp(self.getHp()+d);

        System.out.println(self.getName()+ " healed for "+d+" HP!");
    }
}
