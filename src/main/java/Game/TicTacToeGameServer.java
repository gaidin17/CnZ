package Game;

import MessengerUtils.MessageReciever;
import ServerClientConstants.PlayersType;
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
public class TicTacToeGameServer extends Thread {
    private static final Logger logger = LoggerFactory.getLogger(TicTacToeGameServer.class);
    private final SynchronousQueue<Game> queue;
    private boolean isEndOfGame;
    private PlayersType playerType;
    private Game game;
    private final int turnOrder;
    private final Socket socket;
    private volatile static int playersCount = 0;

    public TicTacToeGameServer(Socket socket, int turnOrder, SynchronousQueue<Game> queue, Game game) {
        this.socket = socket;
        this.game = game;
        this.queue = queue;
        this.turnOrder = turnOrder;
        playersCount++;
    }

    public void run() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {
            MessageReciever messageReciever = new MessageReciever(writer);
            int stepCount = 0;

            setPlayerType();

            if (playerType.equals(PlayersType.X)) {
                waitForAnotherPlayers(messageReciever);
            }

            messageReciever.sendMessage("All players are ready\nYou are play like: " + playerType, ResponseEnd.MESSAGE_END);

            while (true) {
                if (turnOrder != 0 || stepCount != 0) {
                    try {
                        game = queue.take();
                    } catch (InterruptedException ex) {
                        logger.error("Error: ", ex);
                    }
                }

                checkEndOfGame(messageReciever);

                if (isEndOfGame) {
                    break;
                }
                messageReciever.sendMessage("Make turn", ResponseEnd.MESSAGE_END);
                messageReciever.sendMessage(game.showField(), ResponseEnd.GAME_FIELD);
                messageReciever.sendMessage("", ResponseEnd.NEXT_TURN);
                while (true) {
                    String stringTurn = reader.readLine();
                    String[] turn = stringTurn.split(",");
                    try {
                        int lastI = Integer.parseInt(turn[0]);
                        int lastJ = Integer.parseInt(turn[1]);
                        if (game.isTurnAvailable(lastI, lastJ)) {
                            game.makeTurn(playerType, lastI, lastJ);
                            messageReciever.sendMessage(game.showField(), ResponseEnd.GAME_FIELD);
                            messageReciever.sendMessage("Turn is Done", ResponseEnd.MESSAGE_END);
                            break;
                        } else {
                            messageReciever.sendMessage("Error! Please enter the correct data!", ResponseEnd.INPUT_ERROR);
                        }
                    } catch (NumberFormatException ex) {
                        messageReciever.sendMessage("Error! Please enter the correct data!", ResponseEnd.INPUT_ERROR);
                    }
                }

                try {
                    checkEndOfGame(messageReciever);
                    queue.put(game);
                    if (isEndOfGame) {
                        break;
                    }
                } catch (InterruptedException ex) {
                    logger.error("Error: ", ex);
                }
                messageReciever.sendMessage("Waiting for another player turn...", ResponseEnd.MESSAGE_END);
                stepCount++;
            }
        } catch (IOException ex) {
            logger.error("Error: ", ex);
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    logger.error("Error", ex);
                }

            }
        }
    }

    private void setPlayerType() {
        if (turnOrder == 0) {
            playerType = PlayersType.X;
        } else {
            playerType = PlayersType.O;
        }
    }

    private void waitForAnotherPlayers(MessageReciever messageReciever) {
        messageReciever.sendMessage("Waiting for another player connect", "end");
        while (playersCount != 2) {
            messageReciever.sendMessage(".", "end");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
                logger.error("Error: ", ex);
            }
        }
    }

    private void checkEndOfGame(MessageReciever messageReciever) {
        if (game.getWinner() != null) {
            if (game.getWinner().equals(playerType)) {
                messageReciever.sendMessage("Great! You win!", ResponseEnd.GAME_OVER);
            } else {
                messageReciever.sendMessage(game.showField(), ResponseEnd.GAME_FIELD);
                messageReciever.sendMessage("Sorry. You lose!", ResponseEnd.GAME_OVER);
            }
            isEndOfGame = true;
        } else if (game.isNoWinner()) {
            messageReciever.sendMessage("Nobody Wins", ResponseEnd.GAME_OVER);
            isEndOfGame = true;
        }
    }
}
