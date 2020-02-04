package hardcodeSetups;

import fightClub.FightSimulation;
import fightClub.Report;
import fightClub.Scene;
import fightClub.Team;
import mechanics.attempts.MeleeAttempt;
import mechanics.effects.DamageEffect;
import mechanics.rules.HealthThresholdRule;
import models.*;

public class PixieSwarm {
    public static void main(String[] args){
        Scene scene = new Scene();
        scene.setName("Party v Pixies");

        Team t2 = new Team("Pixies");
        scene.getTeams().add(RunMe.Party());
        scene.getTeams().add(t2);

        t2.getCreatures().add(PixieSwarm());
        t2.getCreatures().add(PixieSwarm());
        t2.getCreatures().add(PixieSwarm());
        t2.getCreatures().add(PixieSwarm());

        FightSimulation sim = new FightSimulation(scene);
        Report[] reports = sim.simulateMany(30);
        RunMe.show(reports, "Average");
    }

    static int pixies = 1;
    public static Creature PixieSwarm(){
        Creature c = new Creature("PixieSwarm "+(pixies++));

        c.setMaxHp(40);
        c.setAc(17);
        c.setAttributesBlock(new AttributesBlock(12,16,12,10,16,16));

        Action stinger = new Action("Stinger 1");
        stinger.getRules().add(new HealthThresholdRule(.5,true));
        stinger.getAttempts().add(new MeleeAttempt());
        stinger.getEffects().add(new DamageEffect(new Damage("4d4 piercing")));
        stinger.getModifiers().add(Attribute.DEXTERITY);

        Action stinger2 = new Action("Stinger 2");
        stinger2.getAttempts().add(new MeleeAttempt());
        stinger2.getEffects().add(new DamageEffect(new Damage("2d4 piercing")));
        stinger2.getModifiers().add(Attribute.DEXTERITY);

        c.getActions().add(stinger);
        c.getActions().add(stinger2);

        return c;
    }
}
