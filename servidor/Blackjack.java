import java.util.List;

public class Blackjack {
    private static final int APOSTA_MINIMA = 5;
    private static int apostaAtual;

    public static void iniciarJogo() throws Exception {
        boolean rodadaFinalizada = false;
        apostaAtual = APOSTA_MINIMA;
        resetarPontuacoes();
        do {
            boolean apostaFinalizada = false;
            do {
                apostar(Main.jogadores);

                for (JogadorHandler jogadorHandler : Main.jogadores) {
                    if (jogadorHandler.getApostaAtual() != apostaAtual && !BlackJackValidation.verificarSeJogadoresAbandonaram()){
                        apostaFinalizada = false;
                        break;
                    }
                    apostaFinalizada = true;
                }

            } while (!apostaFinalizada);
            if(BlackJackValidation.verificarSeJogadoresAbandonaram())
                break;

            enviarMensagemParaJogadores(UIJogador.apostaFechada(apostaAtual));
            iniciarMaoDosJogadores();

            for (JogadorHandler jogador : Main.jogadores) {
                if (BlackJackValidation.verificarSeJogadoresEstouraram()) {
                    rodadaFinalizada = true;
                    break;
                }
                jogador.jogar();
            }

            if (!rodadaFinalizada)
                rodadaFinalizada = BlackJackValidation.verificarSeJogadoresMantiveram();

        } while (!rodadaFinalizada);

        if (BlackJackValidation.verificarEmpate() && !BlackJackValidation.verificarSeJogadoresAbandonaram())
            enviarMensagemParaJogadores(UIJogador.empate());

        BlackJackValidation.verificarVencedor();
    }

    public static void enviarMensagemParaJogadores(String mensagem) {
        Main.jogadores.forEach(jogador -> jogador.enviarMensagem(mensagem));
    }

    public static void iniciarMaoDosJogadores() {
        Main.jogadores.forEach(JogadorHandler::maoInicial);
    }

    public static void resetarPontuacoes() {
        for (JogadorHandler jogadorHandler : Main.jogadores) {
            jogadorHandler.setPontuacao(0);
            jogadorHandler.setApostaAtual(0);
            jogadorHandler.setEstourou(false);
        }
    }

    public static void apostar(List<JogadorHandler> jogadores) {
        for (JogadorHandler jogador : jogadores) {
            if (!BlackJackValidation.verificarSeJogadoresAbandonaram() && jogador.getApostaAtual() != apostaAtual && !jogador.isDesistiu()) {
                jogador.apostar(apostaAtual);
                apostaAtual = Math.max(jogador.getApostaAtual(), apostaAtual);
            }
        }
    }
}
