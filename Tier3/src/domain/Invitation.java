package domain;

import java.io.Serializable;

/**
 * Java class defining and incapacitating the characteristics of an Invitation between users
 * of the system; The Invitation is used in asking fo permission to add a certain
 * user as participant in a specific Party
 * When an invitation is answered it is used as a notification
 */
public class Invitation implements Serializable
{
    /***
     *Properties of an Invitation object:
     * -the personID of the user requested to be added as participant
     * -the Party in which the add should happen
     * -the status of the invitation: "pending", "accepted", "declined"
     */
    private int personId;
    private Party party;
    private String status;

    /**
     *Empty constructor of an Invitation object
     */
    public Invitation(){}

    /**
     *Constructor of an Invitation object
     * @param personId
     * @param party
     * @param status
     */
    public Invitation(int personId, Party party, String status) {
        this.personId = personId;
        this.party = party;
        this.status = status;
    }

    /**
     * Getter for the personID variable
     * @return personID
     */
    public int getPersonId() {
        return personId;
    }

    /**
     *Setter for the personID variable
     * @param personId
     */
    public void setPersonId(int personId) {
        this.personId = personId;
    }

    /**
     *Getter for the party variable
     * @return party
     */
    public Party getParty() {
        return party;
    }

    /**
     *Setter for the party variable
     * @param party
     */
    public void setParty(Party party) {
        this.party = party;
    }

    /**
     *Getter for the status variable
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     *Setter for the status variable
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Returns a String representation of  an Invitation object
     * @return String
     */
    @Override
    public String toString() {
        return "Invitation{" +
                "personId=" + personId +
                ", party=" + party +
                ", status='" + status + '\'' +
                '}';
    }
}
