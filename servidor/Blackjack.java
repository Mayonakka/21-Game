import java.util.ArrayList;

public class Blackjack {
    private static final int APOSTA_MINIMA = 5;
    private static int apostaAtual;

    public static void iniciarJogo(ArrayList<JogadorHandler> jogadores, Baralho baralho) {
        boolean rodadaFinalizada = false;
        apostaAtual = APOSTA_MINIMA;
        resetarPontuacoes(jogadores);
        do {
            boolean apostaFinalizada = false;
            do {
                apostar(jogadores);

                for (JogadorHandler jogadorHandler : jogadores) {
                    if (jogadorHandler.getApostaAtual() != apostaAtual && !BlackJackValidation.verificarSeJogadoresAbandonaram(jogadores)){
                        apostaFinalizada = false;
                        break;
                    }
                    apostaFinalizada = true;
                }

            } while (!apostaFinalizada);
            if(BlackJackValidation.verificarSeJogadoresAbandonaram(jogadores))
                break;

            enviarMensagemParaJogadores(UIJogador.apostaFechada(apostaAtual), jogadores);
            iniciarMaoDosJogadores(jogadores, baralho);

            for (JogadorHandler jogador : jogadores) {
                if (BlackJackValidation.verificarSeJogadoresEstouraram(jogadores)) {
                    rodadaFinalizada = true;
                    break;
                }
                jogador.jogar(baralho);
            }

            if (!rodadaFinalizada)
                rodadaFinalizada = BlackJackValidation.verificarSeJogadoresMantiveram(jogadores);

        } while (!rodadaFinalizada);

        if (BlackJackValidation.verificarEmpate(jogadores) && !BlackJackValidation.verificarSeJogadoresAbandonaram(jogadores))
            enviarMensagemParaJogadores(UIJogador.empate(), jogadores);

        BlackJackValidation.verificarVencedor(jogadores);
    }

    public static void enviarMensagemParaJogadores(String mensagem, ArrayList<JogadorHandler> jogadores) {
        jogadores.forEach(jogador -> jogador.enviarMensagem(mensagem));
    }

    public static void iniciarMaoDosJogadores(ArrayList<JogadorHandler> jogadores, Baralho baralho) {
        jogadores.forEach(jogador -> jogador.maoInicial(baralho));
    }

    public static void resetarPontuacoes(ArrayList<JogadorHandler> jogadores) {
        for (JogadorHandler jogadorHandler : jogadores) {
            jogadorHandler.setPontuacao(0);
            jogadorHandler.setApostaAtual(0);
            jogadorHandler.setAbandonou(false);
            jogadorHandler.setManter(false);
            jogadorHandler.setEstourou(false);
        }
    }

    public static void apostar(ArrayList<JogadorHandler> jogadores) {
        for (JogadorHandler jogador : jogadores) {
            if (!BlackJackValidation.verificarSeJogadoresAbandonaram(jogadores) && jogador.getApostaAtual() != apostaAtual && !jogador.isDesistiu()) {
                jogador.apostar(apostaAtual);
                apostaAtual = Math.max(jogador.getApostaAtual(), apostaAtual);
            }
        }
    }
}
