package mechanics.attempts;

import models.Action;
import models.Creature;

public interface Attempt {
    boolean attempt(Action action, Creature self, Creature target);
}
