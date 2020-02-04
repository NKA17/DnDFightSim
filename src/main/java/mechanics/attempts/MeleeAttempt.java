package mechanics.attempts;

import fightClub.FightUtils;
import models.Action;
import models.Attribute;
import models.Creature;

import java.util.Random;

public class MeleeAttempt implements Attempt {
    private static Random random = new Random(100023L);

    @Override
    public boolean attempt(Action action, Creature self, Creature target) {
        Attribute bestAttribute = FightUtils.getOptimalAttributeForAction(self,action);
        int roll = random.nextInt(20)+1;
        int attack = roll + self.getProficiencyBonus() + self.getAttributesBlock().getAttributeModifier(bestAttribute);

        boolean success = attack >= target.getAc();

        if(!success){
            System.out.println(self.getName()+" used "+action.getName()+" on "+target.getName()+" but missed!");
        }

        return success;
    }
}
