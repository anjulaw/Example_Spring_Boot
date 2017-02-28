package com.example.utils;

import com.example.domain.Bug;
import com.example.domain.Issue;
import com.example.view.BugView;
import com.example.view.IssueView;

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
}
