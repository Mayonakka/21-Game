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
        enviarMensagem(UI.bemvindo());
    }

    public void maoInicial() {
        Carta carta1 = baralho.tirarCarta();
        Carta carta2 = baralho.tirarCarta();

        mao.addAll(List.of(carta1, carta2));

        pontuacao += carta1.getValor().getPontuacao() + carta2.getValor().getPontuacao();

        enviarMensagem(UI.maoInicial(mao, pontuacao));
    }

    public void receberCarta() {
        Carta carta = baralho.tirarCarta();
        mao.add(carta);
        pontuacao += carta.getValor().getPontuacao();
        enviarMensagem(UI.cartaNova(carta, pontuacao));
    }

    public void jogar() {
        try {
            manter = false;
            boolean rodadaFinalizada = false;
            while (!rodadaFinalizada) {

                enviarMensagem(UI.CLEAR);
                enviarMensagem(UI.pedirOuParar());
                String comando = in.readLine();

                switch (comando) {
                    case "1": {
                        receberCarta();
                        if (pontuacao > 21)
                            rodadaFinalizada = true;
                        break;
                    }
                    case "2": {
                        enviarMensagem(UI.manter(pontuacao));
                        manter = true;
                        rodadaFinalizada = true;
                        break;
                    }
                    case "0": {
                        enviarMensagem(UI.parou(pontuacao));
                        abandonou = true;
                        rodadaFinalizada = true;
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

    public boolean abandonou() {
        return abandonou;
    }

    public boolean manteve() {
        return manter;
    }
}
