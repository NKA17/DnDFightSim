package mechanics.utils;

import models.Attribute;
import models.Creature;

import java.util.Random;

public class DCUtils {
    private static Random random = new Random(100023L);

    public static int determineDC(int base, Creature user, Attribute modifier){
        int dc = base;

        if(user != null){
            dc += user.getProficiencyBonus();

            if(modifier != null){
                dc += user.getAttributesBlock().getAttributeModifier(modifier);
            }
        }

        return dc;
    }

    public static boolean makeSave(int dc, Creature saveMaker, Attribute modifier){
        int roll = random.nextInt(20) + 1;

        int save = roll + saveMaker.getAttributesBlock().getAttributeModifier(modifier);

        return save >= dc;
    }
}
