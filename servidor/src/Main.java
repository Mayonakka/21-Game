package src;

import src.baralho.Baralho;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Main {

    public static final int PORTA = 6789;
    public static ArrayList<JogadorHandler> jogadores = new ArrayList<>();
    public static Baralho baralho;

    public static void main(String[] args) throws IOException {

        ServerSocket servidorSocket = new ServerSocket(PORTA);
        System.out.println("Servidor de Blackjack rodando na porta: " + PORTA);

        baralho = new Baralho();

        while (true) {
            Socket socket = servidorSocket.accept();
            JogadorHandler jogador = new JogadorHandler(socket);
            jogadores.add(jogador);
            new Thread(jogador).start();
        }

    }
}
