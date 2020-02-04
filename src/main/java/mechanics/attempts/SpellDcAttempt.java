package mechanics.attempts;

import mechanics.utils.DCUtils;
import models.Action;
import models.Creature;
import models.Cantrip;

public class SpellDcAttempt implements Attempt {

    @Override
    public boolean attempt(Action action, Creature self, Creature target) {
        Cantrip spell = (Cantrip)action;
        int dc = DCUtils.determineDC(8,self,spell.getUserAttribute());
        boolean save = DCUtils.makeSave(dc,target,spell.getTargetAttribute());

        return !save;
    }
}
