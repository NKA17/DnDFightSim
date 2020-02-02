package mechanics.rules;

import models.Action;
import models.Creature;

public class AvailabilityRule implements UseRule {

    private int uses;
    private int remaining;

    public AvailabilityRule(int uses) {
        this.uses = uses;
        remaining = uses;
    }

    @Override
    public void reset() {
        remaining = uses;
    }

    @Override
    public boolean canUse(Creature self, Creature target, Action action) {
        return remaining > 0;
    }

    @Override
    public void use(Creature self, Creature target, Action action) {
        remaining--;
    }
}
