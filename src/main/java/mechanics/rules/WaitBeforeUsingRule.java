package mechanics.rules;

import data.Copiable;
import models.Action;
import models.Creature;

public class WaitBeforeUsingRule implements UseRule, Copiable {

    private int waits = 0;
    private int count = 0;

    public WaitBeforeUsingRule(int waits) {
        this.waits = waits;
    }

    @Override
    public void reset() {
        count = 0;
    }

    @Override
    public boolean canUse(Creature self, Creature target, Action action, boolean parentSuccess) {
        boolean use = count > waits;
        count++;
        return use;
    }

    @Override
    public void use(Creature self, Creature target, Action action) {

    }

    @Override
    public Copiable copy() {
        WaitBeforeUsingRule copy = new WaitBeforeUsingRule(waits);
        return copy;
    }
}
