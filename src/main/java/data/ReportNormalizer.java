package data;

import fightClub.Report;
import fightClub.Team;
import models.Creature;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ReportNormalizer {
    public static HashMap<Object, List<Point>> hpReportToRawData(Report report){
        int highestX = 0;

        HashMap<Object, List<Point>> normalizedMap = new HashMap<>();
        Report hpreport = (Report)report.getStat("hpReport");
        Iterator<Object> iter = hpreport.getStats().keySet().iterator();
        while (iter.hasNext()){
            Creature c = (Creature)iter.next();
            List<Point> points = (List<Point>)hpreport.getStat(c);
            normalizedMap.put(c,points);
            for(int i = 0; i < points.size()-1; i++){
                highestX = Math.max(highestX,points.get(i+1).x);
                if(points.get(i+1).x != points.get(i).x+1){
                    points.add(points.get(i).x+1,new Point(points.get(i).x+1,points.get(i).y));
                }
            }
        }

        iter = normalizedMap.keySet().iterator();
        while(iter.hasNext()){
            Creature c = (Creature)iter.next();
            List<Point> points = normalizedMap.get(c);
            Point lastPoint = points.get(points.size()-1);
            for(int i = lastPoint.x+1; i <= highestX; i++){
               // points.add(new Point(i,lastPoint.y));
            }
        }

        return normalizedMap;
    }
    public static HashMap<Object, List<Point>> averageNormalizedMaps(List<HashMap<Object, List<Point>>> rawData){
        HashMap<Object,HashMap<Integer,List<Integer>>> averagesRaw = new HashMap<>();
        HashMap<Object, List<Point>> averaged = new HashMap<>();

        //setup
        for(HashMap<Object,List<Point>> map: rawData){
            Iterator<Object> iter = map.keySet().iterator();
            while (iter.hasNext()){
                Creature c = (Creature)iter.next();
                HashMap<Integer,List<Integer>> xyMap;

                //init creature's map
                if(!averagesRaw.containsKey(c)){
                    xyMap = new HashMap<>();
                    averagesRaw.put(c,xyMap);
                }else {
                    xyMap = averagesRaw.get(c);
                }

                List<Point> points = map.get(c);
                for(Point p: points){
                    //init list
                    List<Integer> pointList;
                    if(xyMap.containsKey(p.x)){
                        pointList = xyMap.get(p.x);
                    }else {
                        pointList = new ArrayList<>();
                        xyMap.put(p.x,pointList);
                    }

                    pointList.add(p.y);
                }
            }
        }

        Iterator<Object> iter = averagesRaw.keySet().iterator();
        while (iter.hasNext()){
            Creature c = (Creature)iter.next();
            List<Point> averagedPoints;
            if(averaged.containsKey(c)){
                averagedPoints = averaged.get(c);
            }else{
                averagedPoints = new ArrayList<>();
                averaged.put(c,averagedPoints);
            }

            HashMap<Integer,List<Integer>> xyMap = averagesRaw.get(c);
            Iterator<Integer> xIter = xyMap.keySet().iterator();
            while (xIter.hasNext()){
                int x = xIter.next();
                List<Integer> yList = xyMap.get(x);
                int total = 0;
                int count = 0;
                for(Integer y: yList){
                    total += y;
                    count++;
                }
                averagedPoints.add(new Point(x,total/count));
            }
        }

        return averaged;
    }
}
