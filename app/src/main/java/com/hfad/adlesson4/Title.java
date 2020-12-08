package com.hfad.adlesson4;

import java.io.Serializable;

public class Title implements Serializable  {

    public String name;
    public String lastName;
    public String date;


    public Title(String name, String date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
