package com.example.gestfutsal.model;

import java.util.Comparator;

public class Team {

    private  int id;
    private  String name;
    private  int underAge;

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
        this.underAge = underAge;
    }

    //Comparator
    public  static Comparator<Team> TeamNameAZComparator = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            return t1.getName().compareTo(t2.getName());
        }
    };

    public  static Comparator<Team> TeamNameZAComparator = new Comparator<Team>() {
        @Override
        public int compare(Team t1, Team t2) {
            return t2.getName().compareTo(t1.getName());
        }
    };

    //Construtores


    public Team(int id, String name, int underAge) {
        this.id = id;
        this.name = name;
        this.underAge = underAge;
    }
    public Team() {
        id = 0;
        name = "";
        underAge = 0;
    }
    public Team(Team t) {
        id = t.id;
        name = t.name;
        underAge = t.underAge;
    }
    public Team(int id) {
        this.id = id;
        name = "";
        underAge = 0;
    }

    public Team(String name, int underAge) {
        id = 0;
        this.name = name;
        this.underAge = underAge;
    }

    //Encapsolamento
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnderAge() {
        return underAge;
    }

    public void setUnderAge(int underAge) {
        this.underAge = underAge;
    }


    //ToString
    @Override
    public String toString() {
        return name;
    }
}
