package hardcodeSetups;

import data.GraphData;
import data.GraphLine;
import data.ReportNormalizer;
import fightClub.*;
import models.*;
import models.Action;
import models.misc.DnD5eDamage;
import ui.GraphPanel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class RunMe {
    public static void main(String[] args){

        System.out.println(DamageType.SET("Slashing"));
        Creature luca = Luca();

        Team teamLuca = new Team("Team Luca");
        teamLuca.getCreatures().add(luca);

        Team party = new Team("Party");
        party.getCreatures().add(Garet());
        party.getCreatures().add(HotRod());
        party.getCreatures().add(Bilrick());
        party.getCreatures().add(Abi());

        Scene scene = new Scene();
        scene.getTeams().add(teamLuca);
        scene.getTeams().add(party);

        FightClub club = new FightClub(scene);
        //club.fight();

        List<HashMap<Object,List<Point>>> simulations = new ArrayList<>();
        FightSimulation sim = new FightSimulation(scene);
        Report[] reports = sim.simulateMany(30);
        for(Report r: reports){
            simulations.add(ReportNormalizer.hpReportToRawData(r));
        }
        GraphData gd = new GraphData(ReportNormalizer.averageNormalizedMaps(simulations));

        GraphPanel gp = new GraphPanel(gd);

        JFrame jf = new JFrame();
        jf.setTitle("Average");
        jf.getContentPane().add(gp);
        jf.setPreferredSize(new Dimension(500,300));
        jf.setSize(500,300);
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        EventQueue.invokeLater(() -> {
            jf.setVisible(true);
        });
    }

    public static Team Party(){
        Team t = new Team("Party");
        t.getCreatures().add(Garet());
        t.getCreatures().add(Abi());
        t.getCreatures().add(HotRod());
        t.getCreatures().add(Bilrick());
        return t;
    }

    public static void show(Report[] reports){
        List<HashMap<Object,List<Point>>> simulations = new ArrayList<>();
        for(Report r: reports){
            simulations.add(ReportNormalizer.hpReportToRawData(r));
        }
        GraphData gd = new GraphData(ReportNormalizer.averageNormalizedMaps(simulations));

        GraphPanel gp = new GraphPanel(gd);

        JFrame jf = new JFrame();
        jf.setTitle("Average");
        jf.getContentPane().add(gp);
        jf.setPreferredSize(new Dimension(500,300));
        jf.setSize(500,300);
        jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        EventQueue.invokeLater(() -> {
            jf.setVisible(true);
        });
    }

    public static Creature Luca(){
        Creature luca = new Creature("Luca");
        luca.setAc(18);
        luca.setMaxHp(115);
        luca.setAttributesBlock(new AttributesBlock(20,0,0,0,0,0));
        luca.setProficiencyBonus(4);

        luca.getResistances().add(DamageType.SLASHING);
        luca.getResistances().add(DamageType.PIERCING);
        luca.getResistances().add(DamageType.BLUDGEONING);
        luca.getImmunities().add(DamageType.COLD);
        luca.getImmunities().add(DamageType.NECROTIC);

        Damage clawDamageSlash = new Damage();
        clawDamageSlash.setDamageDice("1d10");
        clawDamageSlash.setDamageType(DamageType.SLASHING);
        Damage clawDamageCold = new Damage();
        clawDamageCold.setDamageDice("1d10");
        clawDamageCold.setDamageType(DamageType.COLD);
        Action claw = new Action("Claw");
        claw.getModifiers().add(Attribute.STRENGTH);
        //claw.getDamage().add(clawDamageCold);
        claw.getDamage().add(clawDamageSlash);

        Damage biteDamage = new Damage();
        biteDamage.setDamage("2d10");
        biteDamage.setDamageType(DamageType.PIERCING);
        Action bite = new Action("Bite");
        bite.getDamage().add(biteDamage);
        bite.getDamage().add(new Damage("1d10 cold"));
        bite.getHeal().add(new Damage("1d10"));
        bite.getModifiers().add(Attribute.STRENGTH);
        bite.setCost(2);
        claw.setCost(1);

        Action multiAttack = new Action("Multi-Attack");
        multiAttack.getSubsequentActions().add(bite);
        multiAttack.getSubsequentActions().add(claw);
        multiAttack.getSubsequentActions().add(claw);

        luca.setLegendaryActionPoints(3);
        luca.getLegendaryActions().add(bite);
        luca.getLegendaryActions().add(claw);

        luca.getVulnerabilities().add(DamageType.RADIANT);

        luca.getActions().add(multiAttack);

        return luca;
    }
    public static Creature Abi(){
        Creature garet = new Creature("Abi");
        garet.setMaxHp(105);
        garet.setAc(18);
        garet.setAttributesBlock(new AttributesBlock(17,13,16,0,0,0));
        garet.setProficiencyBonus(4);

        Action sword = new Action("Sword");
        sword.getDamage().add(new Damage("2d6 slashing"));
        sword.getDamage().add(new Damage("2d6 slashing"));
        sword.getModifiers().add(Attribute.STRENGTH);

        garet.getActions().add(sword);

        return garet;
    }

    public static Creature Garet(){
        Creature garet = new Creature("Garet");
        garet.setMaxHp(75);
        garet.setAc(17);
        garet.setAttributesBlock(new AttributesBlock(0,20,14,0,0,0));
        garet.setProficiencyBonus(4);

        Action sword = new Action("Sword");
        sword.getDamage().add(new Damage("1d6 slashing"));
        sword.getDamage().add(new Damage("1d6 slashing"));
        sword.getDamage().add(new Damage("1d6 slashing"));
        sword.getModifiers().add(Attribute.DEXTERITY);

        garet.getActions().add(sword);

        return garet;
    }

    public static Creature HotRod(){
        Creature garet = new Creature("HotRod");
        garet.setMaxHp(107);
        garet.setAc(14);
        garet.setAttributesBlock(new AttributesBlock(20,12,16,0,0,0));
        garet.setProficiencyBonus(4);

        Action axe = new Action("Axe");
        axe.getDamage().add(new Damage("1d12 slashing"));
        axe.getDamage().add(new Damage("1d12 slashing"));
        axe.getModifiers().add(Attribute.STRENGTH);

        garet.getActions().add(axe);
        garet.getResistances().add(DamageType.SLASHING);
        garet.getResistances().add(DamageType.COLD);
        garet.getResistances().add(DamageType.PIERCING);
        garet.getResistances().add(DamageType.BLUDGEONING);
        garet.getResistances().add(DamageType.POISON);
        garet.getResistances().add(DamageType.NECROTIC);

        return garet;
    }
    public static Creature Bilrick(){
        Creature garet = new Creature("Bilrick");
        garet.setMaxHp(68);
        garet.setAc(15);
        garet.setAttributesBlock(new AttributesBlock(8,14,15,20,0,0));
        garet.setProficiencyBonus(4);

        Action axe = new Action("Fireball");
        axe.getDamage().add(new Damage("6d6 fire"));
        axe.setAddModifierToDamage(false);
        axe.getModifiers().add(Attribute.INTELLIGENCE);

        garet.getActions().add(axe);

        return garet;
    }
}
