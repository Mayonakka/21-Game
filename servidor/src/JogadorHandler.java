package src;

import src.baralho.Carta;
import src.ui.UI;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import static src.Main.baralho;

public class JogadorHandler implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int pontuacao;
    private ArrayList<Carta> mao;
    private boolean manter;
    private boolean abandonou;
    private boolean estourou;

    private int apostaAtual;

    public JogadorHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.pontuacao = 0;
            this.mao = new ArrayList<>();
            manter = false;
            abandonou = false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        enviarMensagem(UI.bemVindo());
    }

    public void maoInicial() {
        Carta carta1 = baralho.tirarCarta();
        Carta carta2 = baralho.tirarCarta();

        mao.addAll(List.of(carta1, carta2));

        pontuacao += carta1.getValor().getPontuacao() + carta2.getValor().getPontuacao();

        enviarMensagem(UI.maoInicial(mao));
    }

    public void receberCarta() {
        Carta carta = baralho.tirarCarta();
        mao.add(carta);
        pontuacao += carta.getValor().getPontuacao();

        enviarMensagem(UI.cartaNova(carta));
    }

    public void jogar() {
        try {
            manter = false;
            boolean turnoFinalizado = false;
            while (!turnoFinalizado) {

                enviarMensagem(UI.CLEAR);
                enviarMensagem(UI.pedirCartaOuManter());
                String comando = in.readLine();

                switch (comando.toLowerCase()) {
                    case "comprar": {
                        receberCarta();
                        if (pontuacao > 21) {
                            turnoFinalizado = true;
                            setEstourou(true);
                            enviarMensagem(UI.estourou(this.getPontuacao()));
                        }
                        break;
                    }
                    case "manter": {
                        enviarMensagem(UI.manter(pontuacao));
                        manter = true;
                        turnoFinalizado = true;
                        break;
                    }
                    default: {
                        enviarMensagem(UI.comandoInvalido());
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

                enviarMensagem(UI.CLEAR);
                enviarMensagem(UI.apostaInicial(apostaAtual));
                String comando = in.readLine();

                switch (comando.toLowerCase()) {
                    case "cobrir": {
                        this.apostaAtual = apostaAtual;
                        apostaFinalizada = true;
                        break;
                    }
                    case "aumentar": {
                        enviarMensagem(UI.pedirAposta());

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
                        abandonou = true;
                        apostaFinalizada = true;
                        break;
                    }
                    default: {
                        enviarMensagem(UI.comandoInvalido());
                    }
                }
            }
        } catch (IOException erro) {
            System.out.println(erro.getMessage());
        }
    }

    public void enviarMensagem(String mensagem) {
        out.println(mensagem);
    }

    public int getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(int valor) {
        pontuacao = valor;
    }

    public int getApostaAtual() {
        return apostaAtual;
    }

    public void setApostaAtual(int apostaAtual) {
        this.apostaAtual = apostaAtual;
    }

    public boolean abandonou() {
        return abandonou;
    }

    public boolean isEstourou() {
        return pontuacao > 21;
    }

    public void setEstourou(boolean estourou) {
        this.estourou = estourou;
    }

    public boolean manteve() {
        return manter;
    }
}
