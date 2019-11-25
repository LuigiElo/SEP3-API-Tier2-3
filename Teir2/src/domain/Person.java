package domain;

import java.io.Serializable;

public class Person implements Serializable {

    private int personID;
    private String name;
    private String email;
    private String password;
    private boolean isHost;
    private String username;


    public Person(int personID, String name, String username, String email, String password, boolean isHost) {
        this.personID = personID;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.isHost = isHost;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean host) {
        isHost = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
