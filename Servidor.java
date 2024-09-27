
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {
    int totalFichas = 0;
    ServerSocket servidor;
    Socket jogador1;
    Socket jogador2;

    public Servidor(ServerSocket servidor, Socket jogador1, Socket jogador2) {
        this.servidor = servidor;
        this.jogador1 = jogador1;
        this.jogador2 = jogador2;
    }

    public String mensagemDeInicio(int id, ArrayList<Carta> cartas) {
        if (id == 1) {
            try {
                // Preparar os streams para enviar e receber dados
                BufferedReader doJogador = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
                DataOutputStream paraJogador = new DataOutputStream(jogador1.getOutputStream());

                // Criar a mensagem para enviar ao jogador
                StringBuilder mensagem = new StringBuilder();
                mensagem.append("Bem vindo ao BlackJack! Suas cartas são: ");

                for (Carta carta : cartas) {
                    mensagem.append(carta).append(" "); // Adiciona um espaço entre as cartas
                }

                // Enviar a mensagem ao jogador
                paraJogador.writeBytes(mensagem.toString() + "\n");

                // Receber a resposta do jogador
                String resposta = doJogador.readLine();

                // Fechar os streams (se a conexão não for longa)
                doJogador.close();
                paraJogador.close();

                return resposta; // Retorna a resposta do jogador

            } catch (IOException e) {
                e.printStackTrace(); // Lida com problemas de I/O
                return "Erro ao comunicar com o jogador.";
            }
        }

        return "";
    }

    public void enviaCarta(int id, Carta carta) throws Exception {
        if (id == 1) {
            BufferedReader doJogador = new BufferedReader(new InputStreamReader(jogador1.getInputStream()));
            DataOutputStream paraJogador = new DataOutputStream(jogador1.getOutputStream());

        }
    }

    public void iniciarJogo() {
        // envia pro jogador o mensagem de inicio

        // envia pro jogador 2 o mensagem de inicio
    }
}
