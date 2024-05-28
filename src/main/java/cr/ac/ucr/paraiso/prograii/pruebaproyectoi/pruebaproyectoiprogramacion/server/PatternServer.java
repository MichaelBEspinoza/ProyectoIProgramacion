package cr.ac.ucr.paraiso.prograii.pruebaproyectoi.pruebaproyectoiprogramacion.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PatternServer {
    private static final int PORT = 1234;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newCachedThreadPool();
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Servidor escuchando en el puerto " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                pool.execute(new PatternServerThread(clientSocket));
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}