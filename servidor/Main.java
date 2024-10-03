import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

public class Main {

    public static ArrayList<JogadorHandler> jogadores = new ArrayList<>();

    private static final int MAX_JOGADORES = 2;
    private static final int PORTA = 6789;
    public static Baralho baralho;

    public static void main(String[] args) throws Exception {
        ServerSocket servidorSocket = new ServerSocket(PORTA);
        System.out.println(UIServidor.iniciar(PORTA));

        baralho = new Baralho();
        while (true) {
            Socket socket = servidorSocket.accept();
            JogadorHandler jogador = new JogadorHandler(socket);
            jogadores.add(jogador);

            jogador.enviarMensagem(UIJogador.bemVindo());
            System.out.println(UIServidor.conectar(jogadores));

            if (jogadores.size() == MAX_JOGADORES)
                break;
            jogador.enviarMensagem(UIServidor.aguardando());
        }

        Blackjack.iniciarJogo(jogadores, baralho);

        System.out.println(UIServidor.finalizar());
        servidorSocket.close();
    }
}
