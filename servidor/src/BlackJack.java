package src;

import java.util.ArrayList;

import src.baralho.Baralho;
import src.baralho.Carta;

public class BlackJack {
    Baralho baralho = new Baralho();

    public static boolean jogoFinalizado = false;
    int valorMinimo = 5;

    public ArrayList<Carta> getMaoInicial() {
        var list = new ArrayList<Carta>();

        list.add(baralho.daCarta());
        list.add(baralho.daCarta());

        return list;
    }

}
