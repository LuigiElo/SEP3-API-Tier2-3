import domain.Party;
import model.ModelManager;

import java.util.ArrayList;

public class Test {


    public ArrayList<String> list;

    public ArrayList<String> getList() {
        return list;
    }

    public Test(){
        list = new ArrayList<String>();
    }

    public void addList(String party) {
        list.add(party);
        System.out.println(party);
    }
    public static void main(String[] args) {
        ModelManager mm = new ModelManager();
       // mm.createParty(new Party("dddddddddd","ddd","ddd","ddd"));
    }
}
