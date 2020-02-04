package models;


public enum DamageType {
    OTHER("OTHER"),
    BLUDGEONING("BLUDGEONING"),
    ACID("ACID"),
    COLD("COLD"),
    SLASHING("SLASHING"),
    PIERCING("PIERCING"),
    POISON("POISON"),
    FIRE("FIRE"),
    FORCE("FORCE"),
    LIGHTNING("LIGHTNING"),
    RADIANT("RADIANT"),
    NECROTIC("NECROTIC");

    private String damageType;

    DamageType(String dt){
        damageType = dt;
    }

    public static DamageType SET(String s){
        for(DamageType d: DamageType.values()){
            if(d.damageType.equalsIgnoreCase(s)){
                return d;
            }
        }
        return OTHER;
    }
}
