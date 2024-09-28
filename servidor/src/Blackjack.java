package src;

import src.ui.UI;

import static src.Main.jogadores;

public class Blackjack {
    private int rodada;

    public static void iniciarJogo() {
        for (JogadorHandler jogador : jogadores) {
            jogador.maoInicial();
        }

        boolean rodadaFinalizada = false;
        while (!rodadaFinalizada) {
            for (JogadorHandler jogador : jogadores) {
                jogador.jogar();
                if (jogador.getPontuacao() > 21) {
                    jogador.enviarMensagem(UI.estourou(jogador.getPontuacao()));
                    rodadaFinalizada = true;
                    break;
                }
            }

            if (todosJogadoresMantiveram()) {
                determinarVencedor();
                break;
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
            if (!jogador.abandonou())
                return false;
        }
        return true;
    }

    private static boolean todosJogadoresMantiveram() {
        for (var jogador : jogadores) {
            if (jogador.manteve() == false) {
                return false;
            }
        }
        return true;
    }

    private static void determinarVencedor() {
        JogadorHandler vencedor = null;

        if (todosJogadoresMantiveram()) {
            JogadorHandler jogadorMaisPerto = null;

            for (JogadorHandler jogador : jogadores) {
                if (jogador.getPontuacao() <= 21)
                    if (jogadorMaisPerto == null)
                        jogadorMaisPerto = jogador;
                    else {
                        if (jogadorMaisPerto.getPontuacao() < jogador.getPontuacao()) {
                            jogadorMaisPerto = jogador;
                        }
                    }
            }

            vencedor = jogadorMaisPerto;
        }

        if (vencedor == null) {
            for (JogadorHandler jogador : jogadores) {
                if (jogador.getPontuacao() <= 21)
                    vencedor = jogador;
            }
        }

        for (JogadorHandler jogador : jogadores) {
            if (jogador.equals(vencedor)) {
                jogador.enviarMensagem(
                        "Parabéns, você venceu com " + vencedor.getPontuacao() + " pontos! Fim de jogo");
                continue;
            }
            jogador.enviarMensagem(
                    "O outro jogador teve uma pontuação de " + vencedor.getPontuacao()
                            + ". Você Perdeu, Fim de jogo! ");
        }

    }
}
