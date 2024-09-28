package src;

import src.ui.UI;

import static src.Main.jogadores;

public class Blackjack {
    private int rodada;

    public static void iniciarJogo() throws Exception {
        boolean partidaFinalizada = false;

        while (!partidaFinalizada) {
            boolean rodadaFinalizada = false;
            Thread.sleep(4000);
            resetarPontuacoes();
            enviarMensagemParaJogadores("\nIniciando a rodada.");

            // Perguntar apostas da rodada.
            inicializarMaoDosJogadores();

            while (!rodadaFinalizada) {
                for (JogadorHandler jogador : jogadores) {
                    jogador.jogar();
                    if (jogador.estourou()) {
                        jogador.enviarMensagem(UI.estourou(jogador.getPontuacao()));
                        rodadaFinalizada = true;
                        break;
                    }
                }

                rodadaFinalizada = todosJogadoresMantiveram();
                // if (todosJogadoresMantiveram()) {
                // rodadaFinalizada = true;
                // }

                if (todosJogadoresPararam()) {
                    enviarMensagemParaJogadores("Todos pararam, Fim de jogo!");
                    rodadaFinalizada = true;
                    partidaFinalizada = true;
                }

            }

            if (verificarEmpate()) {
                enviarMensagemParaJogadores("Todos jogadores tiveram a mesma pontuação, portando rodada Empatada!");
                rodadaFinalizada = true;
            }
            determinarVencedor();
        }

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

    private static boolean verificarEmpate() {
        var pontuacao = jogadores.get(0).getPontuacao();

        for (int i = 1; i < jogadores.size(); i++) {
            if (pontuacao != jogadores.get(i).getPontuacao())
                return false;
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
                        "Parabéns, você venceu com " + vencedor.getPontuacao() + " pontos! Fim da rodada!");
                continue;
            }
            jogador.enviarMensagem(
                    "O outro jogador teve uma pontuação de " + vencedor.getPontuacao()
                            + ". Você Perdeu, Fim da rodada!");
            continue;
        }
    }

    public static void enviarMensagemParaJogadores(String mensagem) {
        for (JogadorHandler jogadorHandler : jogadores) {
            jogadorHandler.enviarMensagem(mensagem);
        }
    }

    public static void inicializarMaoDosJogadores() {
        for (JogadorHandler jogador : jogadores) {
            jogador.maoInicial();
        }
    }

    public static void resetarPontuacoes() {
        for (JogadorHandler jogadorHandler : jogadores) {
            jogadorHandler.setPontuacao(0);
        }
    }
}
