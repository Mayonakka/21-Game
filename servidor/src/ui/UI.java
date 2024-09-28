package src.ui;

import src.baralho.Carta;

import java.util.ArrayList;

public class UI {

    public final static String CLEAR = "\u001B[H\u001B[2J";
    public final static String RESET = "\u001B[0m";
    public final static String GREEN = "\u001B[32m";
    public final static String RED = "\u001B[31m";

    public static String bemvindo() {
        return """
                +-------------------------------------------+
                |          BEM-VINDO AO BLACKJACK           |
                +-------------------------------------------+
                """;
    }

    public static String infoInicial(ArrayList<Carta> mao, int pontuacao) {
        return "\n" + bemvindo() +
                "\nSuas cartas iniciais sao: " + mao.toString() +
                "\nSua pontuacao inicial: " + pontuacao;
    }

    public static String pedirOuParar(){
        return "\nPressione '1' para pedir uma carta ou '0' para parar.";
    }

    public static String cartaNova(Carta novaCarta, int pontuacao) {
        return "\nVoce recebeu: " + novaCarta +
                "\nSua pontuacao atual: " + pontuacao;
    }

    public static String estourou(int pontuacao) {
        return "\n" + RED + "Voce estourou com " + pontuacao + " pontos! Fim de jogo." + RESET;
    }

    public static String parou(int pontuacao) {
        return "\nVoce parou com " + pontuacao + " pontos.";
    }

    public static String comandoInvalido() {
        return "\nComando invalido!";
    }

    public static String pressioneQualquerTecla() {
        return "\nPressione qualquer tecla para continuar!";
    }
}
