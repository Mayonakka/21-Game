
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Baralho {
    private Queue<Carta> baralho = new LinkedList<>();

    public Baralho() {
        String[] naipes = { "Copas", "Espadas", "Ouros", "Paus" };
        String[] valores = { "Ás", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Valete", "Dama", "Rei" };

        for (String naipe : naipes) {
            for (String valor : valores) {
                Carta carta = new Carta(valor, naipe);
                baralho.add(carta);
            }
        }

        embaralha();
    }

    public void embaralha() {
        List<Carta> lista = new ArrayList<>(baralho);
        // Embaralha a lista
        Collections.shuffle(lista);
        // Converte de volta para Queue
        this.baralho = new LinkedList<>(lista);
    }

    public Carta daCarta() {
        if (!baralho.isEmpty()) {
            return baralho.poll();
        }

        System.out.println("Acabaram as cartas");
        return null;
    }

    public void mostraBaralho() {
        for (Carta carta : baralho) {
            System.out.println(carta);
        }
    }
}
