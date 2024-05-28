package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server;

import cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.common.TCPProtocol;

import java.io.*;
import java.net.Socket;

public class PatternServerThread extends Thread {
    private Socket socket;

    public PatternServerThread(Socket socket) {
        super("PatternServerThread");
        this.socket = socket;
    }

    public void run() {
        try (PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            TCPProtocol protocolo = new TCPProtocol();
            String salida = protocolo.procesarEntrada(null);
            writer.println(salida);

            String entrada;
            while ((entrada = reader.readLine()) != null) {
                salida = protocolo.procesarEntrada(entrada);
                writer.println(salida);
                if (salida.contains("<status>¡Chao!</status>"))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
