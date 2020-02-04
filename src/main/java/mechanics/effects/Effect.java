package mechanics.effects;

import models.Action;
import models.Creature;

public interface Effect {
    public void perform(Action action, Creature self, Creature target);
}
