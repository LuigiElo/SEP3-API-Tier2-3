package domain;

import java.io.Serializable;

public class Person implements Serializable {

    private String personID;
    private String name;
    private String email;
    private String password;
    private boolean isHost;

    public Person(String personID, String name, String email, String password, boolean isHost) {
        this.personID = personID;
        this.name = name;
        this.email = email;
        this.password = password;
        this.isHost = isHost;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
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
}
