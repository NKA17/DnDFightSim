package mechanics.rules;

import models.Action;
import models.Creature;

public interface UseRule {
    void reset();
    boolean canUse(Creature self, Creature target, Action action, boolean parentSuccess);
    void use(Creature self, Creature target, Action action);
}
