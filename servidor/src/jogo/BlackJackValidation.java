package src.jogo;

import src.JogadorHandler;
import src.ui.UIJogador;

import java.util.Comparator;
import java.util.List;

import static src.Main.jogadores;

public class BlackJackValidation {

    public static boolean verificarEmpate() {
        var pontuacao = jogadores.getFirst().getPontuacao();

        return jogadores.stream()
                .allMatch(jogador -> jogador.getPontuacao() == pontuacao);
    }

    public static void verificarVencedor() {
        JogadorHandler vencedor = null;
        boolean abandono = verificarSeJogadoresAbandonaram();

        if (abandono) {
            vencedor = jogadores.stream()
                    .filter(JogadorHandler::isAbandonou)
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

    public static boolean verificarSeJogadoresMantiveram() {
        for (var jogador : jogadores) {
            if (!jogador.isManter())
                return false;
        }
        return true;
    }

    public static boolean verificarSeJogadoresEstouraram() {
        long jogadoresEstouraram = jogadores.stream()
                .filter(JogadorHandler::isEstourou)
                .count();

        return jogadoresEstouraram == jogadores.size() - 1;
    }

    public static boolean verificarSeJogadoresAbandonaram() {
        long jogadoresAbandonaram = jogadores.stream()
                .filter(JogadorHandler::isAbandonou)
                .count();

        return jogadoresAbandonaram == jogadores.size() - 1;
    }
}
