package mechanics.rules;

import models.Action;
import models.Creature;

public class HealthThresholdRule implements UseRule {

    private double threshold;
    private boolean whenAboveThreshold = true;
    private int minHealth = -1;

    public HealthThresholdRule(int minHealth){
        this.minHealth = minHealth;
    }

    public HealthThresholdRule(int minHealth, boolean whenAboveThreshold){
        this.whenAboveThreshold = whenAboveThreshold;
        this.minHealth = minHealth;
    }

    public HealthThresholdRule(double threshold) {
        this.threshold = threshold;
    }

    public HealthThresholdRule(double threshold, boolean whenAboveThreshold) {
        this.threshold = threshold;
        this.whenAboveThreshold = whenAboveThreshold;
    }

    @Override
    public void reset() {

    }

    @Override
    public boolean canUse(Creature self, Creature target, Action action, boolean parentSuccess) {
        if(minHealth == -1) {
            return whenAboveThreshold == ((double) self.getHp() / (double) self.getMaxHp()) > threshold;
        }else {
            return whenAboveThreshold == self.getHp() > minHealth;
        }
    }

    @Override
    public void use(Creature self, Creature target, Action action) {

    }


}
