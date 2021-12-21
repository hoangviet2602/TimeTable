package com.example.timetable;

public class User {
    int idUser;
    String Username;
    String Pass;

    public User(int idUser, String username, String pass) {
        this.idUser = idUser;
        Username = username;
        Pass = pass;
    }
    public User() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPass() {
        return Pass;
    }

    public void setPass(String pass) {
        Pass = pass;
    }
}
