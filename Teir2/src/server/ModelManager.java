package server;

import models.Package;
import models.Party;

import java.io.IOException;

public class ModelManager {

    private DatabaseConnection db;

    public ModelManager() {
        this.db = new DatabaseConnection();
    }

    public Party createParty(Party party) throws IOException {

        Package packageT = new Package("createParty", party);
        Party party1 = db.createParty(packageT);
        if (party1 == null)
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return party1;

    }
}
