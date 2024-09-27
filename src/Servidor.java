package src;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import src.baralho.Carta;

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

    public void iniciar(ArrayList<Carta> cartasJogador1, ArrayList<Carta> cartasJogador2) {
        enviarMensagemDeInicio(jogador1, cartasJogador1);
        enviarMensagemDeInicio(jogador2, cartasJogador2);
    }

    public void enviarMensagemDeInicio(Socket jogador, ArrayList<Carta> cartas) {
        try {
            // Preparar os streams para enviar e receber dados
            BufferedReader doJogador = new BufferedReader(new InputStreamReader(jogador.getInputStream()));
            DataOutputStream paraJogador = new DataOutputStream(jogador.getOutputStream());

            // Criar a mensagem para enviar ao jogador
            StringBuilder mensagem = new StringBuilder();
            mensagem.append("Bem vindo ao BlackJack! Suas cartas são: ");

            for (Carta carta : cartas) {
                mensagem.append(carta).append(" "); // Adiciona um espaço entre as cartas
            }

            // Enviar a mensagem ao jogador
            paraJogador.writeBytes(mensagem.toString() + "\n");

            // Fechar os streams (se a conexão não for longa)
            doJogador.close();
            paraJogador.close();

        } catch (IOException e) {
            e.printStackTrace(); // Lida com problemas de I/O
            System.out.println("Erro ao comunicar com o jogador.");
        }
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
