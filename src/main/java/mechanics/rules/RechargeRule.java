package mechanics.rules;

import models.Action;
import models.Creature;

import java.util.Random;

public class RechargeRule implements UseRule {

    private int[] rechargeOn;
    private boolean charged = true;
    private static Random random = new Random(100023L);

    public RechargeRule(int... rechargeOn) {
        this.rechargeOn = rechargeOn;
    }

    @Override
    public void reset() {
        charged = true;
    }

    @Override
    public boolean canUse(Creature self, Creature target, Action action, boolean parentSuccess) {
        boolean c = charged;

        int rolled = random.nextInt(6)+1;
        for(int i : rechargeOn){
            if(i==rolled && !charged) {
                charged = true;
                System.out.println(self.getName()+" recharged "+action.getName());
            }
        }

        return c;
    }

    @Override
    public void use(Creature self, Creature target, Action action) {
        charged = false;
    }
}
