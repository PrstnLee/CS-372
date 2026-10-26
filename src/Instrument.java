public abstract class Instrument implements Playable {
    private String name;

    public Instrument (String name) {
        this.name = name;
    }

    public String GetName() {
        return this.name;
    }
}