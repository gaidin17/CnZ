package Server;

import Game.Game;
import Game.CnZ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;

public class Server {
    private final static int NUMBER_OF_PLAYERS = 2;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(999);
    }

    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and ready for connection on port: " + port);
            Game game = new CnZ();
            SynchronousQueue<Game> queue = new SynchronousQueue<>();
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                Socket socket = serverSocket.accept();
                if (i == 0) {
                    new ServerThreadForFirstClient(socket, queue, game).start();
                } else {
                    System.out.println("Second Player is Ready");
                    new ServerThreadForSecondClient(socket, queue, game).start();
                }
            }
        } catch (IOException ex) {

        }
    }
}
