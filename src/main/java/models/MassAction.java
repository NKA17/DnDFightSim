package models;

import mechanics.attempts.Attempt;
import mechanics.effects.Effect;

public class MassAction extends Action {


    public MassAction(String name) {
        super(name);
    }


    public boolean use(Creature user, Creature target){

        boolean success = true;
        for(Attempt attempt: getAttempts()){
            success &= attempt.attempt(this,user,target);
        }

        if(success){
            for(Effect effect: getEffects()){
                effect.perform(this,user,target);
            }
        }

        return success;
    }
}
