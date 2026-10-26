// Week 1 Lab
public class Guitar extends Instrument {
    public Guitar () {
        super("Guitar");
    }

    @Override
    public void Play() {
        System.out.println("Strumming Guitar");
    }

    public void Tune() {
        System.out.println("Tuning Guitar");
    }
}
