package com.example.domain;

/*import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;*/

/**
 * Created by Anjulaw on 12/26/2016.
 */
//@Entity
//@Table(name = "user")
public class User {

   /* @Id*/
    int id;
    String email;

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return email;
    }

    public void setName(String name) {
        this.email = name;
    }
}
