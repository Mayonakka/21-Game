package src.jogo;

import src.JogadorHandler;
import src.ui.UIJogador;

import java.util.List;

import static src.Main.jogadores;
import static src.jogo.BlackJackValidation.verificarSeJogadoresAbandonaram;

public class Blackjack {
    private static final int APOSTA_MINIMA = 5;

    public static void iniciarJogo() throws Exception {
        boolean rodadaFinalizada = false;
        int apostaAtual = APOSTA_MINIMA;
        do {
            boolean apostaFinalizada = false;
            do {
                apostaAtual = apostar(jogadores, apostaAtual);

                for (JogadorHandler jogadorHandler : jogadores) {
                    if (jogadorHandler.getApostaAtual() != apostaAtual && !verificarSeJogadoresAbandonaram()){
                        apostaFinalizada = false;
                        break;
                    }
                    apostaFinalizada = true;
                }

            } while (!apostaFinalizada);
            if(verificarSeJogadoresAbandonaram())
                break;

            enviarMensagemParaJogadores(UIJogador.apostaFechada(apostaAtual));
            iniciarMaoDosJogadores();

            for (JogadorHandler jogador : jogadores) {
                if (BlackJackValidation.verificarSeJogadoresEstouraram()) {
                    rodadaFinalizada = true;
                    break;
                }
                jogador.jogar();
            }

            if (!rodadaFinalizada)
                rodadaFinalizada = BlackJackValidation.verificarSeJogadoresMantiveram();

        } while (!rodadaFinalizada);

        if (BlackJackValidation.verificarEmpate() && !verificarSeJogadoresAbandonaram())
            enviarMensagemParaJogadores(UIJogador.empate());

        BlackJackValidation.verificarVencedor();
    }

    public static void enviarMensagemParaJogadores(String mensagem) {
        jogadores.forEach(jogador -> jogador.enviarMensagem(mensagem));
    }

    public static void iniciarMaoDosJogadores() {
        jogadores.forEach(JogadorHandler::maoInicial);
    }

    public static int apostar(List<JogadorHandler> jogadores, int apostaAtual) {
        int aposta = apostaAtual;
        for (JogadorHandler jogador : jogadores) {
            if (!verificarSeJogadoresAbandonaram() && jogador.getApostaAtual() != apostaAtual && !jogador.isAbandonou()) {
                jogador.apostar(apostaAtual);
                aposta = Math.max(jogador.getApostaAtual(), aposta);
            }
        }
        return aposta;
    }
}
