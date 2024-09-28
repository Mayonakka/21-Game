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

    public JogadorHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.mao = new ArrayList<>();
        this.pontuacao = 0;
    }

    @Override
    public void run() {
        try {

            for (int i = 0; i < 2; i++) {
                Carta carta = baralho.tirarCarta();
                mao.add(carta);
                pontuacao += carta.getValor().getPontuacao();
            }

            out.println(UI.infoInicial(mao, pontuacao));
            out.println(UI.pressioneQualquerTecla());
            in.readLine();

            // Loop do jogo
            boolean jogoFinalizado = false;
            while (!jogoFinalizado) {
                out.println(UI.CLEAR);
                out.println(UI.pedirOuParar());
                String comando = in.readLine();
                switch (comando) {
                    case "1":
                        Carta novaCarta = baralho.tirarCarta();
                        mao.add(novaCarta);
                        pontuacao += novaCarta.getValor().getPontuacao();
                        out.println(UI.cartaNova(novaCarta, pontuacao));

                        if (pontuacao > 21) {
                            out.println(UI.estourou(pontuacao));
                            jogoFinalizado = true;
                            break;
                        }
                    case "0": {
                        out.println(UI.parou(pontuacao));
                        jogoFinalizado = true;
                        break;
                    }
                    default: {
                        out.println(UI.comandoInvalido());
                    }
                }
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
