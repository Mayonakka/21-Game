import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

public class Main {

    public static ArrayList<JogadorHandler> jogadores = new ArrayList<>();

    public static final int MAX_JOGADORES = 2;
    public static final int PORTA = 6789;
    public static Baralho baralho;

    public static void main(String[] args) throws Exception {
        ServerSocket servidorSocket = new ServerSocket(PORTA);
        System.out.println(UIServidor.iniciar(PORTA));

        do {
            baralho = new Baralho();
            while (jogadores.size() < MAX_JOGADORES) {
                Socket socket = servidorSocket.accept();
                JogadorHandler jogador = new JogadorHandler(socket);
                jogadores.add(jogador);
                jogador.enviarMensagem(UIJogador.bemVindo());
                if (jogadores.size() < MAX_JOGADORES)
                    jogador.enviarMensagem(UIServidor.aguardando());

                System.out.println(UIServidor.conectar(jogadores));
            }

            Blackjack.iniciarJogo();
            jogadores.forEach(JogadorHandler::sair);
        } while (!verificarSeJogadoresSairam());

        System.out.println(UIServidor.finalizar());
        servidorSocket.close();
    }

    public static boolean verificarSeJogadoresSairam() {
        jogadores = jogadores
                .stream()
                .filter(jogador -> !jogador.isSaiu())
                .collect(toCollection(ArrayList::new));

        return jogadores.isEmpty();
    }
}
