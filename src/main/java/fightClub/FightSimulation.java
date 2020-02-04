package fightClub;

import javafx.util.Pair;
import models.Action;
import models.Creature;
import models.Damage;

import java.awt.*;
import java.util.*;
import java.util.List;

public class FightSimulation {
    private Scene scene;
    private Scene originalScene;
    private Report hpReport;
    private Report initiativeReport;
    private Report deathsOnTurn;
    private Random random;
    private Report killReport;

    public FightSimulation(Scene scene) {
        deathsOnTurn = new Report();
        deathsOnTurn.setReportName("Deaths");
        killReport = new Report();
        killReport.setReportName("Kill Report");
        originalScene = scene;
        random = new Random(1570035L);
    }

    private void init(){
        Scene copy = new Scene();
        copy.setName(originalScene.getName());
        for(Team t: originalScene.getTeams()){
            Team copyTeam = new Team();
            copyTeam.setName(t.getName());
            copyTeam.getCreatures().addAll(t.getCreatures());
            copy.getTeams().add(copyTeam);

            for(Creature c: t.getCreatures()){
                c.setHp(c.getMaxHp());
                for(Action a: c.getActions()){
                    a.resetRules();
                }
                for(Action a: c.getLegendaryActions()){
                    a.resetRules();
                }
                c.resetSpellSlots();
            }
        }

        this.scene = copy;
    }

    public Report[] simulateMany(int simulations){
        Report[] reports = new Report[simulations];

        for(int i = 0; i < simulations; i++){
            Report report = simulate();
            report.setReportName("Simulation "+i);
            reports[i] = report;
        }

        double t1wins = 0.0;
        double t2wins = 0.0;

        for(Report r: reports){
            t1wins += r.getStat("winner").equals(1) ? 1 : 0;
            t2wins += r.getStat("winner").equals(2) ? 1 : 0;
        }

        if(t1wins > t2wins){
            System.out.println(scene.getTeams().get(0).getTeamName()+" wins "+((t1wins/(t1wins+t2wins))*100)+"%; " +
                    "("+t1wins+" of "+(t1wins+t2wins)+")");
        }else {
            System.out.println(scene.getTeams().get(1).getTeamName()+" wins "+((t2wins/(t1wins+t2wins))*100)+"%; " +
                    "("+t2wins+" of "+(t1wins+t2wins)+")");

        }

        System.out.println("----------");
        Iterator<Object> iter = deathsOnTurn.getStats().keySet().iterator();
        while(iter.hasNext()){
            Creature c = (Creature)iter.next();
            List<Integer> turns = (List<Integer>) deathsOnTurn.getStat(c);
            int ave = 0;
            for(Integer i: turns){
                ave+= i;
            }
            ave = ave / turns.size();
            System.out.println(c.getName()+" died "+turns.size()+" times. Typically on round "+ave);
        }

        System.out.println("----------");
        iter = killReport.getStats().keySet().iterator();
        while (iter.hasNext()){
            Creature key = (Creature)iter.next();
            double count = (double)killReport.getStat(key);
            System.out.println(key.getName()+" had "+count+" kills. Average "+(count/simulations)+" per combat.");
        }
        return reports;
    }

    public Report simulate(){
        init();
        Team t1 = scene.getTeams().get(0);
        Team t2 = scene.getTeams().get(1);

        hpReport = hpChart();
        initiativeReport = rollInitiative();

        Report report = new Report();
        report.setReportName(t1.getTeamName() +" vs. "+t2.getTeamName());

        int round = 1;
        while(t1.getCreatures().size() > 0 && t2.getCreatures().size() > 0){
            Report r = round(round++,t1,t2);
            report.getSubReports().add(r);
        }

        int winner = t1.getCreatures().size() > 0 ? 1 : 2;
        int loser = t1.getCreatures().size() > 0 ? 2 : 1;

        report.addStat("winner",winner);
        report.addStat("loser",loser);



        report.addStat("hpReport",hpReport);

        return report;
    }

    private Report rollInitiative(){
        Report report = new Report();
        report.setReportName("Initiative");

        for(Team t: scene.getTeams()){
            for(Creature c: t.getCreatures()){
                int init = random.nextInt(20)+1 + c.getAttributesBlock().getDexterityModifier();
                report.addStat(c,init);
            }
        }

        return report;
    }

