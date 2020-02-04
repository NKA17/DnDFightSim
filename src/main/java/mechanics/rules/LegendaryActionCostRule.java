package mechanics.rules;

import models.Action;
import models.Creature;

public class LegendaryActionCostRule implements UseRule {
    @Override
    public void reset() {

    }

    @Override
    public boolean canUse(Creature self, Creature target, Action action, boolean parentSuccess) {
        return self.getLegendaryActionPointPool() >= action.getCost();
    }

    @Override
    public void use(Creature self, Creature target, Action action) {
        self.setLegendaryActionPointPool(self.getLegendaryActionPointPool()-action.getCost());
    }
}
