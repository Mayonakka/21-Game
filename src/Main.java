package src;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

import src.baralho.Carta;

public class Main {
    public static void main(String[] args) throws Exception {
        BlackJack blackJack = new BlackJack();
        ServerSocket servidorConexao = new ServerSocket(6789);

        System.out.println("Aguardando jogador 1...");
        Socket jogador1 = servidorConexao.accept();

        System.out.println("Aguardando jogador 2...");
        Socket jogador2 = servidorConexao.accept();

        Servidor server = new Servidor(servidorConexao, jogador1, jogador2);

        var aguardandoInicio = true;

        while (aguardandoInicio) {
            if (jogador1.isConnected() && jogador2.isConnected()) {
                var cartasJogador1 = blackJack.getMaoInicial();
                var cartasJogador2 = blackJack.getMaoInicial();
                server.iniciar(cartasJogador1, cartasJogador2);

                blackJack.jogoFinalizado = true;

                aguardandoInicio = false;
            }
        }

        while (!blackJack.jogoFinalizado) {
            // Lógica do jogo, rodadas
        }
    }
}