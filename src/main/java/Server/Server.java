package Server;

import Game.Game;
import Game.GameServer;
import Game.TicTacToe;
import Game.TicTacToeGameServer;
import ServerClientConstants.GameTypeContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;

public class Server {
    private final Logger logger = LoggerFactory.getLogger(Server.class);
    private final static int NUMBER_OF_PLAYERS = 2;
    private int gameType;

    public static void main(String[] args) {
        Server server = new Server();
        server.setGameType(0);
        server.startServer(999);
    }

    public void setGameType(int gameType) {
        this.gameType = gameType;
    }

    public void startServer(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server started and ready for connection on port: " + port);
            Game game;
            if (gameType == GameTypeContainer.TIC_TAC_TOE) {
                game = new TicTacToe();
            } else {
                logger.error("This game type is not exist!");
                return;
            }
            SynchronousQueue<Game> queue = new SynchronousQueue<>();
            for (int i = 0; i < NUMBER_OF_PLAYERS; i++) {
                Socket socket = serverSocket.accept();
                GameServer gameServer = new TicTacToeGameServer(queue, game);
                gameServer.setTurnOrder(i);
                ServerThread client = new ServerThread(socket, gameServer);
                client.start();
            }
        } catch (IOException ex) {
            logger.error("Error: ", ex);
        }
    }
}
