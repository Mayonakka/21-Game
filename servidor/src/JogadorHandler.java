package src;

import src.baralho.Carta;
import src.ui.UI;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static src.Main.baralho;

public class JogadorHandler implements Runnable {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;
    private int pontuacao;
    private ArrayList<Carta> mao;
    private boolean parou;

    public JogadorHandler(Socket socket) {
        try {
            this.socket = socket;
            this.out = new PrintWriter(socket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.pontuacao = 0;
            this.mao = new ArrayList<>();
            parou = false;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void run() {
        enviarMensagem(UI.bemvindo());
    }

    public void receberCarta() {
        Carta carta = baralho.tirarCarta();
        mao.add(carta);
        pontuacao += carta.getValor().getPontuacao();
        enviarMensagem(UI.cartaNova(carta, pontuacao));
    }

    public void jogar() {
        try {
            parou = false;
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
                    case "0": {
                        enviarMensagem(UI.parou(pontuacao));
                        parou = true;
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

    public boolean parou() {
        return parou;
    }
}
