import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 6789);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Mensagem do servidor: " + message);

                // Opcional: condição para sair do loop se uma mensagem específica for recebida
                if (message.equalsIgnoreCase("fim")) {
                    System.out.println("Conexão finalizada pelo servidor.");
                    break;
                }
            }

        } catch (IOException e) {
            System.err.println("Erro de I/O: " + e.getMessage());
            e.printStackTrace();
        }
    }
}