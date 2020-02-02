package fightClub;

import java.util.ArrayList;
import java.util.List;

public class Scene {
    private String name;
    private List<Team> teams = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }
}
