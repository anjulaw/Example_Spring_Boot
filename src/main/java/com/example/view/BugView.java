package com.example.view;

import java.io.Serializable;

/**
 * Created by Anjulaw on 12/26/2016.
 */
public class BugView implements Serializable {

    private double invalidBugCountRatio;

    public double getInvalidBugCountRatio() {
        return invalidBugCountRatio;
    }

    public void setInvalidBugCountRatio(double invalidBugCountRatio) {
        this.invalidBugCountRatio = invalidBugCountRatio;
    }
}
