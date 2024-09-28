package src;

import src.baralho.Baralho;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static ArrayList<JogadorHandler> jogadores = new ArrayList<>();

    public static final int MAX_JOGADORES = 2;
    public static final int PORTA = 6789;
    public static Baralho baralho;

    public static void main(String[] args) throws Exception {
        ServerSocket servidorSocket = new ServerSocket(PORTA);
        System.out.println("Servidor de Blackjack rodando na porta: " + PORTA);

        baralho = new Baralho();

        while (jogadores.size() < MAX_JOGADORES) {
            Socket socket = servidorSocket.accept();
            JogadorHandler jogador = new JogadorHandler(socket);
            jogadores.add(jogador);

            new Thread(jogador).start();
            Thread.sleep(500);

            if (jogadores.size() <= MAX_JOGADORES - 1) {
                jogador.enviarMensagem("Aguardando os outros jogadores...");
            }

            System.out.println("Jogador " + jogadores.size() + " conectado.");
        }

        Blackjack.iniciarJogo();
        System.out.println("Jogo finalizado! Encerrando conexão.");
        servidorSocket.close();
    }
}
