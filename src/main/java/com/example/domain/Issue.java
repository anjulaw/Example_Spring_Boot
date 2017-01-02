package com.example.domain;

import java.io.Serializable;

/**
 * Created by Anjulaw on 12/26/2016.
 */
public class Issue implements Serializable {

    String expand;
    int id;

    public String getExpand() {
        return expand;
    }

    public void setExpand(String expand) {
        this.expand = expand;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
