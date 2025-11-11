public class Artista {
    private int id;
    private String nome;
    private String paese;
    private String genere;
    private Canzone[] canzoni;

    // --- COSTRUTTORI ---
    public Artista() {}

    public Artista(int id, String nome, String paese, String genere) {
        this.id = id;
        this.nome = nome;
        this.paese = paese;
        this.genere = genere;
    }

    public Artista(String nome, String paese, String genere) {
        this.nome = nome;
        this.paese = paese;
        this.genere = genere;
    }

    // --- GETTER E SETTER ---
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPaese() {
        return paese;
    }

    public void setPaese(String paese) {
        this.paese = paese;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public Canzone[] getCanzoni() {
        return canzoni;
    }

    public void setCanzoni(Canzone[] canzoni) {
        this.canzoni = canzoni;
    }

    // --- METODO TO STRING ---
    @Override
    public String toString() {
        return id + "\t" + nome + "\t" + paese + "\t" + genere + "\n";
    }
}
