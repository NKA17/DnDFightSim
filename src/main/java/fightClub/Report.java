package fightClub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Report {
    private String reportName;
    private List<Report> subReports = new ArrayList<>();
    private List<String> notes = new ArrayList<>();
    private HashMap<Object, Object> stats = new HashMap<>();

    public void incrementStat(Object key,double value){
        if(stats.containsKey(key)){
            stats.put(key,(double)stats.get(key)+value);
        }else {
            stats.put(key,value);
        }
    }

    public HashMap<Object, Object> getStats() {
        return stats;
    }

    public void addStat(Object key, Object value){
        stats.put(key,value);
    }

    public Object getStat(Object key){
        return stats.get(key);
    }

    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public void addNote(String note){
        getNotes().add(note);
    }

    public void addNote(String format, Object... values){
        String note = format.replaceAll("%f","%.2f");
        getNotes().add(String.format(note,values));
    }

    public List<Report> getSubReports() {
        return subReports;
    }

    public void setSubReports(List<Report> subReports) {
        this.subReports = subReports;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void setNotes(List<String> notes) {
        this.notes = notes;
    }
}
