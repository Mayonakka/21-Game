import java.util.ArrayList;
import java.util.List;

public class BlackJackValidation {

    public static boolean verificarEmpate(ArrayList<JogadorHandler> jogadores) {
        var pontuacao = jogadores.get(0).getPontuacao();

        return jogadores.stream()
                .allMatch(jogador -> jogador.getPontuacao() == pontuacao);
    }

    public static void verificarVencedor(ArrayList<JogadorHandler> jogadores) {
        JogadorHandler vencedor = null;
        boolean abandono = verificarSeJogadoresAbandonaram(jogadores);

        if (abandono) {
            vencedor = jogadores.stream()
                    .filter(jogador -> !jogador.isDesistiu())
                    .findFirst()
                    .orElse(null);
        }

        if (vencedor == null) {
            List<JogadorHandler> possiveisVencedores = jogadores.stream()
                    .filter(jogador -> jogador.getPontuacao() <= 21)
                    .toList();

            for (JogadorHandler jogador : possiveisVencedores) {
                if (vencedor == null) {
                    vencedor = jogador;
                } else if (jogador.getPontuacao() > vencedor.getPontuacao())
                    vencedor = jogador;
            }
        }

        JogadorHandler finalVencedor = vencedor;
        jogadores.forEach(jogador -> {
            if (jogador.equals(finalVencedor)) {
                jogador.enviarMensagem((abandono) ? UIJogador.vencedorAbandono() : UIJogador.vencedor(finalVencedor.getPontuacao()));
            } else if (!jogador.isEstourou()) {
                jogador.enviarMensagem((abandono) ? UIJogador.perdedorAbandono() : UIJogador.perdedor(finalVencedor.getPontuacao()));
            }
        });
    }

    public static boolean verificarSeJogadoresMantiveram(ArrayList<JogadorHandler> jogadores) {
        for (var jogador : jogadores) {
            if (!jogador.isManter())
                return false;
        }
        return true;
    }

    public static boolean verificarSeJogadoresEstouraram(ArrayList<JogadorHandler> jogadores) {
        long jogadoresEstouraram = jogadores.stream()
                .filter(JogadorHandler::isEstourou)
                .count();

        return jogadoresEstouraram == jogadores.size() - 1;
    }

    public static boolean verificarSeJogadoresAbandonaram(ArrayList<JogadorHandler> jogadores) {
        long jogadoresAbandonaram = jogadores.stream()
                .filter(JogadorHandler::isDesistiu)
                .count();

        return jogadoresAbandonaram == jogadores.size() - 1;
    }
}
