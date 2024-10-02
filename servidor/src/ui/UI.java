package src.ui;

import src.baralho.Carta;

import java.util.ArrayList;

public class UI {

    public final static String CLEAR = "\u001B[2J\u001B[H";
    public final static String RESET = "\u001B[0m";
    public final static String GREEN = "\u001B[32m";
    public final static String RED = "\u001B[31m";

    public static String bemVindo() {
        return """
                +-------------------------------------------+
                |          BEM-VINDO AO BLACKJACK           |
                +-------------------------------------------+
                """;
    }

    public static String iniciarRodada() {
        return """
                +-------------------------------------------+
                |        INICIANDO PRÓXIMA RODADA           |
                +-------------------------------------------+
                """;
    }

    public static String apostaInicial(int aposta) {
        return "\nA aposta mínima da mesa é " + aposta
                + ". Escolha uma opção: 'Cobrir', 'Aumentar' a aposta ou 'Desistir' para sair da mesa.";
    }

    public static String pedirAposta() {
        return "\nDigite sua aposta: ";
    }

    public static String infoInicial(ArrayList<Carta> mao) {
        return "\nSuas cartas iniciais sao: " + mao.toString();
    }

    public static String pedirCartaOuManter() {
        return "\nEscolha uma opção: 'Comprar' uma carta ou 'Manter' a mão atual.";
    }

    public static String cartaNova(Carta novaCarta) {
        return "\nVocê recebeu: " + novaCarta;
    }

    public static String maoInicial(ArrayList<Carta> maoInicial) {
        return "\nCartas iniciais: " + maoInicial.get(0) + " e " + maoInicial.get(1);
    }

    public static String estourou(int pontuacao) {
        return "\n" + RED + "Voce estourou com " + pontuacao + " pontos!" + RESET;
    }

    public static String manter(int pontuacao) {
        return "\nManteve com um total de: " + pontuacao;
    }

    public static String parou(int pontuacao) {
        return "\nVoce parou com " + pontuacao + " pontos.";
    }

    public static String comandoInvalido() {
        return "\nOpção inválida!";
    }

    public static String pressioneQualquerTecla() {
        return "\nPressione qualquer tecla para continuar!";
    }

}
