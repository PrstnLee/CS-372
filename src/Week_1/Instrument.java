package Week_1;

// Week 1 Lab
public abstract class Instrument implements Playable {
    private String name;

    public Instrument (String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}