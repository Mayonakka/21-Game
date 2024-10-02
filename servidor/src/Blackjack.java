package src;

import src.ui.UI;

import static src.Main.jogadores;

public class Blackjack {
    private static int APOSTA_MINIMA = 5;
    private static boolean PARTIDA_FINALIZADA = false;
    private static boolean RODADA_FINALIZADA = false;
    private static boolean APOSTA_DEFINIDA = false;

    public static void iniciarJogo() throws Exception {
        while (!PARTIDA_FINALIZADA) {
            RODADA_FINALIZADA = false;
            resetarPontuacoes();
            APOSTA_MINIMA = 5;
            enviarMensagemParaJogadores(UI.iniciarRodada());

            while (!RODADA_FINALIZADA) {
                // Perguntar apostas da rodada.
                APOSTA_DEFINIDA = false;

                while (!APOSTA_DEFINIDA) {
                    for (JogadorHandler jogadorHandler : jogadores) {
                        if (jogadorHandler.getApostaAtual() != APOSTA_MINIMA) {
                            jogadorHandler.apostar(APOSTA_MINIMA);

                            if (jogadorHandler.abandonou()) {
                                removerJogador(jogadorHandler);
                            }

                            if (jogadorHandler.getApostaAtual() > APOSTA_MINIMA)
                                APOSTA_MINIMA = jogadorHandler.getApostaAtual();
                        }
                    }

                    for (JogadorHandler jogadorHandler : jogadores) {
                        if (jogadorHandler.getApostaAtual() != APOSTA_MINIMA) {
                            break;
                        }
                        APOSTA_DEFINIDA = true;
                    }
                }
                enviarMensagemParaJogadores("Aposta da mesa definida: " + APOSTA_MINIMA + " fichas.");
                inicializarMaoDosJogadores();

                for (JogadorHandler jogador : jogadores) {
                    if (todosJogadoresEstouraram()) {
                        RODADA_FINALIZADA = true;
                        break;
                    }

                    jogador.jogar();
                }

                if (!RODADA_FINALIZADA)
                    RODADA_FINALIZADA = todosJogadoresMantiveram();
            }

            if (verificarEmpate()) {
                enviarMensagemParaJogadores("Todos jogadores tiveram a mesma pontuação, portando rodada Empatada!");
                RODADA_FINALIZADA = true;
            }

            determinarVencedor();
            Thread.sleep(4000);
        }
    }

    private static boolean todosJogadoresEstouraram() {
        int qntEstouraram = 0;

        for (JogadorHandler jogadorHandler : jogadores) {
            if (jogadorHandler.isEstourou())
                qntEstouraram++;
        }

        return jogadores.size() - 1 == qntEstouraram;
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
            if (!jogador.isEstourou()) {
                jogador.enviarMensagem(
                        "O outro jogador teve uma pontuação de " + vencedor.getPontuacao()
                                + ". Você Perdeu, Fim da rodada!");
                continue;
            }
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
            jogadorHandler.setApostaAtual(0);
        }
    }

    public static void removerJogador(JogadorHandler jogador) {
        jogador.enviarMensagem("Você desistiu.");
        jogadores.remove(jogador);

        if (jogadores.size() == 1) {
            jogadores.get(0).enviarMensagem("Os outros jogadores desistiram, portanto você venceu!");
            APOSTA_DEFINIDA = true;
            RODADA_FINALIZADA = true;
        }
    }
}
