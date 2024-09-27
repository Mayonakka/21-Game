
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        Menu menu = new Menu();

        Scanner scanner = new Scanner(System.in);

        BlackJack blackJack = new BlackJack();

        ServerSocket servidorConexao = new ServerSocket(6789);

        Socket jogador1 = servidorConexao.accept();
        Socket jogador2 = servidorConexao.accept();

        Servidor server = new Servidor(servidorConexao, jogador1, jogador2);

        server.iniciarJogo();

        while (!blackJack.jogoFinalizado) {
            // Verifica se o jogador 1 está conectado antes de continuar
            if (jogador1.isConnected()) {
                // Jogador 1 está conectado, agora prepara as cartas
                ArrayList<Carta> mao1 = new ArrayList<>();
                mao1.add(blackJack.baralho.daCarta());
                mao1.add(blackJack.baralho.daCarta());

                ArrayList<Carta> mao2 = new ArrayList<>();
                mao2.add(blackJack.baralho.daCarta());
                mao2.add(blackJack.baralho.daCarta());

                // Envia a mensagem ao jogador 1
                server.mensagemDeInicio(1, mao1);

                // Caso precise de uma pausa ou condição para finalizar a iteração
                String teste = scanner.next(); // Exemplo de entrada de teste, pode ser omitido

            } else {
                // Se não conectado, informa e espera para a próxima verificação
                System.out.println("Jogador 1 não está conectado. Tentando novamente...");
                try {
                    Thread.sleep(1000); // Pausa de 1 segundo antes de verificar novamente
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}