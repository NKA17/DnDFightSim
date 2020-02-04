package hardcodeSetups;

import data.GraphData;
import data.ReportNormalizer;
import fightClub.*;
import mechanics.attempts.MeleeAttempt;
import mechanics.attempts.SpellDcAttempt;
import mechanics.effects.DamageEffect;
import mechanics.effects.HealSelfEffect;
import mechanics.rules.*;
import models.*;
import models.Action;
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
        Report[] reports = sim.simulateMany(50);
        for(Report r: reports){
            simulations.add(ReportNormalizer.hpReportToRawData(r));
            //show(new Report[]{r}, r.getReportName());
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

    public static void show(Report[] reports, String name){
        List<HashMap<Object,List<Point>>> simulations = new ArrayList<>();
        for(Report r: reports){
            simulations.add(ReportNormalizer.hpReportToRawData(r));
        }
        GraphData gd = new GraphData(ReportNormalizer.averageNormalizedMaps(simulations));

        GraphPanel gp = new GraphPanel(gd);

        JFrame jf = new JFrame();
        jf.setTitle(name);
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
        luca.setMaxHp(140);
        luca.setAttributesBlock(new AttributesBlock(20,16,22,14,16,4));
        luca.setProficiencyBonus(5);
        luca.setLegendaryActionPoints(2);

        luca.getResistances().add(DamageType.SLASHING);
        luca.getResistances().add(DamageType.PIERCING);
        luca.getResistances().add(DamageType.BLUDGEONING);
        luca.getImmunities().add(DamageType.COLD);
        luca.getImmunities().add(DamageType.NECROTIC);
        luca.getVulnerabilities().add(DamageType.RADIANT);

        Action charge = new Action("Antler Charge");
        charge.getRules().add(new HealthThresholdRule(.5,false));
        charge.getRules().add(new RechargeRule(5,6));
        charge.getRules().add(new AvailabilityRule(3));
        charge.getAttempts().add(new MeleeAttempt());
        charge.getEffects().add(new DamageEffect(new Damage("6d10 slashing")));

        Action bite = new Action("Bite");
        bite.getAttempts().add(new MeleeAttempt());
        bite.getModifiers().add(Attribute.STRENGTH);
        bite.getRules().add(new HealthThresholdRule(.75,false));
        bite.getEffects().add(new DamageEffect(new Damage("2d10 piercing")));

        Action healFromBite = new Action("Leech");
        healFromBite.getRules().add(new ChainedOnSuccessRule());
        healFromBite.getEffects().add(new HealSelfEffect(new Damage("1d15"),false));
        bite.getSubsequentActions().add(healFromBite);

        Action antler = new Action("Antler");
        antler.getAttempts().add(new MeleeAttempt());
        antler.getModifiers().add(Attribute.STRENGTH);
        antler.getEffects().add(new DamageEffect(new Damage("1d10 slashing")));

        Action claw = new Action("Claw");
        claw.getAttempts().add(new MeleeAttempt());
        claw.getModifiers().add(Attribute.STRENGTH);
        claw.getEffects().add(new DamageEffect(new Damage("1d10 slashing")));

        bite.getSubsequentActions().add(claw);
        bite.getSubsequentActions().add(claw);

        antler.getSubsequentActions().add(claw);
        antler.getSubsequentActions().add(claw);

        luca.getActions().add(bite);
        luca.getActions().add(antler);
        luca.getActions().add(charge);

        //Legendary
        Action biteL = new Action("Bite (Legendary Action)");
        biteL.setCost(2);
        biteL.getAttempts().add(new MeleeAttempt());
        biteL.getModifiers().add(Attribute.STRENGTH);
        biteL.getRules().add(new HealthThresholdRule(.75,false));
        biteL.getRules().add(new LegendaryActionCostRule());
        biteL.getEffects().add(new DamageEffect(new Damage("2d10 piercing")));
        biteL.getSubsequentActions().add(healFromBite);

        Action clawL = new Action("Claw (Legendary Action)");
        clawL.setCost(1);
        clawL.getRules().add(new LegendaryActionCostRule());
        clawL.getAttempts().add(new MeleeAttempt());
        clawL.getModifiers().add(Attribute.STRENGTH);
        clawL.getEffects().add(new DamageEffect(new Damage("1d10 slashing")));

        Action antlerL = new Action("Antler (Legendary Action)");
        antlerL.setCost(1);
        antlerL.getRules().add(new LegendaryActionCostRule());
        antlerL.getAttempts().add(new MeleeAttempt());
        antlerL.getModifiers().add(Attribute.STRENGTH);
        antlerL.getEffects().add(new DamageEffect(new Damage("1d10 slashing")));

        luca.getLegendaryActions().add(biteL);
        luca.getLegendaryActions().add(clawL);
        luca.getLegendaryActions().add(antlerL);

        return luca;
    }
    public static Creature Abi(){
        Creature garet = new Creature("Abi");
        garet.setMaxHp(105);
        garet.setAc(18);
        garet.setAttributesBlock(new AttributesBlock(17,13,16,0,0,0));
        garet.setProficiencyBonus(4);

        Action sword = new Action("Sword");
        sword.getAttempts().add(new MeleeAttempt());
        sword.getEffects().add(new DamageEffect(new Damage("2d6 slashing")));
        sword.getModifiers().add(Attribute.STRENGTH);
        Action sword2 = new Action("Sword");
        sword2.getAttempts().add(new MeleeAttempt());
        sword2.getEffects().add(new DamageEffect(new Damage("2d6 slashing")));
        sword2.getModifiers().add(Attribute.STRENGTH);
        sword.getSubsequentActions().add(sword2);

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
        sword.getAttempts().add(new MeleeAttempt());
        sword.getEffects().add(new DamageEffect(new Damage("1d6 piercing")));
        sword.getModifiers().add(Attribute.DEXTERITY);
        Action sword2 = new Action("Sword");
        sword2.getAttempts().add(new MeleeAttempt());
        sword2.getEffects().add(new DamageEffect(new Damage("1d6 piercing")));
        sword2.getModifiers().add(Attribute.DEXTERITY);
        Action sword3 = new Action("Sword");
        sword3.getAttempts().add(new MeleeAttempt());
        sword3.getEffects().add(new DamageEffect(new Damage("1d6 piercing")));
        sword3.setAddModifierToDamage(false);
        sword3.getModifiers().add(Attribute.DEXTERITY);
        sword.getSubsequentActions().add(sword2);
        sword.getSubsequentActions().add(sword3);

        garet.getActions().add(sword);

        return garet;
    }

    public static Creature HotRod(){
        Creature garet = new Creature("HotRod");
        garet.setMaxHp(107);
        garet.setAc(14);
        garet.setAttributesBlock(new AttributesBlock(20,12,16,0,0,0));
        garet.setProficiencyBonus(4);

        Action one = new Action("Axe");
        one.getAttempts().add(new MeleeAttempt());
        one.getModifiers().add(Attribute.STRENGTH);
        one.getEffects().add(new DamageEffect(new Damage("1d12 slashing")));
        Action two = new Action("Axe");
        two.getAttempts().add(new MeleeAttempt());
        two.getEffects().add(new DamageEffect(new Damage("1d12 slashing")));
        two.getModifiers().add(Attribute.STRENGTH);
        one.getSubsequentActions().add(two);

        garet.getActions().add(one);
        garet.getResistances().add(DamageType.SLASHING);
        garet.getResistances().add(DamageType.COLD);
        garet.getResistances().add(DamageType.PIERCING);
        garet.getResistances().add(DamageType.BLUDGEONING);
        garet.getResistances().add(DamageType.POISON);
        garet.getResistances().add(DamageType.NECROTIC);

        return garet;
    }
    public static Creature Bilrick(){
        Creature bilrick = new Creature("Bilrick");
        bilrick.setMaxHp(68);
        bilrick.setAc(15);
        bilrick.setAttributesBlock(new AttributesBlock(8,14,15,20,0,0));
        bilrick.setProficiencyBonus(4);

        Spell axe = new Spell("Fireball");
        axe.setBaseLevel(3);
        DamageEffect de1 = new DamageEffect(new Damage("5d6 fire"));
        de1.setAddModifier(false);
        axe.setUserAttribute(Attribute.INTELLIGENCE);
        axe.setTargetAttribute(Attribute.DEXTERITY);
        axe.getRules().add(new AvailableSpellSlotRule());
        axe.getEffects().add(de1);
        axe.getAttempts().add(new SpellDcAttempt());
        axe.setAddModifierToDamage(false);
        axe.getModifiers().add(Attribute.INTELLIGENCE);

        Action fireballHalved = new Action("Fireball Halved");
        DamageEffect de2 = new DamageEffect(new Damage("1d15 fire"));
        de2.setAddModifier(false);
        fireballHalved.getEffects().add(de2);
        fireballHalved.getRules().add(new ChainedOnSuccessRule(false));
        axe.getSubsequentActions().add(fireballHalved);

        Spell chromaticOrb = new Spell("Chromatic Orb (Fire)");
        chromaticOrb.setBaseLevel(1);
        chromaticOrb.getRules().add(new AvailableSpellSlotRule());
        chromaticOrb.getAttempts().add(new MeleeAttempt());
        chromaticOrb.getModifiers().add(Attribute.INTELLIGENCE);
        chromaticOrb.setAddModifierToDamage(false);
        chromaticOrb.getEffects().add(new DamageEffect(new Damage("3d8 Fire")));

        Spell coCold = (Spell)chromaticOrb.copy();
        coCold.setName("Chromatic Orb (Cold)");
        coCold.setEffects(new ArrayList<>());
        coCold.getEffects().add(new DamageEffect(new Damage("3d8 Cold")));

        bilrick.getActions().addAll(ActionUtils.expandSpellToHigherLevels(coCold,4));
        bilrick.getActions().addAll(ActionUtils.expandSpellToHigherLevels(chromaticOrb,4));

//        Spell co2 = (Spell)chromaticOrb.copy();
//        co2.setBaseLevel(2);
//        co2.setName("Chromatic Orb Lvl. 2 (Radiant)");
//        co2.setEffects(new ArrayList<>());
//        co2.getEffects().add(new DamageEffect(new Damage("4d8 Radiant")));
//
//        Spell co3 = (Spell)chromaticOrb.copy();
//        co3.setBaseLevel(3);
//        co3.setName("Chromatic Orb Lvl. 3 (Radiant)");
//        co3.setEffects(new ArrayList<>());
//        co3.getEffects().add(new DamageEffect(new Damage("5d8 Radiant")));
//
//        Spell co4 = (Spell)chromaticOrb.copy();
//        co4.setBaseLevel(4);
//        co4.setName("Chromatic Orb Lvl. 4 (Radiant)");
//        co4.setEffects(new ArrayList<>());
//        co4.getEffects().add(new DamageEffect(new Damage("6d8 Radiant")));
//
//        Spell co5 = (Spell)chromaticOrb.copy();
//        co5.setBaseLevel(5);
//        co5.setName("Chromatic Orb Lvl. 5 (Radiant)");
//        co5.setEffects(new ArrayList<>());
//        co5.getEffects().add(new DamageEffect(new Damage("7d8 Radiant")));

        Action shortSword = new Action("Short Sword");
        shortSword.getEffects().add(new DamageEffect(new Damage("1d6 slashing")));
        shortSword.getAttempts().add(new MeleeAttempt());
        shortSword.setAddModifierToDamage(false);
        shortSword.getModifiers().add(Attribute.DEXTERITY);

        bilrick.getActions().add(axe);
        bilrick.getActions().add(shortSword);

        bilrick.setSpellSlots(new SpellSlots(4,3,3,3,2));

        return bilrick;
    }
}
