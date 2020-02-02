package models;

public class Spell extends Action{

    private Attribute saveAttribute;
    private Attribute attackAttribute;

    public Spell(String name) {
        super(name);
    }

    public Attribute getSaveAttribute() {
        return saveAttribute;
    }

    public void setSaveAttribute(Attribute saveAttribute) {
        this.saveAttribute = saveAttribute;
    }

    public Attribute getAttackAttribute() {
        return attackAttribute;
    }

    public void setAttackAttribute(Attribute attackAttribute) {
        this.attackAttribute = attackAttribute;
    }
}
