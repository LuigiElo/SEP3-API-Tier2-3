package domain;

public class Invitation
{
    private int personId;
    private Party party;
    private String status;

    public Invitation(){}

    public Invitation(int personId, Party party, String status) {
        this.personId = personId;
        this.party = party;
        this.status = status;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public Party getParty() {
        return party;
    }

    public void setParty(Party party) {
        this.party = party;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Invitation{" +
                "personId=" + personId +
                ", party=" + party +
                ", status='" + status + '\'' +
                '}';
    }
}
