package fightClub;

import models.Action;
import models.Creature;

public class FightClub {
    private Scene scene;
    private static final String ADVANTAGE_RATIO = "advantage_ratio";
    private static final String CREATURE_1 = "creature_1";
    private static final String CREATURE_2 = "creature_2";

    public FightClub(Scene scene) {
        this.scene = scene;
    }

    public Report fight(){
        Report report = new Report();
        report.setReportName(generateReportName());

        Team t1 = scene.getTeams().get(0);
        Team t2 = scene.getTeams().get(1);

        //1 v 1
        for(Creature c1: t1.getCreatures()){
            for(Creature c2: t2.getCreatures()){
                Report pitReport = pitFighters(c1,c2);
                report.getSubReports().add(pitReport);
            }
        }

        //1 v many
        for(Creature c1: t1.getCreatures()){
            Report pitReport = pitFighterAgainstTeam(c1,t2);
            report.getSubReports().add(pitReport);
        }


        //many v 1
        for(Creature c2: t2.getCreatures()){
            Report pitReport = pitFighterAgainstTeam(c2,t1);
            report.getSubReports().add(pitReport);
        }

        return report;
    }

    private Report pitFighterAgainstTeam(Creature c1, Team t2){
        Report report = new Report();
        report.setReportName(c1.getName()+" vs. "+t2.getTeamName());



        return report;
    }

    private Report pitFighters(Creature c1, Creature c2){
        Report report = new Report();
        report.setReportName(c1.getName()+" vs. "+c2.getName());

        FightingPit pit = new FightingPit(c1,c2);

        Action actionOnC1 = pit.getC2BestAction();
        Action actionOnC2 = pit.getC1BestAction();
        String bestActionFormat = "%s best action: %s";
        report.addNote(String.format(bestActionFormat,c1.getName(),actionOnC2.getName()));
        report.addNote(String.format(bestActionFormat,c2.getName(),actionOnC1.getName()));

        int damageOnC1 = pit.getC2AverageDPT();
        int damageOnC2 = pit.getC1AverageDPT();
        String damgeOnFormat = "%s damage on %s: %d";
        report.addNote(damgeOnFormat,c1.getName(),c2.getName(),damageOnC2);
        report.addNote(damgeOnFormat,c2.getName(),c1.getName(),damageOnC1);

        double c1ChanceToHit = pit.getC1ChanceToHit(actionOnC2);
        double c2ChanceToHit = pit.getC2ChanceToHit(actionOnC1);
        String chanceToHitFormat = "%s chance to hit %s: %f";
        report.addNote(String.format(chanceToHitFormat,c1.getName(),c2.getName(),c1ChanceToHit));
        report.addNote(String.format(chanceToHitFormat,c2.getName(),c1.getName(),c2ChanceToHit));

        int turnsToKillC1 = (int)Math.ceil(c1.getHp() / (damageOnC1 * c2ChanceToHit));
        int turnsToKillC2 = (int)Math.ceil(c2.getHp() / (damageOnC2 * c1ChanceToHit));
        String turnsToKillFormat = "%s turns to kill %s: %d";
        report.addNote(turnsToKillFormat,c1.getName(),c2.getName(),turnsToKillC2);
        report.addNote(turnsToKillFormat,c2.getName(),c1.getName(),turnsToKillC1);

        double slowestDeath = Math.max(turnsToKillC1,turnsToKillC2);
        double fastestDeath = Math.min(turnsToKillC1,turnsToKillC2);
        double ratio = fastestDeath / slowestDeath;

        ratio = turnsToKillC1 > turnsToKillC2 ? ratio : -1 * ratio;
        String ratioFormat = "Ratio: %f";
        report.addNote(String.format(ratioFormat,ratio));

        report.addStat(ADVANTAGE_RATIO,ratio);
        report.addStat(CREATURE_1,c1);
        report.addStat(CREATURE_2,c2);

        return report;
    }

    private String generateReportName(){
        String str = "";

        for(int i = 0; i < scene.getTeams().size(); i++){
            if(i > 0){
                str += "vs. ";
            }
            str += scene.getTeams().get(0).getTeamName();
        }

        return str;
    }
}
