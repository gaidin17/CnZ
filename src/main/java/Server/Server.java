package Server;

import Game.Game;
import Game.TicTacToe;
import Game.TicTacToeGameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SynchronousQueue;

public class Server {
    private final Logger logger = LoggerFactory.getLogger(Server.class);
    private final static int NUMBER_OF_PLAYERS = 2;

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer(999);
    }

    public void startServer(int port) {
        List<Socket> sockets = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Server started and ready for connection on port: {}", port);
            Game game = new TicTacToe();
            SynchronousQueue<Game> queue = new SynchronousQueue<>();
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                TicTacToeGameServer gameServerThread = new TicTacToeGameServer(socket, i, queue, game);
                gameServerThread.start();

            }
        } catch (IOException ex) {
            logger.error("Error: ", ex);
        }
    }
}
