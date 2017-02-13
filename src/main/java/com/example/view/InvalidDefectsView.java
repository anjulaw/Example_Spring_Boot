package com.example.view;

import java.io.Serializable;

/**
 * Created by Anjulaw on 2/13/2017.
 */
public class InvalidDefectsView implements Serializable {

    int totalBugCount;
    int invaidBugCount;

    public int getTotalBugCount() {
        return totalBugCount;
    }

    public void setTotalBugCount(int totalBugCount) {
        this.totalBugCount = totalBugCount;
    }
}
