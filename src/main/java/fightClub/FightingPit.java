package fightClub;

import models.*;
import models.misc.DnD5eDamage;

import java.util.List;

public class FightingPit {

    private Creature c1;
    private Creature c2;

    public FightingPit(Creature c1, Creature c2) {
        this.c1 = c1;
        this.c2 = c2;
    }


    public double getC1ChanceToHit(Action action){
        return FightUtils.getChanceToHit(action,c1,c2);
    }

    public double getC2ChanceToHit(Action action){
        return FightUtils.getChanceToHit(action,c2,c1);
    }


    public Action getC1BestAction(){
        return FightUtils.getHighestDamageAction(c1,c2);
    }

    public Action getC2BestAction(){
        return FightUtils.getHighestDamageAction(c2,c1);
    }

    public int getC1AverageDPT(){
        return FightUtils.getAvePotentialDamage(FightUtils.getHighestDamageAction(c1,c2),c2);
    }

    public int getC2AverageDPT(){
        return FightUtils.getAvePotentialDamage(FightUtils.getHighestDamageAction(c2,c1),c1);
    }

    public Creature getC1() {
        return c1;
    }

    public void setC1(Creature c1) {
        this.c1 = c1;
    }

    public Creature getC2() {
        return c2;
    }

    public void setC2(Creature c2) {
        this.c2 = c2;
    }
}
