package models;

import data.Copiable;
import models.misc.DnD5eDamage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Damage implements Copiable {
    private int diceCount;
    private int diceSize;
    private int damageBonus;
    private DamageType damageType;

    public Damage(){}

    public Damage(String damage){
        setDamage(damage);
    }

    @Override
    public Copiable copy() {
        Damage d = new Damage();
        d.diceCount = diceCount;
        d.diceSize = diceSize;
        d.damageBonus = damageBonus;
        d.damageType = damageType;
        return d;
    }

    public String getDamageDice() {
        return diceCount+"d"+diceSize;
    }

    public void setDamageDice(String damageDice) {
        String[] parts = damageDice.split("d");
        diceCount = Integer.parseInt(parts[0]);
        diceSize = Integer.parseInt(parts[1]);
    }

    public void setDamage(String damage){
        Matcher diceM = Pattern.compile("\\d+d\\d+").matcher(damage);
        if(diceM.find()){
            setDamageDice(diceM.group());
        }

        Matcher bonusM = Pattern.compile("\\+\\s*(\\d+)").matcher(damage);
        if(bonusM.find()){
            setDamageBonus(bonusM.group(1));
        }

        Matcher typeM = Pattern.compile("(^|\\D|\\s)(?<type>[a-zA-Z]+)").matcher(damage);
        if(typeM.find()){
            setDamageType(typeM.group("type"));
        }
    }

    public int average(){
        return (diceCount * diceSize) / 2 + damageBonus;
    }

    public int max(){
        return diceCount * diceSize + damageBonus;
    }

    public int min(){
        return diceCount + damageBonus;
    }

    public int getDiceCount() {
        return diceCount;
    }

    public void setDiceCount(int diceCount) {
        this.diceCount = diceCount;
    }

    public int getDiceSize() {
        return diceSize;
    }

    public void setDiceSize(int diceSize) {
        this.diceSize = diceSize;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public void setDamageBonus(int damageBonus) {
        this.damageBonus = damageBonus;
    }

    public DamageType getDamageType() {
        return damageType;
    }

    public void setDamageType(DamageType damageType) {
        this.damageType = damageType;
    }

    public void setDamageBonus(String bonus){
        damageBonus = Integer.parseInt(bonus);
    }

    public void setDamageType(String type){
        this.damageType = DamageType.SET(type);
    }
}
