package net.isogen;

/**
 * Created by Ian Murphy on 8/29/2016.
 */
public abstract class Generator {
    protected int _level;
    protected String _username;
    protected String _outputFile;
    public void setLevel(int level){
        _level = level;
    }
    public void setUsername(String username){
        _username = username;
    }
    public abstract void writeOutput();
    public abstract void generate();
}