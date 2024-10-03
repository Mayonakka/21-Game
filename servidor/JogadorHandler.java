import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class JogadorHandler {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private ArrayList<Carta> mao;
    private int pontuacao;
    private boolean manter;
    private boolean desistiu;
    private boolean estourou;
    private boolean saiu;
    private int apostaAtual;

    public JogadorHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.mao = new ArrayList<>();
            this.pontuacao = 0;
            this.manter = false;
            this.desistiu = false;
            this.estourou = false;
            this.saiu = false;
            this.apostaAtual = Integer.MIN_VALUE;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void maoInicial() {
        mao.clear();
        Carta carta1 = Main.baralho.tirarCarta();
        Carta carta2 = Main.baralho.tirarCarta();
        mao.addAll(List.of(carta1, carta2));
        pontuacao += carta1.getValor().getPontuacao() + carta2.getValor().getPontuacao();

        enviarMensagem(UIJogador.maoInicial(mao));
    }

    public void receberCarta() {
        Carta carta = Main.baralho.tirarCarta();
        mao.add(carta);
        pontuacao += carta.getValor().getPontuacao();

        enviarMensagem(UIJogador.cartaNova(carta));
    }

    public void enviarMensagem(String mensagem) {
        out.println(mensagem);
    }

    public void jogar() {
        try {
            manter = false;
            boolean turnoFinalizado = false;
            while (!turnoFinalizado) {

                enviarMensagem(Ansi.CLEAR.getCodigo());
                enviarMensagem(UIJogador.pedirCartaOuManter());
                String comando = in.readLine();

                switch (comando.toLowerCase()) {
                    case "comprar": {
                        receberCarta();
                        if (pontuacao > 21) {
                            turnoFinalizado = true;
                            setEstourou(true);
                            enviarMensagem(UIJogador.estourou(this.getPontuacao()));
                        }
                        break;
                    }
                    case "manter": {
                        manter = true;
                        turnoFinalizado = true;
                        enviarMensagem(UIJogador.manter(pontuacao));
                        break;
                    }
                    default: {
                        enviarMensagem(UIJogador.comandoInvalido());
                    }
                }
            }
        } catch (IOException erro) {
            System.out.println(erro.getMessage());
        }
    }

    public void apostar(int apostaAtual) {
        try {
            boolean apostaFinalizada = false;
            while (!apostaFinalizada) {

                enviarMensagem(Ansi.CLEAR.getCodigo());
                enviarMensagem(UIJogador.apostaInicial(apostaAtual));
                String comando = in.readLine();

                switch (comando.toLowerCase()) {
                    case "cobrir": {
                        this.apostaAtual = apostaAtual;
                        apostaFinalizada = true;
                        break;
                    }
                    case "aumentar": {
                        enviarMensagem(UIJogador.pedirAposta());

                        try {
                            this.apostaAtual = Integer.parseInt(in.readLine());
                            if (this.apostaAtual <= apostaAtual) {
                                throw new Exception("A aposta deve ser maior que a aposta atual.");
                            }
                        } catch (Exception e) {
                            enviarMensagem(e.getMessage());
                            continue;
                        }
                        apostaFinalizada = true;
                        break;
                    }
                    case "desistir": {
                        desistiu = true;
                        apostaFinalizada = true;
                        enviarMensagem(UIJogador.abandonou());
                        break;
                    }
                    default: {
                        enviarMensagem(UIJogador.comandoInvalido());
                    }
                }
            }
        } catch (IOException erro) {
            System.out.println(erro.getMessage());
        }
    }

    public void sair() {
        try {
            boolean sair = false;
            while (!sair) {
                enviarMensagem(UIJogador.sairOuContinuar());
                switch (in.readLine().toLowerCase()) {
                    case "sair": {
                        saiu = true;
                        sair = true;
                        enviarMensagem(UIJogador.fim());
                        socket.close();
                        break;
                    }
                    case "continuar": {
                        saiu = false;
                        sair = true;
                        break;
                    }
                    default: {
                        enviarMensagem(UIJogador.comandoInvalido());
                    }
                }
            }
        } catch (IOException erro) {
            System.out.println(erro.getMessage());
        }
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public boolean isManter() {
        return manter;
    }

    public boolean isDesistiu() {
        return desistiu;
    }

    public boolean isEstourou() {
        return estourou;
    }

    public void setEstourou(boolean estourou) {
        this.estourou = estourou;
    }

    public int getApostaAtual() {
        return apostaAtual;
    }

    public boolean isSaiu() {
        return saiu;
    }

    public void setPontuacao(int i) {
        this.pontuacao = i;
    }

    public void setApostaAtual(int i) {
        this.apostaAtual = i;
    }
}
