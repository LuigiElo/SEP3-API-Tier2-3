package models;

import java.io.Serializable;

public class Party implements Serializable {

    private String partyTitle;
    private String description;
    private String address;


    public Party()
    {

    }

    public Party(String partyTitle, String description, String address) {
        this.partyTitle = partyTitle;
        this.description = description;
        this.address = address;
    }

    public String getPartyTitle() {
        return partyTitle;
    }

    public void setPartyTitle(String partyTitle) {
        this.partyTitle = partyTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
