package edu.ranken.emeier.gittogo.models;

public class Repo {

    private final String _name;
    private final String _owner;
    private final String _desc;

    public Repo(String name, String owner, String desc) {
        _name = name;
        _owner = owner;
        _desc = desc;
    }

    public String getName() {
        return _name;
    }

    public String getOwner() {
        return _owner;
    }

    public String getDesc() {
        return _desc;
    }
}