package Client;

import Game.GameClient;
import Game.TicTacToeGameClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.Socket;


public class Client {
    private final Logger logger = LoggerFactory.getLogger(Client.class);
    private GameClient gameClient;

    private String server;
    private int port;

    public static void main(String[] args) {
        Client client = new Client();
        client.setConnectionParam("localhost", 999);
        TicTacToeGameClient gameClient = new TicTacToeGameClient();
        client.setGameClient(gameClient);
        client.startClient();
    }

    public void setGameClient(GameClient gameClient) {
        this.gameClient = gameClient;
    }

    public void startClient() {
        try (Socket socket = new Socket(server, port)) {
            if (gameClient == null) {
                return;
            }
            gameClient.startGame(socket);
        } catch (IOException ex) {
            logger.error("Error: ", ex);
        }
    }

    public void setConnectionParam(String server, int port) {
        this.server = server;
        this.port = port;
    }
}
