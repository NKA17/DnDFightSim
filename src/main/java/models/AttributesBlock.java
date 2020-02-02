package models;

public class AttributesBlock {
    private int strength;
    private int dexterity;
    private int constitution;
    private int intelligence;
    private int wisdom;
    private int charisma;

    public AttributesBlock(){}

    public AttributesBlock(int strength, int dexterity, int constitution, int intelligence, int wisdom, int charisma) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public int getConstitution() {
        return constitution;
    }

    public void setConstitution(int constitution) {
        this.constitution = constitution;
    }

    public int getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }

    public int getWisdom() {
        return wisdom;
    }

    public void setWisdom(int wisdom) {
        this.wisdom = wisdom;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charisma) {
        this.charisma = charisma;
    }

    public int getAttributeModifier(Attribute att){

        switch (att){
            case STRENGTH:
                return getStrengthModifier();
            case DEXTERITY:
                return getDexterityModifier();
            case CONSTITUTION:
                return getConstitutionModifier();
            case INTELLIGENCE:
                return getIntelligenceModifier();
            case WISDOM:
                return getWisdomModifier();
            case CHARISMA:
                return getCharismaModifier();
        }

        return 0;
    }

    private int getModifier(int att){
        return (att - 10) / 2;
    }
    public int getStrengthModifier(){
        return getModifier(strength);
    }

    public int getDexterityModifier(){
        return getModifier(dexterity);
    }

    public int getConstitutionModifier(){
        return getModifier(constitution);
    }

    public int getIntelligenceModifier(){
        return getModifier(intelligence);
    }

    public int getWisdomModifier(){
        return getModifier(wisdom);
    }

    public int getCharismaModifier(){
        return getModifier(charisma);
    }
}
