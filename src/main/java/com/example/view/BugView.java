package com.example.view;

import java.io.Serializable;

/**
 * Created by Anjulaw on 12/26/2016.
 */
public class BugView implements Serializable {

    private double invalidBugCountRatio;
    private double defectRemovalEfficiency;
    private double defectLeackageRatio;
    private double effortVarianceRatio;
    private double defectSeverityIndexRatio;

    public double getEffortVarianceRatio() {
        return effortVarianceRatio;
    }

    public void setEffortVarianceRatio(double effortVarianceRatio) {
        this.effortVarianceRatio = effortVarianceRatio;
    }

    public double getDefectSeverityIndexRatio() {
        return defectSeverityIndexRatio;
    }

    public void setDefectSeverityIndexRatio(double defectSeverityIndexRatio) {
        this.defectSeverityIndexRatio = defectSeverityIndexRatio;
    }

    public double getInvalidBugCountRatio() {
        return invalidBugCountRatio;
    }

    public void setInvalidBugCountRatio(double invalidBugCountRatio) {
        this.invalidBugCountRatio = invalidBugCountRatio;
    }

    public double getDefectRemovalEfficiency() {
        return defectRemovalEfficiency;
    }

    public void setDefectRemovalEfficiency(double defectRemovalEfficiency) {
        this.defectRemovalEfficiency = defectRemovalEfficiency;
    }

    public double getDefectLeackageRatio() {
        return defectLeackageRatio;
    }

    public void setDefectLeackageRatio(double defectLeackageRatio) {

        this.defectLeackageRatio = defectLeackageRatio;
    }




}