    public Report round(int round, Team t1, Team t2){
        Report report = new Report();
        report.setReportName("Round "+(round));
        System.out.println(report.getReportName()+" -------");

        List<Creature> legendaries = new ArrayList<>();

        List<Creature> allCreatures = new ArrayList<>();
        allCreatures.addAll(t1.getCreatures());
        allCreatures.addAll(t2.getCreatures());
        Collections.sort(allCreatures, new Comparator<Creature>() {
            @Override
            public int compare(Creature o1, Creature o2) {
                int i1 = (int)initiativeReport.getStat(o1);
                int i2 = (int)initiativeReport.getStat(o2);
                return i2 - i1;
            }
        });
        for(Creature c: allCreatures){
            if(c.getLegendaryActionPoints() > 0){
                legendaries.add(c);
                c.setLegendaryActionPointPool(c.getLegendaryActionPoints());
            }
        }

        for(Creature creature: allCreatures){
            Report actionReport = takeAction(creature,t1,t2,round);
            report.getNotes().addAll(actionReport.getNotes());
            if(t1.getCreatures().size() == 0 || t2.getCreatures().size() == 0){
                break;
            }
            for(Creature lendary: legendaries){
                if(lendary != creature ){
                    if((int)initiativeReport.getStat(creature) > (int)initiativeReport.getStat(lendary) && round == 1){
                        continue;
                    }
                    Report legendaryReport = takeLegendaryAction(lendary,t1,t2,round);
                    report.getNotes().addAll(legendaryReport.getNotes());
                    if(t1.getCreatures().size() == 0 || t2.getCreatures().size() == 0){
                        break;
                    }
                }
            }
        }

        for(String s: report.getNotes()){
            System.out.println(s);
        }

        round++;

        return report;
    }

    private void kill(Creature killer, Creature dead, List<Creature> creatureTeam, int round){
        creatureTeam.remove(dead);
        List<Integer> turns;
        if(deathsOnTurn.getStats().containsKey(dead)){
            turns = (List<Integer>)deathsOnTurn.getStat(dead);
        }else {
            turns = new ArrayList<>();
            deathsOnTurn.addStat(dead,turns);
        }
        turns.add(round);

        killReport.incrementStat(killer,1);
    }

    private Report hpChart(){
        hpReport = new Report();
        hpReport.setReportName("HP Graph");
        for(Team t: scene.getTeams()){
            for(Creature c: t.getCreatures()){
                List<Point> hpLine = new ArrayList<>();
                hpLine.add(new Point(0,c.getHp()));
                hpReport.addStat(c,hpLine);
            }
        }

        return hpReport;
    }

    private Report takeLegendaryAction(Creature creature, Team t1, Team t2, int round){
        Report report = new Report();

        if(isDead(creature) || creature.getLegendaryActionPointPool() <= 0){
            return report;
        }
        List<Creature> enemyTeam = t1.getCreatures().contains(creature) ? t2.getCreatures() : t1.getCreatures();
        if(enemyTeam.size() == 0){
            return report;
        }

        Creature target = enemyTeam.get(random.nextInt(enemyTeam.size()));
        List<Action> availableActions = getAvailableActions(creature.getLegendaryActions(),creature,target);

        Action useAction = availableActions.get(random.nextInt(availableActions.size()));
        useAction.use(creature,target);
        if(isDead(target)){
            //report.addNote("%s died!", target.getName());
            System.out.println(String.format("%s died!", target.getName()));
            kill(creature,target,enemyTeam,round);
        }

        return report;
    }

    private Report takeAction(Creature creature, Team t1, Team t2, int round){
        Report report = new Report();

        if(isDead(creature)){
            return report;
        }
        List<Creature> enemyTeam = t1.getCreatures().contains(creature) ? t2.getCreatures() : t1.getCreatures();
        if(enemyTeam.size() == 0){
            return report;
        }

        Creature target = enemyTeam.get(random.nextInt(enemyTeam.size()));
        Report attackReport = attack(round,creature,target);
        report.getNotes().addAll(attackReport.getNotes());
        if(isDead(target)){
            //report.addNote("%s died!", target.getName());
            System.out.println(String.format("%s died!", target.getName()));
            kill(creature,target,enemyTeam,round);
        }

        return report;
    }
    private Report attack(int round, Creature attacker, Creature target){
        Report report = new Report();

        List<Action> available = getAvailableActions(attacker.getActions(),attacker,target);
        Action useAction = FightUtils.getHighestDamageAction(available,target);
        if(useAction != null) {
            useAction.use(attacker, target);
        }else {
            List<Action> a = getAvailableActions(attacker.getActions(),attacker,target);
            Action b = FightUtils.getHighestDamageAction(available,target);
            int i = 2+3;
        }

        List<Point> hpList = (ArrayList<Point>)hpReport.getStat(target);
        for(Point p: hpList){
            if(p.x == round) {
                p.y = target.getHp();
                return report;
            }
        }
        hpList.add(new Point(round,target.getHp()));

        return report;
    }

    private boolean isDead(Creature c){
        List<Point> hpList = (ArrayList<Point>)hpReport.getStat(c);
        return hpList.get(hpList.size() - 1).y <= 0;
    }

    private List<Action> getAvailableActions(List<Action> list,Creature self,Creature target){
        List<Action> actions = new ArrayList<>();
        for(Action a: list){
            if(a.canUse(self,target))
                actions.add(a);
        }

        return actions;
    }
}
