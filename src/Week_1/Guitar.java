package Week_1;

// Week 1 Lab
public class Guitar extends Instrument {
    public Guitar () {
        super("Week_1.Guitar");
    }

    @Override
    public void Play() {
        System.out.println("Strumming Week_1.Guitar");
    }

    public void Tune() {
        System.out.println("Tuning Week_1.Guitar");
    }
}
