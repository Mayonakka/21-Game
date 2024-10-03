import java.util.List;

public class BlackJackValidation {

    public static boolean verificarEmpate() {
        var pontuacao = Main.jogadores.get(0).getPontuacao();

        return Main.jogadores.stream()
                .allMatch(jogador -> jogador.getPontuacao() == pontuacao);
    }

    public static void verificarVencedor() {
        JogadorHandler vencedor = null;
        boolean abandono = verificarSeJogadoresAbandonaram();

        if (abandono) {
            vencedor = Main.jogadores.stream()
                    .filter(jogador -> !jogador.isDesistiu())
                    .findFirst()
                    .orElse(null);
        }

        if (vencedor == null) {
            List<JogadorHandler> possiveisVencedores = Main.jogadores.stream()
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
        Main.jogadores.forEach(jogador -> {
            if (jogador.equals(finalVencedor)) {
                jogador.enviarMensagem((abandono) ? UIJogador.vencedorAbandono() : UIJogador.vencedor(finalVencedor.getPontuacao()));
            } else if (!jogador.isEstourou()) {
                jogador.enviarMensagem((abandono) ? UIJogador.perdedorAbandono() : UIJogador.perdedor(finalVencedor.getPontuacao()));
            }
        });
    }

    public static boolean verificarSeJogadoresMantiveram() {
        for (var jogador : Main.jogadores) {
            if (!jogador.isManter())
                return false;
        }
        return true;
    }

    public static boolean verificarSeJogadoresEstouraram() {
        long jogadoresEstouraram = Main.jogadores.stream()
                .filter(JogadorHandler::isEstourou)
                .count();

        return jogadoresEstouraram == Main.jogadores.size() - 1;
    }

    public static boolean verificarSeJogadoresAbandonaram() {
        long jogadoresAbandonaram = Main.jogadores.stream()
                .filter(JogadorHandler::isDesistiu)
                .count();

        return jogadoresAbandonaram == Main.jogadores.size() - 1;
    }
}
