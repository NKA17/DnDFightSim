package hardcodeSetups;

import fightClub.FightSimulation;
import fightClub.Report;
import fightClub.Scene;
import fightClub.Team;
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
        RunMe.show(reports);
    }

    public static Creature PixieSwarm(){
        Creature c = new Creature("PixieSwarm");
        c.setHp(40);
        c.setAc(18);
        c.setAttributesBlock(new AttributesBlock(12,16,12,10,16,16));

        Action stinger = new Action("Stinger");
        stinger.getDamage().add(new Damage("4d4 piercing"));
        stinger.getModifiers().add(Attribute.DEXTERITY);

        c.getActions().add(stinger);

        return c;
    }
}
