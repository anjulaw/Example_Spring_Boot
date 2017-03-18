package com.example.utils;

import com.example.domain.Bug;
import com.example.domain.Issue;
import com.example.view.BugView;
import com.example.view.IssueView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anjulaw on 2/17/2017.
 */
public class CalculationUtil {




    public static BugView calInvalidBugRatio(Bug totalBugObject, Bug invalidBugObject){

        BugView bgView = new BugView();

        double ratio  = ((invalidBugObject.getTotal() / totalBugObject.getTotal()) * 100);

        bgView.setInvalidBugCountRatio(ratio);
        return bgView;
    }

    public static BugView caldefectRemovalEfficiency (Bug totaldefectQA, Bug totalDefectEndUser){

        BugView bugView = new BugView();

        double totalBugCount = totaldefectQA.getTotal() + totalDefectEndUser.getTotal();

        double defectRemovalEfficiencyRatio = ((totaldefectQA.getTotal() / totalBugCount) * 100 );

        bugView.setDefectRemovalEfficiency(defectRemovalEfficiencyRatio);
        return bugView;
    }

    public static BugView calDefectLeakage (Bug defectFoundUAT,Bug defectFoundQA){

        BugView bugView = new BugView();

        double defectLeakageRatio = ((defectFoundUAT.getTotal() / defectFoundQA.getTotal()) * 100);

        bugView.setDefectLeackageRatio(defectLeakageRatio);
        return bugView;

    }

    public static BugView calDefectSeverity(Bug validDefectSeverity,Bug totalValidDefects,String defectPriority){

        BugView bugView = new BugView();

        Map<String,Double> severityIndexMap = new HashMap<String,Double> ();

        severityIndexMap.put("Blocker",new Double(6.5));
        severityIndexMap.put("Critical",new Double(5.3));
        severityIndexMap.put("Medium",new Double(3.2));
        severityIndexMap.put("Low",new Double(2.0));

        if(severityIndexMap.containsKey(defectPriority)){
            double severityIndex = severityIndexMap.get(defectPriority);

            double defectSeverityIndexRatio = ((severityIndex * validDefectSeverity.getTotal())/totalValidDefects.getTotal());
            bugView.setDefectSeverityIndexRatio(defectSeverityIndexRatio);
        }

        return bugView;
    }

    public static BugView caleffortVariance(Bug effoet){

        BugView bugView = new BugView();

        double estimatedEffort = effoet.getAggregatetimeoriginalestimate()/3600;
        double spentEffort = effoet.getAggregatetimespent()/3600;

        double effortVariance = ((spentEffort - estimatedEffort)/estimatedEffort) *100;
        bugView.setEffortVarianceRatio(effortVariance);

        return bugView;

    }
}
