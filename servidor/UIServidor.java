import java.util.List;

public class UIServidor {

    public static String iniciar(int porta) {
        return "\nServidor de Blackjack rodando na porta: " + porta;
    }

    public static String conectar(List<JogadorHandler> jogadores) {
        return "\nJogador " + jogadores.size() + " conectado.";
    }

    public static String finalizar() {
        return "\nJogo finalizado! Encerrando conexao.";
    }

    public static String aguardando() {
        return "Aguardando os outros jogadores...";
    }
}
