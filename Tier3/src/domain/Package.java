package domain;

import java.util.Objects;

/**
 * java class used in the communication between tier 2 and tier 3 of the application
 * The purpose of this object is to rap different types of objects under a common
 * shell and the intention behind their use. The objects can encapsulate a variant number of objects too.
 */
public class Package {
    /**
     * The command of the package.
     * Provides information regarding what should be executated with the objects passed through the package
     */
    private String command;

    /**
     * The information contained by the package.
     */
    private Objects[] objects;


    /**
     * @param command
     * @param objects
     *
     * Constructor of the Package class. It takes a string parameter for the command and a vague argument
     * as the information(objects). The vague argument provides the posibility of passing multiple objects in a form
     * of an array with it's length undefined
     */
    public Package(String command, Object... objects)
    {
        this.command = command;
        this.objects = new Objects[objects.length];
        this.objects = (Objects[]) objects;

        ///now that i'm thinking about it, we mind need need something different, in the case wher we are sending a Party and a Person
        ///object at the same time....
        ////hmmm...i'm gonna think about it


    }


    /**
     * Getter for the command of the package
     * @return command (String)
     */
    public String getCommand() {
        return command;
    }

    /**
     * Setter for the command of the package
     * @param command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * Getter for the objects instance of the package. (the information it contains)
     * @return objects (Object[])
     */
    public Objects[] getObjects() {
        return objects;
    }

    /**
     * Setter for the information variable
     * @param objects
     */
    public void setObjects(Objects[] objects) {
        this.objects = objects;
    }
}
