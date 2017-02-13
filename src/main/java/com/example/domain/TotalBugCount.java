package com.example.domain;

import java.io.Serializable;

/**
 * Created by Anjulaw on 2/13/2017.
 */
public class TotalBugCount implements Serializable {

    int totalBugCount;

    public int getTotalBugCount() {
        return totalBugCount;
    }

    public void setTotalBugCount(int totalBugCount) {
        this.totalBugCount = totalBugCount;
    }
}
