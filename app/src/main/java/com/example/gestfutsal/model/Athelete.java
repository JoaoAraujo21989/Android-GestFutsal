package com.example.gestfutsal.model;

import java.util.Comparator;

public class Athelete {
    private int id;
    private String name;
    private int number;
    private String genre;
    private String email;
    private String tel;
    private String address;
    private int idTeam;

    //Construtores

    public Athelete() {
        id = 0;
        name = "";
        number = 0;
        genre = "";
        email = "";
        tel = "";
        address = "";
        idTeam = 0;
    }

    public Athelete(int id, String name, int number, String genre, String email, String tel, String address, int idTeam) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.genre = genre;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.idTeam = idTeam;
    }

    public Athelete(String name, int number, String genre, String email, String tel, String address, int idTeam) {
        this.name = name;
        this.number = number;
        this.genre = genre;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.idTeam = idTeam;
    }

    public Athelete(int id, String name, int number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    //Comparator
    public static Comparator<Athelete> AtheleteNameAZComparator = new Comparator<Athelete>() {
        @Override
        public int compare(Athelete a1, Athelete a2) {
            return a1.getName().compareTo(a2.getName());
        }
    };

    public static Comparator<Athelete> AtheleteNameZAComparator = new Comparator<Athelete>() {
        @Override
        public int compare(Athelete a1, Athelete a2) {
            return a2.getName().compareTo(a1.getName());
        }
    };


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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getIdTeam() {
        return idTeam;
    }

    public void setIdTeam(int idTeam) {
        this.idTeam = idTeam;
    }


    //ToString

    @Override
    public String toString() {
        return "Athelete{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", genre='" + genre + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + tel + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
