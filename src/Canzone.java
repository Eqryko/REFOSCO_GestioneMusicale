public class Canzone {

    private int id;
    private String titolo;
    private int durata;
    private int annoPubblicazione;
    private Artista artista;


    public Canzone(int id, String titolo, int durata, int annoPubblicazione) {
        this.id = id;
        this.titolo = titolo;
        this.durata = durata;
        this.annoPubblicazione = annoPubblicazione;
    }

    @Override
    public String toString(){
        return id + "\t" + titolo + "\t" + durata + "\t" + annoPubblicazione + "\t" + artista.toString();
    }

}