package com.example.domain;

import java.io.Serializable;

/**
 * Created by Anjulaw on 2/13/2017.
 */
public class Bug implements Serializable {

    private double total;
    private double aggregatetimespent;
    private double aggregatetimeoriginalestimate;
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public double getAggregatetimeoriginalestimate() {
        return aggregatetimeoriginalestimate;
    }

    public void setAggregatetimeoriginalestimate(double aggregatetimeoriginalestimate) {
        this.aggregatetimeoriginalestimate = aggregatetimeoriginalestimate;
    }


    public double getAggregatetimespent() {
        return aggregatetimespent;
    }

    public void setAggregatetimespent(double aggregatetimespent) {
        this.aggregatetimespent = aggregatetimespent;
    }


    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
