package Game;

import ServerClientConstants.ResponseEnd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.SynchronousQueue;

/**
 * Created by Evgeny_Akulenko on 6/9/2016.
 */
public class TicTacToeGameServer implements GameServer {
    private final Logger logger = LoggerFactory.getLogger(TicTacToeGameServer.class);
    private static final String X = "X";
    private static final String O = "O";
    private SynchronousQueue<Game> queue;
    private Game game;
    private int turnOrder;
    private static int playersCount = 0;
    private Socket socket;

    public TicTacToeGameServer(SynchronousQueue<Game> queue, Game game) {
        this.game = game;
        this.queue = queue;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public void setTurnOrder(int turnOrder) {
        this.turnOrder = turnOrder;
        playersCount++;
    }

    @Override
    public void startGame() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            int i = 0;
            String playerType;
            if (turnOrder == 0) {
                playerType = X;
                writer.println("Waiting for another player connect");
                writer.println("end");
            } else {
                playerType = O;
            }
            while (playersCount != 2) {
                writer.println(".");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    logger.error("Error: ", ex);
                }
            }
            writer.println("end");
            writer.println("All players are ready");
            writer.println("You are play like: " + playerType);
            writer.println(ResponseEnd.MESSAGE_END);

            while (true) {
                System.out.println("client step N:" + i);
                if (turnOrder != 0 || (turnOrder == 0 && i != 0)) {
                    try {
                        game = queue.take();
                    } catch (InterruptedException ex) {
                        logger.error("Error: ", ex);
                    }
                }
                if (game.getWinner() != null) {
                    writer.println("Oops...you lose!");
                    writer.println(game.showField());
                    writer.println(ResponseEnd.GAME_OVER);
                    break;
                } else if (game.isNoWinner()) {
                    writer.println("Nobody Wins");
                    writer.println(game.showField());
                    writer.println(ResponseEnd.GAME_OVER);
                    break;
                } else {
                    writer.println("Make turn!");
                    writer.println(game.showField());
                    writer.println(ResponseEnd.NEXT_TURN);
                }

                while (true) {
                    String stringTurn = reader.readLine();
                    String[] turn = stringTurn.split(",");
                    int lastI = Integer.parseInt(turn[0]);
                    int lastJ = Integer.parseInt(turn[1]);
                    if (game.isTurnAvailable(lastI, lastJ)) {
                        game.makeTurn(playerType, lastI, lastJ);
                        break;

                    }
                    writer.println("Error!!!");
                    writer.println("Please enter the correct data!");
                    writer.println(ResponseEnd.INPUT_ERROR);
                }
                try {
                    writer.println(game.showField());
                    writer.println("Turn is Done");
                    writer.println(ResponseEnd.MESSAGE_END);
                    queue.put(game);
                    if (game.getWinner() != null) {
                        writer.println("Great!!! You WIN!!!");
                        writer.println(ResponseEnd.GAME_OVER);
                        break;
                    } else if (game.isNoWinner()) {
                        writer.println("Nobody Wins");
                        writer.println(game.showField());
                        writer.println(ResponseEnd.GAME_OVER);
                        break;
                    } else {
                        writer.println("Waiting for another player turn...");
                        writer.println(ResponseEnd.MESSAGE_END);
                    }
                } catch (InterruptedException ex) {
                    logger.error("Error: ", ex);
                }
                i++;
            }
        } catch (IOException ex) {
            logger.error("Error: ", ex);
        }
    }
}
