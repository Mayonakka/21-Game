package src;

import src.ui.UI;

import static src.Main.jogadores;

public class Blackjack {

    public static void iniciarJogo() {
        for (JogadorHandler jogador : jogadores) {
            jogador.receberCarta();
            jogador.receberCarta();
        }

        boolean rodadaFinalizada = false;
        while (!rodadaFinalizada){
            for (JogadorHandler jogador : jogadores) {
                jogador.jogar();
                if (jogador.getPontuacao() > 21) {
                    jogador.enviarMensagem(UI.estourou(jogador.getPontuacao()));
                    rodadaFinalizada = true;
                    break;
                }
            }

            if (todosJogadoresPararam()) {
                for (JogadorHandler jogador : jogadores)
                    jogador.enviarMensagem("Todos pararam, Fim de jogo!");
                break;
            }
        }

        determinarVencedor();
    }

    private static boolean todosJogadoresPararam() {
        for (JogadorHandler jogador : jogadores) {
            if (!jogador.parou())
                return false;
        }
        return true;
    }

    private static void determinarVencedor() {
        JogadorHandler vencedor = null;
        for (JogadorHandler jogador : jogadores) {
            if (jogador.getPontuacao() <= 21)
                vencedor = jogador;
        }

        if (vencedor != null) {
            for (JogadorHandler jogador : jogadores) {
                if (!jogador.equals(vencedor))
                    jogador.enviarMensagem("Parabéns, você venceu com " + vencedor.getPontuacao() + " pontos! Fim de jogo");
                jogador.enviarMensagem("Voce Perdeu, Fim de jogo!");
            }
        }
    }
}
