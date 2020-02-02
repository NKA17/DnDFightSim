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

    public FightSimulation(Scene scene) {
        deathsOnTurn = new Report();
        deathsOnTurn.setReportName("Deaths");
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

        System.out.println(report.getReportName()+" -------");
        for(String s: report.getNotes()){
            System.out.println(s);
        }

        round++;

        return report;
    }

    private void kill(Creature dead, List<Creature> creatureTeam, int round){
        creatureTeam.remove(dead);
        List<Integer> turns;
        if(deathsOnTurn.getStats().containsKey(dead)){
            turns = (List<Integer>)deathsOnTurn.getStat(dead);
        }else {
            turns = new ArrayList<>();
            deathsOnTurn.addStat(dead,turns);
        }
        turns.add(round);
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
        List<Action> availableActions = new ArrayList<>(creature.getLegendaryActions());
        for(int i = 0; i < availableActions.size(); i++){
            if(availableActions.get(i).getCost() > creature.getLegendaryActionPointPool()){
                availableActions.remove(availableActions.get(i));
                i--;
            }
        }
        Action useAction = availableActions.get(random.nextInt(availableActions.size()));
        creature.setLegendaryActionPointPool(creature.getLegendaryActionPointPool() - useAction.getCost());
        attack(report,creature,target,useAction,round);
        for(int i = 0; i < report.getNotes().size(); i++){
            report.getNotes().set(i,"Legendary Action: "+report.getNotes().get(i));
        }
        if(isDead(target)){
            report.addNote("%s died!", target.getName());
            kill(target,enemyTeam,round);
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
            report.addNote("%s died!", target.getName());
            kill(target,enemyTeam,round);
        }

        return report;
    }
    private Report attack(int round, Creature attacker, Creature target){
        Report report = new Report();

        List<Action> available = getAvailableActions(attacker.getActions(),attacker,target);
        Action useAction = FightUtils.getHighestDamageAction(available,target);
        attack(report,attacker,target,useAction,round);
        for(Action action: useAction.getSubsequentActions()){
            attack(report,attacker,target,action,round);
        }

        return report;
    }

    private void attack(Report report, Creature attacker, Creature target,Action useAction,int round){
        double toHit = FightUtils.getChanceToHit(useAction,attacker,target);

        boolean hit = toHit >= random.nextDouble();

        int damage = hit ? FightUtils.getAvePotentialDamage(useAction,attacker,target) : 0;
        damage(target,round,damage);

        if(hit){
            report.addNote("%s attacks %s with %s! Hit for %d damage!",attacker.getName(),target.getName(),
                    useAction.getName(),damage);
            for(Damage d: useAction.getHeal()){
                damage(attacker,round, - d.average());
                report.addNote("%s healed for %d HP!",attacker.getName(),d.average());
            }
        }else{
            report.addNote("%s attacks %s with %s! It's a miss!",attacker.getName(),target.getName(),useAction.getName());
        }
    }

    private void damage(Creature c,int round, int damage){
        List<Point> hpList = (ArrayList<Point>)hpReport.getStat(c);


        int newHp = (((Point)hpList.get(hpList.size() - 1)).y - damage);
        for(Point p: hpList){
            if(p.x == round){
                p.y -= damage;
                return;
            }
        }
        hpList.add(new Point(round, newHp));
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
