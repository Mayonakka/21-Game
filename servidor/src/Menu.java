package src;

import java.util.ArrayList;

import src.baralho.Carta;

public class Menu {
    public String iniciar(ArrayList<Carta> cartas) {
        System.out.println();
        StringBuilder retorno = new StringBuilder();

        retorno.append("Bem vindo ao BlackJack! ");

        for (Carta carta : cartas) {
            retorno.append(carta);
        }

        return retorno.toString();
    }

    public void mostraMenu() {
        System.out.println("Opções: ");
        System.out.println("1: Comprar carta");
        System.out.println("2: Manter");
        System.out.println("3: Desistir");
    }

    public void apostar() {
        System.out.println("Digite o valor da sua aposta: ");
    }
}
