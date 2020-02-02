package fightClub;

import models.Creature;

import java.util.ArrayList;
import java.util.List;

public class Team {
    private String name;
    private List<Creature> creatures = new ArrayList<>();


    public Team(String name) {
        this.name = name;
    }

    public Team() {    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Creature> getCreatures() {
        return creatures;
    }

    public void setCreatures(List<Creature> creatures) {
        this.creatures = creatures;
    }

    public String getTeamName(){
        if(creatures.size() == 1){
            return creatures.get(0).getName();
        }

        return name;
    }
}
