package models;


import models.equipment.DnD5eEquipment;
import models.misc.DnD5eDamage;
import models.spells.DnD5eSpell;
import models.spells.DnD5eSpellMagazine;

import java.util.ArrayList;
import java.util.List;

public class Creature {
    private String name;
    private int hp;
    private int maxHp;
    private int ac;
    private int proficiencyBonus;
    private AttributesBlock attributesBlock = new AttributesBlock();
    private List<DamageType> vulnerabilities = new ArrayList<>();
    private List<DamageType> resistances = new ArrayList<>();
    private List<DamageType> immunities = new ArrayList<>();
    private DnD5eSpellMagazine spellMagazine;
    private List<DnD5eEquipment> equipment = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();
    private List<DnD5eSpell> spells = new ArrayList<>();
    private int legendaryActionPointPool = 0;
    private int legendaryActionPoints = 0;
    private List<Action> legendaryActions = new ArrayList<>();

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
        hp = maxHp;
    }

    public int getLegendaryActionPoints() {
        return legendaryActionPoints;
    }

    public void setLegendaryActionPoints(int legendaryActionPoints) {
        this.legendaryActionPoints = legendaryActionPoints;
        this.legendaryActionPointPool = this.legendaryActionPoints;
    }

    public int getLegendaryActionPointPool() {
        return legendaryActionPointPool;
    }

    public void setLegendaryActionPointPool(int legendaryActionPointPool) {
        this.legendaryActionPointPool = legendaryActionPointPool;
    }

    public List<Action> getLegendaryActions() {
        return legendaryActions;
    }

    public void setLegendaryActions(List<Action> legendaryActions) {
        this.legendaryActions = legendaryActions;
    }

    public AttributesBlock getAttributesBlock() {
        return attributesBlock;
    }

    public void setAttributesBlock(AttributesBlock attributesBlock) {
        this.attributesBlock = attributesBlock;
    }

    public int getProficiencyBonus() {
        return proficiencyBonus;
    }

    public void setProficiencyBonus(int proficiencyBonus) {
        this.proficiencyBonus = proficiencyBonus;
    }

    public List<DamageType> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<DamageType> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

    public List<DamageType> getResistances() {
        return resistances;
    }

    public void setResistances(List<DamageType> resistances) {
        this.resistances = resistances;
    }

    public List<DamageType> getImmunities() {
        return immunities;
    }

    public void setImmunities(List<DamageType> immunities) {
        this.immunities = immunities;
    }

    public List<DnD5eSpell> getSpells() {
        return spells;
    }

    public void setSpells(List<DnD5eSpell> spells) {
        this.spells = spells;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAc() {
        return ac;
    }

    public void setAc(int ac) {
        this.ac = ac;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public List<Action> getActions() {
        return actions;
    }

    public Creature(String name) {
        this.name = name;
    }

    public Creature(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DnD5eSpellMagazine getSpellMagazine() {
        return spellMagazine;
    }

    public void setSpellMagazine(DnD5eSpellMagazine spellMagazine) {
        this.spellMagazine = spellMagazine;
    }

    public List<DnD5eEquipment> getEquipment() {
        return equipment;
    }

    public void setEquipment(List<DnD5eEquipment> equipment) {
        this.equipment = equipment;
    }

    public String toString(){
        return String.format("%s {HP: %d, AC: %d}",name,hp,ac);
    }
}
