package hardcodeSetups;

import fightClub.FightSimulation;
import fightClub.Report;
import fightClub.Scene;
import fightClub.Team;
import mechanics.attempts.Attempt;
import mechanics.attempts.MeleeAttempt;
import mechanics.effects.DamageEffect;
import mechanics.rules.HealthThresholdRule;
import models.*;

import java.util.ArrayList;

public class SnapDragon {
    public static void main(String[] args){
        Scene scene = new Scene();
        scene.setName("Party v SnapDragon");

        Team t2 = new Team("SnapDragon");
        scene.getTeams().add(RunMe.Party());
        scene.getTeams().add(t2);

        t2.getCreatures().add(SnapDragon());

        FightSimulation sim = new FightSimulation(scene);
        Report[] reports = sim.simulateMany(30);
        RunMe.show(reports, "Average");
    }

    public static Creature SnapDragon(){
        Creature creature = new Creature("SnapDragon");
        creature.setMaxHp(195);
        creature.setAc(18);
        creature.setAttributesBlock(new AttributesBlock(23,14,21,5,10,6));
        creature.getImmunities().add(DamageType.POISON);
        creature.getImmunities().add(DamageType.ACID);

        Action acid = new Action("Acid Spit 1");
        acid.getRules().add(new HealthThresholdRule(0));
        acid.getEffects().add(new DamageEffect(new Damage("2d6 Acid")));
        acid.getModifiers().add(Attribute.STRENGTH);
        acid.getAttempts().add(new MeleeAttempt());

        Action acid2 = acid.copy();
        acid2.setName("Acid Spit 2");
        acid2.setRules(new ArrayList<>());
        acid2.getRules().add(new HealthThresholdRule(40));

        Action acid3 = acid.copy();
        acid3.setName("Acid Spit 3");
        acid3.setRules(new ArrayList<>());
        acid3.getRules().add(new HealthThresholdRule(80));

        Action acid4 = acid.copy();
        acid4.setName("Acid Spit 4");
        acid4.setRules(new ArrayList<>());
        acid4.getRules().add(new HealthThresholdRule(120));

        Action acid5 = acid.copy();
        acid5.setName("Acid Spit 5");
        acid5.setRules(new ArrayList<>());
        acid5.getRules().add(new HealthThresholdRule(160));

        Action tail = new Action("Tail");
        tail.getEffects().add(new DamageEffect(new Damage("2d8 Bludgeoning")));
        tail.getModifiers().add(Attribute.STRENGTH);
        tail.getAttempts().add(new MeleeAttempt());

        creature.getActions().add(acid);
        acid.getSubsequentActions().add(acid2);
        acid.getSubsequentActions().add(acid3);
        acid.getSubsequentActions().add(acid4);
        acid.getSubsequentActions().add(acid5);
        acid.getSubsequentActions().add(tail);

        return creature;
    }
}
