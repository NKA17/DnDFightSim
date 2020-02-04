package mechanics.rules;

import models.Action;
import models.Creature;

public class ChainedOnSuccessRule implements UseRule {

    private boolean triggerOnSuccess = true;

    public ChainedOnSuccessRule(){}

    public ChainedOnSuccessRule(boolean triggerOnSuccess) {
        this.triggerOnSuccess = triggerOnSuccess;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean canUse(Creature self, Creature target, Action action, boolean parentSuccess) {
        return parentSuccess == triggerOnSuccess;
    }

    @Override
    public void use(Creature self, Creature target, Action action) {

    }
}
