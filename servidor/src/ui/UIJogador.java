package src.ui;

import src.baralho.Carta;

import java.util.ArrayList;

public class UIJogador {

    public static String bemVindo() {
        return """
                +------------------------------------------------------+
                |                BEM-VINDO AO BLACKJACK                |
                +------------------------------------------------------+
                """;
    }

    public static String iniciarRodada() {
        return """
                +------------------------------------------------------+
                |               INICIANDO PROXIMA RODADA               |
                +------------------------------------------------------+
                """;
    }

    public static String apostaFechada(int aposta) {
        return """
                +------------------------------------------------------+
                |                APOSTA DA MESA DEFINIDA               |
                +------------------------------------------------------+
                ---->>>>>""" + aposta + " fichas.";
    }

    public static String apostaInicial(int aposta) {
        return "\nA aposta minima da mesa é " + aposta
                + ". Escolha uma opcao: 'Cobrir', 'Aumentar' a aposta ou 'Desistir' para sair da mesa.";
    }

    public static String pedirAposta() {
        return "\nDigite sua aposta: ";
    }


    public static String pedirCartaOuManter() {
        return "\nEscolha uma opcao: 'Comprar' uma carta ou 'Manter' a mao atual.";
    }

    public static String cartaNova(Carta novaCarta) {
        return "\nVoce recebeu: " + novaCarta;
    }

    public static String maoInicial(ArrayList<Carta> maoInicial) {
        return "\nCartas iniciais: " + maoInicial.get(0) + " e " + maoInicial.get(1);
    }

    public static String estourou(int pontuacao) {
        return "\nVoce estourou com " + pontuacao + " pontos!";
    }

    public static String manter(int pontuacao) {
        return "\nManteve com um total de: " + pontuacao;
    }

    public static String abandonou() {
        return "\nVoce abandonou a rodada!";
    }

    public static String comandoInvalido() {
        return "\nOpcao invalida!";
    }

    public static String vencedor(int pontuacao) {
        return Ansi.GREEN.getCodigo() + "\nParabens, voce venceu com " + pontuacao + " pontos! Fim da rodada!" + Ansi.RESET.getCodigo();
    }

    public static String perdedor(int pontuacao) {
        return Ansi.RED.getCodigo() + "\nO outro jogador teve uma pontuacao de " + pontuacao + ". Voce Perdeu, Fim da rodada!" + Ansi.RESET.getCodigo();
    }

    public static String vencedorAbandono() {
        return Ansi.GREEN.getCodigo() + "\nParabens, voce venceu devido aos outrosjogadores abandonarem a partida! Fim da rodada!" + Ansi.RESET.getCodigo();
    }

    public static String perdedorAbandono() {
        return Ansi.RED.getCodigo() + "\nVoce Perdeu, Fim da rodada!" + Ansi.RESET.getCodigo();
    }

    public static String empate() {
        return "\nTodos jogadores tiveram a mesma pontuacao, portando rodada Empatada!";
    }
}
