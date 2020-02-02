package mechanics.attempts;

import models.Action;
import models.Creature;

import java.util.Random;

public class SpellDcAttempt implements Attempt {
    private static Random random = new Random(100023L);

    @Override
    public boolean attempt(Action action, Creature self, Creature target) {
        //TODO
        return false;
    }
}
