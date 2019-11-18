package domain;

import java.util.Objects;

public class Package {

    private String command;
    private Objects[] objects;


    public Package(String command, Object... objects)
    {
        this.command = command;
        this.objects = new Objects[objects.length];
        this.objects = (Objects[]) objects;

    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Objects[] getObjects() {
        return objects;
    }

    public void setObjects(Objects[] objects) {
        this.objects = objects;
    }
}
