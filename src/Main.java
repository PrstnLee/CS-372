import Week_1.Drums;
import Week_1.Guitar;
import Week_1.Instrument;
import Week_1.Piano;

void main(String[] args) {
    // GitHub Link: https://github.com/PrstnLee/CS-372

    // Week 1 Lab
    Instrument[] instruments = new Instrument[3];

    Piano piano = new Piano();
    Guitar guitar = new Guitar();
    Drums drums = new Drums();

    instruments[0] = piano;
    instruments[1] = guitar;
    instruments[2] = drums;

    piano.Play();
    guitar.Tune();
    guitar.Play();
    drums.Play();

    for (Instrument instrument : instruments) {
        System.out.println(instrument.getName());
    }
    /* -------------------------------------------------- */



    // Week 2 Lab

    /* -------------------------------------------------- */
}
