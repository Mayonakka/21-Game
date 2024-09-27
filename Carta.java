
public class Carta {
    private String nome, naipe;

    public Carta(String nome, String naipe) {
        this.nome = nome;
        this.naipe = naipe;
    }

    public String getNome() {
        return nome;
    }

    public String getNaipe() {
        return naipe;
    }

    public void setNome() {
        this.nome = nome;
    }

    public void setNaipe() {
        this.naipe = naipe;
    }

    @Override
    public String toString() {
        return "[Carta: " + nome + ", Naipe: " + naipe + ".]";
    }
}