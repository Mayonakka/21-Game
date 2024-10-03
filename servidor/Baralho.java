import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Baralho {

    private Queue<Carta> baralho;

    public Baralho() {
        baralho = new LinkedList<>();
        for (Naipe naipe : Naipe.values()) {
            for (Valor valor : Valor.values())
                baralho.add(new Carta(valor, naipe));
        }
        embaralha();
    }

    public void embaralha() {
        List<Carta> lista = new ArrayList<>(baralho);
        Collections.shuffle(lista);
        this.baralho = new LinkedList<>(lista);
    }

    public Carta tirarCarta() {
        if (!baralho.isEmpty())
            return baralho.poll();
        System.out.println("Acabaram as cartas");
        return null;
    }

    public void mostraBaralho() {
        for (Carta carta : baralho)
            System.out.println(carta);
    }
}
