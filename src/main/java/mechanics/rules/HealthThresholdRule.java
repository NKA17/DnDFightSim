package mechanics.rules;

import models.Action;
import models.Creature;

public class HealthThresholdRule implements UseRule {

    private double threshold;
    private boolean whenAboveThreshold;

    public HealthThresholdRule(double threshold) {
        this.threshold = threshold;
        this.whenAboveThreshold = false;
    }

    public HealthThresholdRule(double threshold, boolean whenAboveThreshold) {
        this.threshold = threshold;
        this.whenAboveThreshold = whenAboveThreshold;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean canUse(Creature self, Creature target, Action action) {
        return whenAboveThreshold == ((double)self.getHp()/(double)self.getMaxHp()) > threshold;
    }

    @Override
    public void use(Creature self, Creature target, Action action) {

    }


}
