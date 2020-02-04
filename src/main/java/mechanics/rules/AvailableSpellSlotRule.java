package mechanics.rules;

import models.Action;
import models.Creature;
import models.Spell;

public class AvailableSpellSlotRule implements UseRule{
    @Override
    public void reset() {

    }

    @Override
    public boolean canUse(Creature self, Creature target, Action action, boolean parentSuccess) {
        Spell spell = (Spell)action;
        return self.getRemainingSpellSlots().hasSlotsAtLevel(spell.getBaseLevel());
    }

    @Override
    public void use(Creature self, Creature target, Action action) {
        Spell spell = (Spell)action;
        self.getRemainingSpellSlots().useSlot(spell.getBaseLevel());
    }
}
