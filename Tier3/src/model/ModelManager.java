package model;


import database.DatabaseAccess;
import database.DatabaseCon;
import domain.Party;

import java.sql.SQLException;

public class ModelManager {
    private DatabaseCon db;

    public ModelManager(){
        db = new DatabaseAccess();

    }
    public void createParty(Party party){


        try {
            db.createParty(party);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
